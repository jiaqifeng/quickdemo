package com.jack.maven.webapp;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * Created by jack on 4/6/16.
 *
 * This serializer pinpoint ids infor into kafka value.
 * The ids will be instrument by pinpoint agent.
 */
public class PinpointEncoder implements Encoder<String> {

    private String header;

    public PinpointEncoder(){
        System.out.println("PinpointEncoder.<init> no args");
        header="tempPinpointHeader";
    }

    public PinpointEncoder(VerifiableProperties properties) {
        System.out.println("PinpointEncoder.<init> with prop");
        header="tempPinpointHeader";
    }

    /*
     * called by pinpoint agent to set ids
     */
    public void setPinpointHeader(String header) {
        this.header=header;
    }

    public byte[] toBytes(String s) {
        System.out.println("PinpointEncoder.toBytes: header="+header+", value="+s);
        StringBuilder sb;
        if (header != null) {
            sb = new StringBuilder(header);
            sb.append("\n");
            sb.append(s);
        } else {
            sb = new StringBuilder(s.toString());
        }
        System.out.println("PinpointEncoder.toBytes: message="+sb.toString());
        return sb.toString().getBytes();
    }
}
