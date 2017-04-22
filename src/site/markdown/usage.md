# Usage

Just call the tool inside a Velocity template of a Maven skin to make use of it. The only requirements is setting up the custom tag in the site.xml file.

## Maven Site tools loader

Maven Site will load the tool automatically as long as it is a dependency on the project.

Try to use the latest Maven Site plugin version, as the tools won't work in all the versions due to various compatibility issues.

## Custom site node

Inside the custom node, in the site.xml file, a skinConfig node should be added. All the configuration data to be read with the use of the tool should be included there.

For example, this sets up the key words for the project:

```
<custom>
   <skinConfig>
      ...
      <keywords>Velocity tool, configuration</keywords>
      ...
   </skinConfig>
</custom>
```

Then that value can be acquired just by calling the tool.

## Calling the tools

The $config key will call the tool:

```
$config.keywords
```

### Getting values

By default the tool will call the get method, which will return a child from skinConfig with the same name.

In the previous example it will look for the keywords node, and return its content as a node object, allowing additional processing of the data.

## Usage examples

The [Docs Maven Skin][docs-skin] makes use of these tools, and can be a good example for them.

[tools]: ./tools.html

[docs-skin]: https://github.com/Bernardo-MG/docs-maven-skin