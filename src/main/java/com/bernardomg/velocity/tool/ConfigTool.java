/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2021 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.velocity.tool;

import java.util.Locale;
import java.util.Objects;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.SafeConfig;
import org.apache.velocity.tools.generic.ValueParser;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * Utilities class to ease using custom Maven Site configuration values through Velocity.
 * <p>
 * The configuration values should be in the site.xml file, inside a {@code
 * <skinConfig>}, itself inside the {@code <custom>} element, like this:
 *
 * <pre>
 * {@code <project>
 *   <custom>
 *      <skinConfig></skinConfig>
 *   </custom>
 * </project>}
 * </pre>
 * <p>
 * Any value stored there can be acquired through the use of the {@link #get(String) get} method. Note that thanks to
 * Velocity inside a template any getter can be replaced by the field it returns, and this works with the {@code get}
 * method too.
 * <p>
 * This means that instead of using {@code $config.get("myproperty")}, the same value can be acquired with
 * {@code $config.myproperty}.
 * <p>
 * This tool is stateful, as it binds itself to the context and data of the page being rendered.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@DefaultKey("config")
public final class ConfigTool extends SafeConfig {

    /**
     * Identifier for the current file.
     * <p>
     * This is a slug created from the current file's name.
     */
    private String  fileId;

    /**
     * Skin configuration node.
     * <p>
     * This contains the custom configuration for the skin, as set inside the site.xml file, inside the {@code <custom>}
     * node.
     */
    private Xpp3Dom skinConfig = new Xpp3Dom("");

    /**
     * Constructs an instance of the {@code ConfigTool}.
     */
    public ConfigTool() {
        super();
    }

    /**
     * Returns a configuration's node property.
     * <p>
     * This node will be acquired from the custom skin configuration inside the site.xml file. If there is no node with
     * a matching name then the returned value will be {@code null}.
     *
     * @param property
     *            the property being acquired
     * @return the value assigned to the property in the skin custom configuration
     */
    public final Xpp3Dom get(final String property) {
        Objects.requireNonNull(property, "Received a null pointer as property");

        return skinConfig.getChild(property);
    }

    /**
     * Returns the file identifier.
     * <p>
     * This is a slugged version of the current file name.
     * <p>
     * With Velocity the value can be acquired by using the command {@code $config.fileId}.
     *
     * @return the file identifier
     */
    public final String getFileId() {
        return fileId;
    }

    /**
     * Returns the skin configuration node.
     * <p>
     * This contains the custom configuration for the skin, as set inside the site.xml file, inside the {@code <custom>}
     * node.
     *
     * @return the skin configuration node
     */
    public final Xpp3Dom getSkinConfig() {
        return skinConfig;
    }

    /**
     * Sets the file identifier.
     * <p>
     * This is a slugged version of the current file name.
     *
     * @param id
     *            file identifier
     */
    public final void setFileId(final String id) {
        fileId = id;
    }

    /**
     * Sets the skin configuration node.
     * <p>
     * This contains the custom configuration for the skin, as set inside the site.xml file, inside the {@code <custom>}
     * node.
     *
     * @param config
     *            skin configuration node
     */
    public final void setSkinConfig(final Xpp3Dom config) {
        skinConfig = config;
    }

    /**
     * Loads the file identifier from the velocity tools context.
     * <p>
     * This is generated from the file's name.
     *
     * @param context
     *            the Velocity tools context
     */
    private final void loadFileId(final ToolContext context) {
        final Integer lastDot;
        final Object  currentFileObj;
        final String  id;
        String        currentFile;

        if (context.containsKey(ConfigToolKeys.CURRENT_FILE_NAME)) {
            currentFileObj = context.get(ConfigToolKeys.CURRENT_FILE_NAME);
            if (currentFileObj == null) {
                id = "";
            } else {
                currentFile = String.valueOf(currentFileObj);

                // Drops the extension
                lastDot = currentFile.lastIndexOf('.');
                if (lastDot >= 0) {
                    currentFile = currentFile.substring(0, lastDot);
                }

                // File name is slugged
                id = slug(currentFile);
            }
        } else {
            id = "";
        }

        fileId = id;
    }

    /**
     * Processes the decoration model, acquiring the skin and page configuration.
     * <p>
     * The decoration model are the contents of the site.xml file.
     *
     * @param model
     *            decoration data
     */
    private final void processDecoration(final DecorationModel model) {
        final Object  customObj;
        final Xpp3Dom customNode;
        final Xpp3Dom skinNode;

        customObj = model.getCustom();

        if (customObj instanceof Xpp3Dom) {
            // This is the <custom> node in the site.xml file

            customNode = (Xpp3Dom) customObj;

            // Acquires <skinConfig> node
            skinNode = customNode.getChild(ConfigToolKeys.SKIN);

            if (skinNode == null) {
                skinConfig = new Xpp3Dom("");
            } else {
                skinConfig = skinNode;
            }
        }
    }

    /**
     * Returns a URL slug created from the received text.
     * <p>
     * A slug is a human-readable version of the text, where all the special characters have been removed, and spaces
     * have been swapped by dashes.
     * <p>
     * For example: <em>This, That & the Other! Various Outré Considerations</em> would become
     * <em>this-that-the-other-various-outre-considerations</em>
     * <p>
     * Of course, this can be applied to any text, not just URLs, but it is usually used in the context of an URL.
     *
     * @param text
     *            text to generate the slug from
     * @return the slug of the given text
     */
    private final String slug(final String text) {
        final String separator; // Separator for swapping whitespaces
        String       corrected; // Modified string

        Objects.requireNonNull(text, "Received a null pointer as the text");

        separator = "-";

        corrected = text.replace('/', '-')
            .replace('\\', '-')
            .replace('.', '-')
            .replace('_', '-');

        // Removes multiple lines
        corrected = ConfigToolRegex.MULTIPLE_HYPHEN.matcher(corrected)
            .replaceAll(separator);
        // Removes white spaces
        corrected = ConfigToolRegex.WHITESPACE.matcher(corrected)
            .replaceAll(separator);
        // Removes non-latin characters
        corrected = ConfigToolRegex.NON_LATIN.matcher(corrected)
            .replaceAll("");

        return corrected.toLowerCase(Locale.getDefault());
    }

    /**
     * Sets up the tool with the skin configuration and file id.
     */
    @Override
    protected final void configure(final ValueParser values) {
        final Object      velocityContext; // Value from the parser
        final ToolContext ctxt;            // Casted context
        final Object      decorationObj;   // Value of the decoration key

        Objects.requireNonNull(values, "Received a null pointer as values");

        velocityContext = values.get(ConfigToolKeys.VELOCITY_CONTEXT);

        if (velocityContext instanceof ToolContext) {
            ctxt = (ToolContext) velocityContext;

            loadFileId(ctxt);

            decorationObj = ctxt.get(ConfigToolKeys.DECORATION);
            if (decorationObj instanceof DecorationModel) {
                processDecoration((DecorationModel) decorationObj);
            }
        }
    }

}
