package uz.support.v14.letter.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class HttpRequest {

    public static void send(String url,
                            Request request,
                            boolean methodPost,
                            String contentType) throws Exception {
        load(url, request, methodPost, methodPost, contentType);
    }

    public static void sendGZIP(String url,
                                Request request,
                                String contentType) throws Exception {
        load(url, request, true, true, contentType);
    }

    private static void load(String url,
                             Request request,
                             boolean methodPost,
                             boolean gzip,
                             String contentType) throws Exception {
        InputStream is;
        OutputStream os;
        GZIPInputStream gis;
        GZIPOutputStream gos;
        HttpURLConnection conn = null;

        try {
            URL u = new URL(url);
            if (u.getProtocol().equalsIgnoreCase("https")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) u.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) u.openConnection();
            }
            conn.setRequestProperty("connection", "close");
            //************************ Send request to server **************************************
            if (methodPost) {
                conn.setRequestProperty("Content-Type", contentType);
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);

                os = new BufferedOutputStream(conn.getOutputStream());

                if (gzip) {
                    gos = new GZIPOutputStream(os);
                    request.send(gos);
                    gos.finish();
                } else {
                    request.send(os);
                }
                os.flush();
            }
            //************************ Receive response to server **************************************
            is = new BufferedInputStream(conn.getInputStream());

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (gzip) {
                    gis = new GZIPInputStream(is);
                    request.receive(gis);
                } else {
                    request.receive(is);
                }
            } else {
                throw new RuntimeException("resp.StatusCode=" + String.valueOf(conn.getResponseCode()));
            }

            is.close();
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Invalid url:" + url, ex);
        } catch (IOException ex) {
            if (conn != null) {
                is = conn.getErrorStream();
                String err;
                if (is != null) {
                    err = makeString(is);
                    throw new RuntimeException(err);
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }

    public static String makeString(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF8"), 4096);
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
