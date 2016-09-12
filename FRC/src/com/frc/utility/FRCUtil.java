package com.frc.utility;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.commons.io.IOUtils;

public class FRCUtil {

    final static int BUFFER_SIZE = 4096;

    private static String getClassName() {
	return "FRCUtil";
    }

    public static double str2double(String strVal) {
	String flag = getClassName() + ".str2double";
	try {
	    return Double.valueOf(strVal).doubleValue();
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(IdGen.getId(), flag,
		    "value:{} exception", e, strVal);
	    return 0.0;
	}
    }

    public static int str2int(String strVal) {
	String flag = getClassName() + ".str2int";
	try {
	    return Integer.valueOf(strVal).intValue();
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(IdGen.getId(), flag,
		    "value:{} exception", e, strVal);
	    return 0;
	}
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
	String flag = getClassName() + ".getMD5";
	try {
	    // 生成MD5加密计算摘要
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    // 计算md5函数
	    md.update(str.getBytes());
	    // digest()后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash
	    return new BigInteger(1, md.digest()).toString(16);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(IdGen.getId(), flag,
		    "value:{} exception", e, str);
	    return null;
	}
    }

    /**
     * 对字符串md5加密
     *
     * @param input
     *            InputStream
     * @return
     */
    public static String getInputMD5(long logIndex, InputStream input) {
	String flag = getClassName() + ".getInputMD5";
	try {
	    // 生成�?个MD5加密计算摘要
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    // 计算md5函数
	    md.update(IOUtils.toByteArray(input));
	    // digest()�?后确定返回md5 hash值，返回值为8为字符串。因为md5
	    // hash值是16位的hex值，实际上就�?8位的字符
	    // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash�?
	    return new BigInteger(1, md.digest()).toString(16);
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "exception", e);
	    return null;
	}
    }

    public static String getInputCRC32(long logIndex, InputStream input) {
	String flag = getClassName() + ".getInputCRC32";
	try {
	    CRC32 crc32 = new CRC32();
	    for (CheckedInputStream checkedinputstream = new CheckedInputStream(
		    input, crc32); checkedinputstream.read() != -1;) {
	    }
	    return Long.toHexString(crc32.getValue());
	} catch (Exception e) {
	    FRCLogger.getInstance().warn(logIndex, flag, "", e);
	    return null;
	}

    }

}
