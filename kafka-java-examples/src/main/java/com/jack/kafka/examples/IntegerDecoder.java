package com.jack.kafka.examples;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by jack on 4/6/16.
 */
public class IntegerDecoder implements Decoder<Integer> {

    public IntegerDecoder(){
    }

    public IntegerDecoder(VerifiableProperties properties) {
    }

    public Integer fromBytes(byte[] bytes) {
        return (int)(bytes[0]&0xff | (bytes[1]&0xff)<<8 | (bytes[2]&0xff)<<16 | (bytes[3]&0xff)<<24);
    }
}
