package com.jack.pinpoint.utils;

/**
 * Created by jack on 17-4-17.
 */
public class BytesToHexString {
    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("{");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            stringBuilder.append("0x");
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv+" ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        byte[] data={0x01,0x43,0x78,0x2f};
        System.out.println("result is "+bytesToHexString(data));
    }
}
