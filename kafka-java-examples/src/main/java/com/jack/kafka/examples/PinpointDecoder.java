package com.jack.kafka.examples;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by jack on 4/6/16.
 */
public class PinpointDecoder implements Decoder<String> {
    String header;

    public PinpointDecoder(){
    }

    public PinpointDecoder(VerifiableProperties properties) {
    }

    public String fromBytes(byte[] in) {
        String value=new String(in);
        System.out.println("PinpointDecoder.fromBytes(): got value="+value);
        int pos=value.indexOf("\n");
        if (pos == -1) {
            System.out.println("PinpointDecoder.fromBytes(): return header=null value="+value);
            header=null;
            return value;
        }
        header=value.substring(0,pos);
        System.out.println("PinpointDecoder.fromBytes(): return header="+header+", value="+value.substring(pos));
        return value.substring(pos+1);
    }
}
