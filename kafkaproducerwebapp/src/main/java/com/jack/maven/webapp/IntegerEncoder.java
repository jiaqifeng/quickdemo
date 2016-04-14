package com.jack.maven.webapp;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by jack on 4/6/16.
 */
public class IntegerEncoder implements Encoder<Integer> {

    public IntegerEncoder(){
        System.out.println("PinpointEncoder.<init> no args");
    }

    public IntegerEncoder(VerifiableProperties properties) {
        System.out.println("PinpointEncoder.<init> with prop");
    }

    public byte[] toBytes(Integer number) {
        System.out.println("PinpointEncoder.toBytes: value="+number);
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(number&0xff);
        bytes[1] = (byte)(number>>8&0xff);
        bytes[2] = (byte)(number>>16&0xff);
        bytes[3] = (byte)(number>>24&0xff);
        return bytes;
    }

}
