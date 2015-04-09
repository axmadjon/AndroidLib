package uz.support.v14.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        }
    };

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifi.isConnected() || mobile.isConnected();
    }

    public static String calcSHA(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        return shaHex(md.digest());
    }

    public static byte[] bitmapToByte(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return bytes.toByteArray();
    }

    private static String shaHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toString((aB & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String nvl(String v, String p) {
        return TextUtils.isEmpty(v) ? p : v;
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String DateToString(Date d) {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        return date.format(d);
    }

    public static Date StringToDate(String d) {
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        Date result = null;
        try {
            result = date.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
