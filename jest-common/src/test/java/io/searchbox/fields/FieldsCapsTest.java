package io.searchbox.fields;

import com.google.gson.Gson;
import io.searchbox.client.config.ElasticsearchVersion;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FieldsCapsTest {
    static final String TEST_FIELD = "test_name";
    static final String INDEX = "twitter";
    static final List FIELDS = Collections.singletonList(TEST_FIELD);

    @Test
    public void testBasicUriGeneration() {
        FieldCaps fieldCaps = new FieldCaps.Builder(FIELDS).setIndex(INDEX).build();
        assertEquals("POST", fieldCaps.getRestMethodName());
        assertEquals(INDEX + "/_field_caps", fieldCaps.getURI(ElasticsearchVersion.V55));
        assertEquals("{\"fields\":[\"" + TEST_FIELD + "\"]}", fieldCaps.getData(new Gson()));
    }

    @Test
    public void testBasicUriGenerationNoIndex() {
        FieldCaps fieldCaps = new FieldCaps.Builder(FIELDS).build();
        assertEquals("POST", fieldCaps.getRestMethodName());
        assertEquals("_field_caps", fieldCaps.getURI(ElasticsearchVersion.V55));
        assertEquals("{\"fields\":[\"" + TEST_FIELD + "\"]}", fieldCaps.getData(new Gson()));
    }
}
