package no.unit.transformer.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import no.unit.transformer.Transformer;
import picocli.CommandLine;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions extends TestWiring {

    private Transformer transformer;
    private CommandLine commandLine;

    @Given("^the user has an application \"Transformer\" that has a command line interface$")
    public void theUserHasAnApplicationThatHasACommandLineInterface() {
        transformer = new Transformer();
        commandLine = new CommandLine(transformer);
    }

    @And("\"Transformer\" has a flag \"--input\" that takes a single argument that is a filename")
    public void hasAFlagInputThatTakesASingleArgumentThatIsAFilename() {
        CommandLine.ParseResult result = commandLine.parseArgs("--input", "filename.json");
        assertThat(result.matchedOption("--input").<File>getValue()).hasName("filename.json");
    }

    @And("\"Transformer\" has a flag \"--output\" that takes a single argument that is a filename")
    public void hasAFlagOutputThatTakesASingleArgumentThatIsAFilename() {
        CommandLine.ParseResult result = commandLine.parseArgs("--output", "filename.json");
        assertThat(result.matchedOption("--output").<File>getValue()).hasName("filename.json");
    }

    @And("\"Transformer\" has a flag \"--input-format\" that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagInputFormatThatTakesASingleArgumentXmlOrJson() {
        CommandLine.ParseResult result = commandLine.parseArgs("--input-format", "json");
        assertThat(result.matchedOption("--input-format").<String>getValue()).isEqualTo("json");
    }

    @And("\"Transformer\" has a flag \"--output-format\" that takes a single argument \"xml\" or \"json\"")
    public void theTransformerHasAFlagOutputFormatThatTakesASingleArgumentXmlOrJson() {
        CommandLine.ParseResult result = commandLine.parseArgs("--output-format", "xml");
        assertThat(result.matchedOption("--output-format").<String>getValue()).isEqualTo("xml");
    }

    @Given("the user has a file {string}")
    public void theUserHasAFileInputFile(String file) {
        throw new PendingException();
    }

    @And("the data is formatted correctly")
    public void theDataIsFormattedCorrectly() {
        // do nothing
    }

    @When("the user transforms the file to (.*)")
    public void theUserTransformsTheFileTo(String outputFile) {
        throw new PendingException();
    }

    @Then("they see that the data is transformed to (.*)")
    public void theySeeThatTheDataIsTransformedToSerializationB(String serialization) {
        throw new PendingException();
    }

    @And("that the elements in \"users\" section of the file are ordered by element \"sequence\"")
    public void thatTheElementsInSectionOfTheFileAreOrderedByElement() {
        throw new PendingException();
    }

    @And("they open the transformed file")
    public void theyOpenTheTransformedFile() {
        throw new PendingException();
    }

    @When("the user attempts to transform the file")
    public void theUserAttemptsToTransformTheFile() {
        throw new PendingException();
    }

    @Given("the user has a badly formatted file")
    public void theUserHasABadlyFormattedFile() {
        File file = getFileFromResources("badly_formatted.json");
        assertThat(file).exists();
    }

    @Then("they see an error message telling them that the input file is badly formatted")
    public void theySeeAnErrorMessageTellingThemThatTheInputFileIsBadlyFormatted() {
        throw new PendingException();
    }

}
