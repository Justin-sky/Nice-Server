package com.nice.core.utils;

import java.io.*;

public class FileUtil {
    public static boolean exists(String path){
        File file = new File(path);
        return file.exists();
    }
    public static String getFileCharset(String fileName) throws Exception{
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        bin.close();
        return code;
    }
    public static String readToString(File file, String charset) throws Exception {
        FileInputStream fInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fInputStream, charset);
        BufferedReader in = new BufferedReader(inputStreamReader);

        String strTmp = "";
        StringBuffer sBuffer = new StringBuffer();
        //°´ÐÐ¶ÁÈ¡
        while (( strTmp = in.readLine()) != null) {
            sBuffer.append(strTmp + "\n");
        }
        in.close();
        inputStreamReader.close();
        fInputStream.close();
        return sBuffer.toString();
    }

    public static String readToString(String fileName, String charset) throws Exception {
        File file=new File(fileName);
        String content = readToString(file, charset);

        file = null;
        return content;
    }

    public static String readToString(String fileName) throws Exception {
        String charset = getFileCharset(fileName);
        File file=new File(fileName);
        String content = readToString(file, charset);

        file = null;
        return content;
    }
}
