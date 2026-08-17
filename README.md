[![Actions Status](https://github.com/bonitasoft/bonita-actorfilter-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions?query=workflow%3ABuild)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-actorfilter-archetype?color=blue&label=Release&include_prereleases)](https://github.com/bonitasoft/bonita-actorfilter-archetype/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.archetypes/bonita-actorfilter-archetype.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.archetypes%22%20AND%20a:%22bonita-actorfilter-archetype%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# Bonita Actor filter Archetype

This project contains a maven archetype, which allow to easily setup a Bonita actor filter project.

## Setup an extension project using the archetype 

⚠️ **Java 11 is required for Bonita 7.13+**

 You can setup a Bonita actor filter project using the following command, from a terminal: 
 
 _Make sure that you do not launch the command from an existing maven project._
 
```
mvn archetype:generate -DarchetypeGroupId=org.bonitasoft.archetypes -DarchetypeArtifactId=bonita-actorfilter-archetype
```

- **archetypeGroupId:** the group id of the actor filter archetype.
- **archetypeArtifactId:** the artifact id of the actor filter archetype.
- **archetypeVersion:** the version of the actor filter archetype.

You'll then have to specify interactively the properties of your project: 

- **groupId:** the group id of your actor filter
- **artifactId:** the artifact id of your actor filter
	- Must match the following regex: `^[a-zA-Z0-9\-]+$`
	- Example: _my-actor-filter_
- **version:** the version of your actor filter _(default value: 1.0-SNAPSHOT)_
- **package** the package in which the actor filter source files will be created _(default value: the group id of the actor filter)_
- **bonitaVersion:** the targeted Bonita version
- **className:** the class name of your actor filter 
    - Must match the following regex: `^[a-zA-Z_$][a-zA-Z\d_$]+$` (A Java classname valid identifier)
    - Example: _MyActorfilter1_
- **language**: the language used in the actor filter project. Available values:
    - java
    - groovy
    - kotlin
- **wrapper** _(optional)_: install a [maven wrapper](https://maven.apache.org/wrapper/). Available values: 
    - true _(default)_
    - false

A folder named _[your artifact id]_ is created, with your Bonita actor filter project, ready to use.

⚠️ You can avoid the interactive mode by specifying all properties of your project directly in the command line, but by doing that you'll bypass the validation performed on the properties content.

## Building the archetype
The archetype can be installed in your local maven repository.

 1. Clone this project
 2. From a terminal, enter the following command at the root of the cloned project: 
```
./mvnw clean install
```

The archetype is now installed on your local maven repository, and is ready to be used.

## Contributing

Please sign the contributor license agreement and read our [contribution guidelines](CONTRIBUTING.md) before to open a pull request. 
 
<a href="https://cla-assistant.io/bonitasoft/bonita-actorfilter-archetype"><img src="https://cla-assistant.io/readme/badge/bonitasoft/bonita-actorfilter-archetype" alt="CLA assistant" /></a>

## Release this project

The GitHub Action [Release](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions/workflows/release.yml) is used to perform a release:

- This action is triggered manually, from the Actions tab
- It sets the release version, tags it, publishes the archetype to the Maven Central Portal, bumps to the next development version, pushes the branch and the tag, then creates the GitHub release with generated notes

So, to release a new version of the project, you have to:
- Open the [Release workflow](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions/workflows/release.yml) and click *Run workflow*
- Fill in the version to release (e.g. `1.2.1`) and the next development version (e.g. `1.2.2-SNAPSHOT`)
- Leave the `branch` input to `master`, unless you want to release from another branch

⚠️ The release is performed on the branch given by the `branch` input, not on the branch selected in the *Run workflow* dropdown (which only selects the version of the workflow file to run): that branch is the one checked out and built, tagged with the released version, and updated with the next development version.

⚠️ The deployment is not published automatically (`autoPublish` is set to `false` in the `pom.xml`): once the workflow succeeds, the deployment must be reviewed and published from the [Maven Central Portal](https://central.sonatype.com/publishing/deployments).

⚠️ Nothing is pushed until the deployment succeeded: the release commit, the next development version commit and the tag are all pushed in one go, near the end of the workflow. A run that fails before that step leaves the branch and the tags untouched, but the deployment may already exist in the Maven Central Portal.

⚠️ Make sure the version is final before running the workflow. If you have to fix something afterwards, then you must first:
- Delete the tag and the release on GitHub
- Revert the release and next development version commits on the branch
- Drop the deployment from our Maven Central Portal repository
