# Examples

## String value

Setting up a simple string value in the skin configuration is very straightforward.

This sets up the keywords for the site:

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

And this prints those keywords into the Velocity template.

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

## Nested value

The tool returns nodes, allowing nested configurations.

This sets the menus for the page navigation:

```
<project>
   ...
   <custom>
      <skinConfig>
         ...
         <topNav>
            <menu>Documentation</menu>
            <menu>Info and reports</menu>
         </topNav>
         ...
      </skinConfig>
   </custom>
   ...
</project>
```

And then you can iterate over those values:

```
#foreach( $menu in $config.topNav )
```

## Usage examples

The [Docs Maven Skin][docs-skin] makes use of these tools, and can be a good example for them.


[docs-skin]: https://github.com/Bernardo-MG/docs-maven-skin