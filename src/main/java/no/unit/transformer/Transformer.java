package no.unit.transformer;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "transformer", mixinStandardHelpOptions = true, version = "1.0")
public class Transformer implements Callable<Integer> {

    @Option(names = "--input", description = "Input file name")
    File inputFile;

    @Option(names = "--output", description = "Output file name")
    File outputFile;

    @Option(names = "--input-format", description = "Format of the input file")
    String inputFormat;

    @Option(names = "--output-format", description = "Format of the output file")
    String outputFormat;

    @Override
    public Integer call() {
        return 0;
    }

    public static void main(String... args) {
        System.exit(new CommandLine(new Transformer()).execute(args));
    }
}
