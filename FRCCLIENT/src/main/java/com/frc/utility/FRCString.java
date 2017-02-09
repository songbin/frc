package com.frc.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FRCString {

    private static String getClassName() {
	return "FRCString";
    }

    public static String toUtf8(String orgStr) {
	// A StringBuffer Object
	StringBuffer sb = new StringBuffer();
	sb.append(orgStr);
	String conStr = "";
	String strUTF8 = "";
	try {
	    conStr = new String(sb.toString().getBytes("UTF-8"));
	    strUTF8 = URLEncoder.encode(conStr, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    return null;
	}
	// return to String Formed
	return strUTF8;
    }

    /** inpiut �? utf8 String */
    public static String is2String(InputStream is) {
	try {
	    String line = "";
	    StringBuffer buffer = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(is,
		    "UTF-8"));
	    buffer = new StringBuffer();

	    while ((line = in.readLine()) != null) {
		buffer.append(line);
	    }
	    // is.reset();
	    return buffer.toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    return "";
	}

    }

    public static InputStream Str2InputStream(long logIndex, String str) {
	try {
	    // return new StringBufferInputStream(inStr);
	    ByteArrayInputStream stream = new ByteArrayInputStream(
		    str.getBytes("UTF-8"));

	    return stream;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, "LCQString.Str2Inputstr",
		    "str:{}", e, str);
	    return null;
	}
    }

    public static long strByteSize(long logIndex, String data) {
	try {
	    // return new StringBufferInputStream(inStr);
	    byte[] bytes = data.getBytes("UTF-8");
	    return bytes.length;
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, "LCQString.Str2Inputstr",
		    "str:{}", e, data);
	    return 0;
	}
    }

    public static String[] splitStr(long logIndex, String str, String split) {
	String flag = getClassName() + ".splitStr";
	try {
	    return str.split(split);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "", e);
	    return null;
	}
    }

}
