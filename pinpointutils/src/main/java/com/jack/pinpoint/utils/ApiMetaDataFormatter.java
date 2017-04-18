package com.jack.pinpoint.utils;

import com.navercorp.pinpoint.common.PinpointConstants;
import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.buffer.FixedBuffer;
import com.navercorp.pinpoint.common.util.BytesUtils;
import com.navercorp.pinpoint.common.util.TimeUtils;

import java.io.IOException;
import java.util.Date;
import java.nio.ByteBuffer;

/**
 * Created by jack on 4/15/16.
 */
public class ApiMetaDataFormatter {
    public static int AGENT_NAME_MAX_LEN=65;

    public static String format(byte[] bytes) throws IOException {
        byte[] value = bytes;

        Buffer buffer = new FixedBuffer(value);
        String apiInfo = buffer.readPrefixedString();
        int lineNumber = buffer.readInt();
        int methodTypeEnum = buffer.readInt();

        return apiInfo+","+lineNumber+","+methodTypeEnum;
    }

    public static String keyrow(byte[] bytes) throws IOException {
        String agentId = BytesUtils.safeTrim(BytesUtils.toString(bytes, 1, AGENT_NAME_MAX_LEN-1));
        long startTime = TimeUtils.recoveryTimeMillis(readTime(bytes));
        int apiId = readKeyCode(bytes);
        return agentId+","+(new Date(startTime))+","+apiId;
    }
    private static long readTime(byte[] rowKey) {
        return BytesUtils.bytesToLong(rowKey, AGENT_NAME_MAX_LEN);
    }

    private static int readKeyCode(byte[] rowKey) {
        //System.out.println("readKeyCode()"+(AGENT_NAME_MAX_LEN + BytesUtils.LONG_BYTE_LENGTH)+",total="+rowKey.length);
        return BytesUtils.bytesToInt(rowKey, AGENT_NAME_MAX_LEN + BytesUtils.LONG_BYTE_LENGTH);
    }
}
