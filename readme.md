# Velocity Config Tool

Acquire the [Maven site skin][maven_site] configuration from a [Velocity][velocity] template.

This way any custom setting from the site.xml file can be used when generating the site pages.

[![Maven Central](https://img.shields.io/maven-central/v/com.wandrell.velocity/velocity-config-tool.svg)][maven-repo]
[![Bintray](https://api.bintray.com/packages/bernardo-mg/maven/velocity-config-tool/images/download.svg)][bintray-repo]

[![Release docs](https://img.shields.io/badge/docs-release-blue.svg)][site-release]
[![Development docs](https://img.shields.io/badge/docs-develop-blue.svg)][site-develop]

[![Release javadocs](https://img.shields.io/badge/javadocs-release-blue.svg)][javadoc-release]
[![Development javadocs](https://img.shields.io/badge/javadocs-develop-blue.svg)][javadoc-develop]

## Features

- No additional configuration, just add the dependency
- Read Maven Skin data from the custom tag inside a Velocity template
- Read general Maven Skin data, such as the current page id, easily inside a Velocity template

## Documentation

Documentation is always generated for the latest release, kept in the 'master' branch:

- The [latest release documentation page][site-release].
- The [the latest release Javadoc site][javadoc-release].

Documentation is also generated from the latest snapshot, taken from the 'develop' branch:

- The [the latest snapshot documentation page][site-develop].
- The [the latest snapshot Javadoc site][javadoc-develop].

### Building the docs

The documentation site sources come along the source code (as it is a Maven site), so it is always possible to generate them using the following Maven command:

```
$ mvn verify site
```

The verify phase is required, as otherwise some of the reports won't be created.

## Acknowledgement

The code comes from adapting the tools includes inside the [Reflow Maven Skin][reflow-skin].

## Usage

The tools are meant to be used through Velocity, by making use of Maven Site autofinder feature. Just include the project as a dependency on any Maven Skin, and then the tool will be accessible.

Having a keywords tag set as follows in the site.xml:

```
<custom>
   <skinConfig>
      ...
      <keywords>Velocity tool, configuration</keywords>
      ...
   </skinConfig>
</custom>
```

Just invoke the tool like this:

```
<html>
   <head>
      ...
      <meta name="keywords" content="$config.keywords.getValue()">
      ...
   </head>
   ...
<html>
```

And the keywords will be set into the template.

More information can be found in the documentation pages.

### Prerequisites

The project has been tested on the following Java versions:
* JDK 7
* JDK 8
* OpenJDK 7

All other dependencies are handled through Maven, and noted in the included POM file.

### Installing

The recommended way to install the project is by setting up your preferred dependencies manager. To get the configuration information for this check the [Bintray repository][bintray-repo], or the [Maven Central Repository][maven-repo].

If for some reason manual installation is necessary, just use the following Maven command:

```
$ mvn install
```

## Collaborate

Any kind of help with the project will be well received, and there are two main ways to give such help:

- Reporting errors and asking for extensions through the issues management
- or forking the repository and extending the project

### Issues management

Issues are managed at the GitHub [project issues tracker][issues], where any Github user may report bugs or ask for new features.

### Getting the code

If you wish to fork or modify the code, visit the [GitHub project page][scm], where the latest versions are always kept. Check the 'master' branch for the latest release, and the 'develop' for the current, and stable, development version.

## License

The project has been released under the [MIT License][license].

[maven_site]: https://maven.apache.org/plugins/maven-site-plugin/
[reflow-skin]: https://github.com/andriusvelykis/reflow-maven-skin
[velocity]: http://velocity.apache.org/

[bintray-repo]: https://bintray.com/bernardo-mg/maven/velocity-config-tool/view
[maven-repo]: http://mvnrepository.com/artifact/com.wandrell.velocity/velocity-config-tool
[issues]: https://github.com/bernardo-mg/velocity-config-tool/issues
[javadoc-develop]: http://docs.wandrell.com/development/maven/velocity-config-tool/apidocs
[javadoc-release]: http://docs.wandrell.com/maven/velocity-config-tool/apidocs
[license]: http://www.opensource.org/licenses/mit-license.php
[scm]: https://github.com/bernardo-mg/velocity-config-tool
[site-develop]: http://docs.wandrell.com/development/maven/velocity-config-tool
[site-release]: http://docs.wandrell.com/maven/velocity-config-tool
