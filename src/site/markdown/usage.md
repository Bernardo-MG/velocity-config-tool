# Usage

Just call the tool inside a Velocity template of a Maven skin to make use of it.

The only requirement is setting up the custom tag in the site.xml file.

## Maven Site tools loader

Maven Site will load the tool automatically, as long as it is a dependency on the project.

Try to use the latest Maven Site plugin version, as the tools won't work in all the versions due to various compatibility issues.

## Custom site node

Configuration values should be added to the site.xml file, inside the custom node in a node named skinConfig.

For example, this sets up the key words for the project:

```
<project>
   ...
   <custom>
      <skinConfig>
         ...
         <keywords>Velocity tool, configuration</keywords>
         ...
      </skinConfig>
   </custom>
   ...
</project>
```

## Calling the tool

The $config key will call the tool. For example, this returns the keywords set in the skin configuration:

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

Note that the values are returned as nodes, allowing nested configurations.

### File id

As a special case, the tool can return a slugiffied version of the current file name through the fileId field:

```
$config.fileId
```

This will be a string, instead of a node.

[tools]: ./tools.html

[docs-skin]: https://github.com/Bernardo-MG/docs-maven-skin