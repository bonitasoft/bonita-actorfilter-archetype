## Contributing

We are pleased to receive any kind of contribution (issues, pull requests, suggestions ...).  

### Pull requests guidelines

To open a pull request on this repository, you must sign the contributor license agreement. 
 
<a href="https://cla-assistant.io/bonitasoft/bonita-actorfilter-archetype"><img src="https://cla-assistant.io/readme/badge/bonitasoft/bonita-actorfilter-archetype" alt="CLA assistant" /></a>

Here are a few things we would appreciate that you do when opening a pull request: 

#### Commit message format

Commit messages and pull request titles must follow the [Conventional Commits](https://www.conventionalcommits.org/) specification, which describes the expected format and the available types.

This is enforced on every pull request by the `Commit Check` workflow, and we rely on it to generate the release notes.

#### Tests

Ensure that your contribution is correctly tested: 

 - Any update on the generated project must be tested through the generated unit tests
 - Any update on the archetype must be tested through the integration test suite (*src/test/resources/projects*), run by `./mvnw install`
 - Any update on the sub-module generation or the version guard (*archetype-post-generate.groovy*) must be tested through the script integration tests: `./mvnw groovy:execute -Dsource=target/test-classes/testJavaSubModuleProject/IT.groovy -Dscope=test` (same for *testGroovySubModuleProject*, *testKotlinSubModuleProject* and *testInvalidBonitaVersion*)

<!-- TODO Bonita 12.0 GA: remove this note (grep 'TODO Bonita 12.0 GA' for all the sites to update) -->
Note: until Bonita 12.0 is released on Maven Central, the integration tests build the generated projects against the `12.0-SNAPSHOT` Bonita runtime, which requires access to the Bonitasoft Artifactory snapshot repositories in your Maven `settings.xml` (CI gets it through `bonitasoft/maven-settings-action`). Use `-DskipTests` to skip them.
