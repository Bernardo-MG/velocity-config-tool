# Customizing

## Maven Site tools loader

The configuration file for autoloading is src\main\resources\META-INF\maven\site-tools.xml. There is no need to touch this file unless the tool class is renamed or moved.

## Custom site node

Currently the name of the configuration node is defined inside the ConfigToolConstants class, in the SKIN_KEY field.

## Calling the tools

The tool key, used to call it inside Velocity templates, is defined in the DefaultKey annotation, which is marking the ConfigTool class.

[tools]: ./tools.html

[docs-skin]: https://github.com/Bernardo-MG/docs-maven-skin