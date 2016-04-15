package com.jack.pinpoint.utils;

import com.navercorp.pinpoint.common.util.TransactionId;
import com.navercorp.pinpoint.common.util.TransactionIdUtils;

public class ConvertTxId {
    /**
     *
     * 十六进制转换字符串
     */

    public static String hexStr2Str(String hexStr) {
        System.out.println("hexStr2Str: hexstr="+hexStr);
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    /*
     * src0, src1 are single character in [0-9a-hA-H]
     */
    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println("arg="+args[0]);

        byte[] bytes=hexStr2Bytes(args[0].replace(" ","").toUpperCase());
        TransactionId txId=TransactionIdUtils.parseTransactionId(bytes);
        System.out.println("agentId  ="+txId.getAgentId());
        System.out.println("startTime="+txId.getAgentStartTime());
        System.out.println("sequence ="+txId.getTransactionSequence());
    }
}