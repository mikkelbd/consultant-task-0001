package no.unit.transformer;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "not @wip",
        features = { "classpath:features/mvp.feature" },
        glue = { "no.unit.transformer.features" })
public class FeatureTest {
    @Test
    public void dummyTest(){}
}
