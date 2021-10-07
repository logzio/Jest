package io.searchbox.fields;

import io.searchbox.client.config.ElasticsearchVersion;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FieldsStatsTest {
    static final String TEST_FIELD = "test_name";
    static final String INDEX = "twitter";
    static final List FIELDS = Collections.singletonList(TEST_FIELD);

    @Test
    public void testBasicUriGeneration() {
        FieldCapabilities fieldCapabilities = new FieldCapabilities.Builder(FIELDS).setIndex(INDEX).build();
        assertEquals("GET", fieldCapabilities.getRestMethodName());
        assertEquals(INDEX + "/_field_caps?fields=test_name", fieldCapabilities.getURI(ElasticsearchVersion.UNKNOWN));
    }

    @Test
    public void testBasicUriGenerationNoIndex() {
        FieldCapabilities fieldCapabilities = new FieldCapabilities.Builder(FIELDS).build();
        assertEquals("GET", fieldCapabilities.getRestMethodName());
        assertEquals("_field_caps?fields=test_name", fieldCapabilities.getURI(ElasticsearchVersion.UNKNOWN));
    }

    @Test
    public void testBasicUriGenerationWithLevel() {
        FieldCapabilities fieldCapabilities = new FieldCapabilities.Builder(FIELDS).setIndex(INDEX).setLevel("indices").build();
        assertEquals("GET", fieldCapabilities.getRestMethodName());
        assertEquals(INDEX + "/_field_caps?fields=test_name&level=indices", fieldCapabilities.getURI(ElasticsearchVersion.UNKNOWN));
    }
}
