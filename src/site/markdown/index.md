# Velocity Config Tool

Acquire the [Maven site skin][maven_site] configuration from a [Velocity][velocity] template.

This way any custom setting from the site.xml file can be used when generating the site pages.

It was created to be used by [Docs Maven Skin][docs-maven-skin].

## Dependant projects

The tools were developed to be used by the [Docs Maven Skin][docs-skin].

## Usage

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

[docs-skin]: https://github.com/Bernardo-MG/docs-maven-skin
[maven_site]: https://maven.apache.org/plugins/maven-site-plugin/
[velocity]: http://velocity.apache.org/

[docs-maven-skin]: https://github.com/Bernardo-MG/docs-maven-skin
