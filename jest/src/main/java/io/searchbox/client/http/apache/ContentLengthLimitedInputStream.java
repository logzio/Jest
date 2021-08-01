package io.searchbox.client.http.apache;

import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.ContentTooLongException;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthLimitedInputStream extends CountingInputStream {

    private final int contentLengthLimit;

    public ContentLengthLimitedInputStream(InputStream in, int contentLengthLimit) {
        super(in);
        this.contentLengthLimit = contentLengthLimit;
    }

    @Override
    protected void beforeRead(int n) throws IOException {
        if (this.getByteCount() >= contentLengthLimit)
            throw new ContentTooLongException("ES response exceeds allowed limit");
        super.beforeRead(n);
    }
}
