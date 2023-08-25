package io.searchbox.core;

import io.searchbox.action.BulkableAction;
import io.searchbox.action.SingleResultAbstractDocumentTargetedAction;
import io.searchbox.client.config.ElasticsearchVersion;
import io.searchbox.params.Parameters;
import io.searchbox.strings.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * ES 7 and OS 2 compatible
 */
public class Update2 extends SingleResultAbstractDocumentTargetedAction implements BulkableAction<DocumentResult> {

    protected Update2(Builder builder) {
        super(builder);
        this.payload = builder.payload;
    }

    @Override
    public String getBulkMethodName() {
        return "update";
    }

    @Override
    protected String buildURI(ElasticsearchVersion elasticsearchVersion) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotBlank(indexName)) {
            sb.append(encode(indexName));
            sb.append("/_update");

            if (StringUtils.isNotBlank(id)) {
                sb.append("/").append(encode(id));
            }

            String commandExtension = getURLCommandExtension(elasticsearchVersion);
            if (StringUtils.isNotBlank(commandExtension)) {
                sb.append("/").append(encode(commandExtension));
            }
        }

        return sb.toString();
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    @Override
    public String getPathToResult() {
        return "ok";
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, CHARSET);
        } catch (UnsupportedEncodingException e) {
            // unless CHARSET is overridden with a wrong value in a subclass,
            // this exception won't be thrown.
            log.error("Error occurred while adding index/type to uri", e);
            throw new RuntimeException(e);
        }
    }

    public static class Builder extends SingleResultAbstractDocumentTargetedAction.Builder<Update2, Builder> {
        private final Object payload;

        public Builder(Object payload) {
            this.payload = payload;
        }

        @Override
        public Update2 build() {
            return new Update2(this);
        }
    }

    public static class VersionBuilder extends Builder {
        public VersionBuilder(Object payload, Long version) {
            super(payload);
            this.setParameter(Parameters.VERSION, version);
        }
    }
}
