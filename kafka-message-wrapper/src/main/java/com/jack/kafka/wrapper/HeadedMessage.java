package com.jack.kafka.wrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HeadedMessage {
    public Map<String, String> header;
    public String message;

    public HeadedMessage() {
        header=new HashMap<String, String>(16);
        message=null;
    }

    public String setHeader(String key, String value) {
        return header.put(key, value);
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public void setMessage(String message) {
        this.message=message;
    }

    public String getMessage() {
        return this.message;
    }

    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : header.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = header.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        stringBuilder.append("\n\n");
        stringBuilder.append(message);
        return stringBuilder.toString();
    }

    public boolean decode(String input) {
        this.header.clear();
        this.message=null;

        int pos = input.indexOf("\n\n");
        if (pos == -1)
            return false;

        this.message=input.substring(pos+2);
        input=input.substring(0, pos);

        String[] nameValuePairs = input.split("&");
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                this.header.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                        nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                //throw new RuntimeException("This method requires UTF-8 encoding support", e);
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return encode();
    }
}