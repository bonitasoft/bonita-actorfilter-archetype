[![Actions Status](https://github.com/bonitasoft/bonita-actorfilter-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions?query=workflow%3ABuild)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-actorfilter-archetype?color=blue&label=Release&include_prereleases)](https://github.com/bonitasoft/bonita-actorfilter-archetype/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.archetypes/bonita-actorfilter-archetype.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.archetypes%22%20AND%20a:%22bonita-actorfilter-archetype%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# Bonita Actor filter Archetype

This project contains a maven archetype, which allow to easily setup a Bonita actor filter project.

## Setup an extension project using the archetype 

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
- **wrapper** _(optional)_: install a [maven wrapper](https://github.com/takari/maven-wrapper). Available values: 
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

A github action is used to perform release : 

[![Actions Status](https://github.com/bonitasoft/bonita-actorfilter-archetype/workflows/Create%20release/badge.svg)](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions?query=workflow%3A"Create+release")

- This action is triggered when a push is performed on a branch 'release-xxx'
- It generates the changelog since the last release, creates the github tag and release with the changelog as description, and push the release on our nexus repository. 

So, to release a new version of the project, you have to: 
- Create a branch release-[version] on your local git repository
- Update the version in the pom.xml (remove the -SNAPSHOT)
- Push the branch

⚠️ Make sur that the release branch is final before to push it. If you have to update something on the release branch after the push, then you must first:
- Delete the tag and the release on github
- Remove the artifact from our nexus repository 
