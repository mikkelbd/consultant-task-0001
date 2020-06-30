package no.unit.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;


public final class ObjectMappers {

    public static final ObjectMapper jsonObjectMapper = createJsonObjectMapper();
    public static final XmlMapper xmlMapper = createXmlMapper();

    private ObjectMappers() {
    }

    private static ObjectMapper createJsonObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static XmlMapper createXmlMapper() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        return new XmlMapper(module);
    }

    static void transform(File inputFile, File outputFile) throws IOException {
        if (isJsonFile(inputFile)) {
            UsersJsonFile usersJsonFile = jsonObjectMapper.readValue(inputFile, UsersJsonFile.class);
            usersJsonFile.getUsers().sort(User::compareTo);

            if (isJsonFile(outputFile)) {
                jsonObjectMapper.writeValue(outputFile, usersJsonFile);
            } else if (isXmlFile(outputFile)) {
                UsersXmlFile usersXmlFile = new UsersXmlFile();
                usersXmlFile.setUser(usersJsonFile.getUsers());
                xmlMapper.writeValue(outputFile, usersXmlFile);
            }

        } else if (isXmlFile(inputFile)) {
            UsersXmlFile usersXmlFile = xmlMapper.readValue(inputFile, UsersXmlFile.class);
            usersXmlFile.getUser().sort(User::compareTo);

            if (isXmlFile(outputFile)) {
                xmlMapper.writeValue(outputFile, usersXmlFile);
            } else if (isJsonFile(outputFile)) {
                UsersJsonFile usersJsonFile = new UsersJsonFile();
                usersJsonFile.setUsers(usersXmlFile.getUser());
                jsonObjectMapper.writeValue(outputFile, usersJsonFile);
            }
        }
    }

    private static boolean isJsonFile(File file) {
        return file.getName().endsWith(".json");
    }

    private static boolean isXmlFile(File file) {
        return file.getName().endsWith(".xml");
    }

}
