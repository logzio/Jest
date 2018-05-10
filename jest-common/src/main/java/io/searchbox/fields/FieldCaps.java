package io.searchbox.fields;

import io.searchbox.action.AbstractAction;
import io.searchbox.action.GenericResultAbstractAction;
import io.searchbox.client.config.ElasticsearchVersion;
import io.searchbox.params.Parameters;

import java.util.HashMap;
import java.util.Map;

public class FieldCaps extends GenericResultAbstractAction {
    protected FieldCaps(FieldCaps.Builder builder) {
        super(builder);

        this.indexName = builder.index;

        Map<String, Object> fieldStatsBody = new HashMap<>();
        fieldStatsBody.put("fields", builder.fields);

        this.payload = fieldStatsBody;
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    @Override
    protected String buildURI(ElasticsearchVersion elasticsearchVersion) {
        String buildURI = super.buildURI(elasticsearchVersion);
        if (buildURI.isEmpty())
            return "_field_caps";

        return buildURI + "/_field_caps";
    }


    public static class Builder extends AbstractAction.Builder<FieldCaps, FieldCaps.Builder> {

        private String index;
        private Object fields;

        public Builder(Object fields) {
            this.fields = fields;
        }

        public FieldCaps.Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        @Override
        public FieldCaps build() {
            return new FieldCaps(this);
        }
    }
}
