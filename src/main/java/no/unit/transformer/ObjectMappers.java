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

    static void transform(File inputFile, File outputFile) throws IOException {
        if (isJsonFile(inputFile)) {
            UsersJsonFile users = jsonObjectMapper.readValue(inputFile, UsersJsonFile.class);
            users.getUsers().sort(User::compareTo);
            if (isJsonFile(outputFile)) {
                jsonObjectMapper.writeValue(outputFile, users);
            }
        }
    }

    private static boolean isJsonFile(File file) {
        return file.getName().endsWith(".json");
    }

}
