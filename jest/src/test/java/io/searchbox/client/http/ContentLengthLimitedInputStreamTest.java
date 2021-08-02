package io.searchbox.client.http;

import io.searchbox.client.http.apache.ContentLengthLimitedInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.ContentTooLongException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class ContentLengthLimitedInputStreamTest {

    @Test
    public void contentLimitedInputStream() throws IOException {
        // 57 bytes
        String initialString = "this should be too long and throw ContentTooLongException";
        InputStream targetStream = IOUtils.toInputStream(initialString, Charset.defaultCharset());
        ContentLengthLimitedInputStream contentLengthLimitedInputStream = new ContentLengthLimitedInputStream(targetStream, 57);
        String afterStream = IOUtils.toString(contentLengthLimitedInputStream, Charset.defaultCharset());
        assertEquals(initialString, afterStream);
    }

    @Test(expected = ContentTooLongException.class)
    public void contentLimitedInputStreamShouldThrowIfTooLong() throws IOException {
        String initialString = "this should be too long and throw ContentTooLongException";
        InputStream targetStream = IOUtils.toInputStream(initialString, Charset.defaultCharset());
        ContentLengthLimitedInputStream contentLengthLimitedInputStream = new ContentLengthLimitedInputStream(targetStream, 5);
        IOUtils.toString(contentLengthLimitedInputStream, Charset.defaultCharset());
    }
}
