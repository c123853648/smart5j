package com.jinandaxue.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Liucong on 2019/4/5.
 */

public class StreamTools {
    public static String readStream(InputStream is) throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
