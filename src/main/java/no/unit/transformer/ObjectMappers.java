package no.unit.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;


public final class ObjectMappers {

    public static final ObjectMapper jsonObjectMapper = createJsonObjectMapper();

    private static ObjectMapper createJsonObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    private ObjectMappers() {
    }

}
