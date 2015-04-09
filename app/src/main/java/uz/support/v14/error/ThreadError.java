/*
 * Copyright (c) 2014. Green White Solutions LLC. Tashkent.
 */

package uz.support.v14.error;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import uz.support.v14.util.Util;

public class ThreadError implements ThreadExceptionHandler.OnError {

    @Override
    public void onError(Throwable ex) {
        String stackTrace = extractStackTrace(ex);
        key(stackTrace);
    }

    public static void key(String stackTrace) {
        FileWriter writer = null;
        try {
            File appLogPath = getAppLogPath();
            String today = Util.YYYYMMDDHHMMSS.get().format(new Date());
            File logFile = new File(appLogPath, today + ".txt");
            writer = new FileWriter(logFile);
            writer.append(stackTrace);
        } catch (Throwable e) {
            Log.e("MY TAG", "Error save", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getAppLogPath() {
        File dir = new File(Environment.getExternalStorageDirectory(), "support_v14");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String extractStackTrace(Throwable ex) {
        StringWriter result = new StringWriter();
        PrintWriter writer = new PrintWriter(result);
        ex.printStackTrace(writer);
        writer.close();
        return result.toString();
    }
}
