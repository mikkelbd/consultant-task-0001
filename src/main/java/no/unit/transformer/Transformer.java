package no.unit.transformer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import static no.unit.transformer.ObjectMappers.jsonObjectMapper;

@Command(name = "transformer", mixinStandardHelpOptions = true, version = "1.0")
public class Transformer implements Callable<Integer> {

    @Option(names = "--input", required = true, description = "Input file name")
    File inputFile;

    @Option(names = "--output", required = true, description = "Output file name")
    File outputFile;

    @Override
    public Integer call() {
        try {
            if (isJsonFile(inputFile)) {
                UsersJsonFile users = jsonObjectMapper.readValue(inputFile, UsersJsonFile.class);
                if (isJsonFile(outputFile)) {
                    jsonObjectMapper.writeValue(outputFile, users);
                }
            }
            return 0;
        } catch (JsonParseException | JsonMappingException e) {
            System.err.println(String.format("File %s seems to be malformed JSON", inputFile));
            return 1;
        } catch (IOException e) {
            System.err.println(String.format("Could not find or open file %s", inputFile));
            return 1;
        }
    }

    private boolean isJsonFile(File file) {
        return file.getName().endsWith(".json");
    }

    public static void main(String... args) {
        System.exit(new CommandLine(new Transformer()).execute(args));
    }
}
