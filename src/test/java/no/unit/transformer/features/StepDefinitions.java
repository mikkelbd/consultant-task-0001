package no.unit.transformer.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import no.unit.transformer.Transformer;
import no.unit.transformer.User;
import no.unit.transformer.UsersJsonFile;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static no.unit.transformer.ObjectMappers.jsonObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class StepDefinitions extends TestWiring {

    private CommandLine commandLine;
    private String inputFileArgument;
    private String outputFileArgument;
    private File transformedOutputFile;
    private List<User> usersListFromTransformedFile;
    private int transformerExitCode;
    private StringWriter errorWriterUsedByCommand;

    @Given("^the user has an application \"Transformer\" that has a command line interface$")
    public void theUserHasAnApplicationThatHasACommandLineInterface() {
        Transformer transformer = new Transformer();
        commandLine = new CommandLine(transformer);
        errorWriterUsedByCommand = new StringWriter();
        commandLine.setErr(new PrintWriter(errorWriterUsedByCommand));
    }

    @And("\"Transformer\" has a flag \"--input\" that takes a single argument that is a filename")
    public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename() {
        CommandLine.ParseResult result = commandLine.parseArgs("--input", "filename.json", "--output", "some file");
        assertThat(result.matchedOption("--input").<File>getValue()).hasName("filename.json");
    }

    @And("\"Transformer\" has a flag \"--output\" that takes a single argument that is a filename")
    public void hasAFlagOutputThatTakesASingleArgumentThatIsAFilename() {
        CommandLine.ParseResult result = commandLine.parseArgs("--output", "filename.json", "--input", "some file");
        assertThat(result.matchedOption("--output").<File>getValue()).hasName("filename.json");
    }

    @Given("the user has a file {string}")
    public void theUserHasAFileInputFile(String file) {
        File fileFromResources = getFileFromResources(file);
        assertThat(fileFromResources).exists();
        inputFileArgument = fileFromResources.getAbsolutePath();
    }

    @And("the data is formatted correctly")
    public void theDataIsFormattedCorrectly() {
        // do nothing
    }

    @When("the user transforms the file to {string}")
    public void theUserTransformsTheFileTo(String file) {
        transformedOutputFile = new File("build/", file);
        outputFileArgument = transformedOutputFile.getAbsolutePath();
        String[] args = {"--input", inputFileArgument, "--output", outputFileArgument};
        transformerExitCode = commandLine.execute(args);
    }

    @And("the exitcode from the transformer is {int}")
    public void theExitcodeFromTheTransforIs(int expectedExitCode) {
        assertThat(transformerExitCode).isEqualTo(expectedExitCode);
    }

    @And("they see that the data is transformed to {string}")
    public void theySeeThatTheDataIsTransformedToSerialization(String serialization) throws IOException {
        assertThat(transformerExitCode).isEqualTo(0);

        if ("json".equals(serialization)) {
            UsersJsonFile transformedUsersJsonFile = jsonObjectMapper.readValue(transformedOutputFile, UsersJsonFile.class);
            usersListFromTransformedFile = transformedUsersJsonFile.getUsers();
            assertThat(transformedUsersJsonFile.getUsers()).isNotEmpty();
        } else if ("xml".equals(serialization)) {
            throw new PendingException("xml case not implemented");
        } else {
            fail("Should either be json or xml");
        }
    }

    @And("that the elements in \"users\" section of the file are ordered by element \"sequence\"")
    public void thatTheUsersListIsOrderedBySequence() {
        assertThat(usersListFromTransformedFile).isSorted();
    }

    @And("they see an error message telling them that the input file is badly formatted")
    public void theySeeAnErrorMessageTellingThemThatTheInputFileIsBadlyFormatted() {
        assertThat(errorWriterUsedByCommand.toString()).contains("seems to be malformed");
    }
}
