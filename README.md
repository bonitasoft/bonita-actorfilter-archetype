[![Actions Status](https://github.com/bonitasoft/bonita-actorfilter-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions)

[![Release](https://github.com/bonitasoft/bonita-actorfilter-archetype/workflows/Release/badge.svg)](https://github.com/bonitasoft/bonita-actorfilter-archetype/actions)

## Bonita Actor filter Archetype

This project contains a maven archetype, which allow to easily setup a Bonita actor filter project.

### Install the archetype
The archetype has to be installed on your local maven repository (not available on maven central for now).

 1. Clone this project
 2. From a terminal, enter the following command at the root of the cloned project: 
```
./mvnw clean install
```

The archetype is now installed on your local maven repository, and is ready to be used.

### Setup an extension project using the archetype 

 You can setup a Bonita actor filter project using the following command, from a terminal: 
 
 _Make sure that you do not launch the command from an existing maven project._
 
```
mvn archetype:generate -DarchetypeGroupId=org.bonitasoft.archetypes -DarchetypeArtifactId=bonita-actorfilter-archetype -DarchetypeVersion=1.0.0-SNAPSHOT
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
- **wrapper** _(optional)_: install a [maven wrapper](https://github.com/takari/maven-wrapper). Available values: 
    - true _(default)_
    - false

A folder named _[your artifact id]_ is created, with your Bonita actor filter project, ready to use.

⚠️ You can avoid the interactive mode by specifying all properties of your project directly in the command line, but by doing that you'll bypass the validation performed on the properties content.
