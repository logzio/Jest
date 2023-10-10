package io.searchbox.cluster;

import io.searchbox.action.AbstractMultiINodeActionBuilder;
import io.searchbox.action.GenericResultAbstractAction;
import io.searchbox.client.config.ElasticsearchVersion;

public class ReloadSecureSettings extends GenericResultAbstractAction {

    protected ReloadSecureSettings(ReloadSecureSettings.Builder builder) {
        super(builder);
    }

    @Override
    protected String buildURI(ElasticsearchVersion elasticsearchVersion) {
        return "/_cluster/" + nodes + "/reload_secure_settings";
    }

    @Override
    public String getRestMethodName() {
        return "POST";
    }

    public static class Builder extends AbstractMultiINodeActionBuilder<ReloadSecureSettings, ReloadSecureSettings.Builder> {
        @Override
        public ReloadSecureSettings build() {
            return new ReloadSecureSettings(this);
        }
    }
}