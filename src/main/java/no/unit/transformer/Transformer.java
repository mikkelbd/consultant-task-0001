package no.unit.transformer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

@Command(name = "transformer", mixinStandardHelpOptions = true, version = "1.0")
public class Transformer implements Callable<Integer> {

    @Spec
    CommandSpec commandSpec;

    @Option(names = "--input", required = true, description = "Input file name")
    File inputFile;

    @Option(names = "--output", required = true, description = "Output file name")
    File outputFile;

    @Override
    public Integer call() {
        PrintWriter err = commandSpec.commandLine().getErr();
        try {
            ObjectMappers.transform(inputFile, outputFile);
            return 0;
        } catch (JsonParseException | JsonMappingException e) {
            err.println(String.format("File %s seems to be malformed JSON", inputFile));
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            err.println(String.format("Could not find or open file %s", inputFile));
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String... args) {
        System.exit(new CommandLine(new Transformer()).execute(args));
    }
}
