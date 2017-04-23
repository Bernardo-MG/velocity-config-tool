/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 the original author or authors.
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

package com.wandrell.velocity.tool;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.generic.SafeConfig;
import org.apache.velocity.tools.generic.ValueParser;
import org.codehaus.plexus.util.xml.Xpp3Dom;

/**
 * Utilities class to ease using custom Maven Site configuration values through
 * Velocity.
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
 * Any value stored there can be acquired through the use of the
 * {@link #get(String) get} method.
 * <p>
 * This tool is stateful, as it binds itself to the context and data of the page
 * being rendered.
 * <p>
 * This class has been created from the Skin Config Tool class from the
 * <a href="http://andriusvelykis.github.io/reflow-maven-skin/">Reflow Maven
 * Skin</a>.
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
    private String        fileId;

    /**
     * Regex for multiple hyphens.
     */
    private final Pattern multipleHyphen = Pattern.compile("-+");

    /**
     * Regex for non-latin characters.
     */
    private final Pattern nonLatin       = Pattern.compile("[^\\w-]");

    /**
     * Skin configuration node.
     * <p>
     * This is the {@code <skinConfig>} located in the site.xml file, inside the
     * {@code <custom>} node.
     */
    private Xpp3Dom       skinConfig     = new Xpp3Dom("");

    /**
     * Regex for whitespaces.
     */
    private final Pattern whitespace     = Pattern.compile("[\\s]");

    /**
     * Constructs an instance of the {@code ConfigTool}.
     */
    public ConfigTool() {
        super();
    }

    /**
     * Returns a configuration's node property.
     * <p>
     * This will be the data on the site.xml file where the node is called like
     * the property.
     * <p>
     * Thanks to Velocity, instead of using {@code $config.get("myproperty")},
     * this method can be called as a getter by using {@code $config.myproperty}
     * .
     * <p>
     * The method will look for the property first in the page configuration. If
     * it is not found there, then it looks for it in the global configuration.
     * If again it is not found, then the {@code null} value is returned.
     * 
     * @param property
     *            the property being queried
     * @return the value assigned to the property in the page or the global
     *         properties
     */
    public final Xpp3Dom get(final String property) {
        Xpp3Dom value; // Node with the property's value

        checkNotNull(property, "Received a null pointer as property");

        // Looks for it in the global properties
        value = getSkinConfig().getChild(property);

        return value;
    }

    /**
     * Returns the file identifier.
     * <p>
     * This is the slugged current file name.
     * <p>
     * It can be called through Velocity with the command {@code $config.fileId}
     * .
     * 
     * @return the file identifier
     */
    public final String getFileId() {
        return fileId;
    }

    /**
     * Returns the boolean value of a property's value.
     * <p>
     * This will transform whatever value the property has assigned to a
     * boolean.
     * 
     * @param property
     *            the property to check
     * @return the property's value transformed to a boolean
     */
    public final Boolean isTrue(final String property) {
        final Xpp3Dom value;  // Node with the property's value
        final Boolean result; // Value transformed to a boolean

        checkNotNull(property, "Received a null pointer as property");

        value = get(property);

        if (value == null) {
            result = false;
        } else {
            result = Boolean.valueOf(value.getValue());
        }

        return result;
    }

    /**
     * Returns the regular expression for multiple hyphens.
     * 
     * @return the regular expression for multiple hyphens
     */
    private final Pattern getMultipleHyphenPattern() {
        return multipleHyphen;
    }

    /**
     * Returns the non-latin characters regular expression.
     * 
     * @return the non-latin characters regular expression
     */
    private final Pattern getNonLatinPattern() {
        return nonLatin;
    }

    /**
     * Returns the skin config node.
     * 
     * @return the skin config node
     */
    private final Xpp3Dom getSkinConfig() {
        return skinConfig;
    }

    /**
     * Returns the regular expression for whitespaces.
     * 
     * @return the regular expression for whitespaces
     */
    private final Pattern getWhitespacePattern() {
        return whitespace;
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
        final Integer lastDot;       // Location of the extension dot
        final Object currentFileObj; // File's name as received
        String currentFile;          // File's name

        if (context.containsKey(ConfigToolConstants.CURRENT_FILE_NAME_KEY)) {
            currentFileObj = context
                    .get(ConfigToolConstants.CURRENT_FILE_NAME_KEY);
            if (currentFileObj == null) {
                setFileId("");
            } else {
                currentFile = String.valueOf(currentFileObj);

                // Drops the extension
                lastDot = currentFile.lastIndexOf('.');
                if (lastDot >= 0) {
                    currentFile = currentFile.substring(0, lastDot);
                }

                // File name is slugged
                setFileId(slug(currentFile));
            }
        } else {
            setFileId("");
        }
    }

    /**
     * Processes the decoration model, acquiring the skin and page
     * configuration.
     * <p>
     * The decoration model are the contents of the site.xml file.
     * 
     * @param model
     *            decoration data
     */
    private final void processDecoration(final DecorationModel model) {
        final Object customObj;   // Object for the <custom> node
        final Xpp3Dom customNode; // <custom> node
        final Xpp3Dom skinNode;   // <skinConfig> node

        customObj = model.getCustom();

        if (customObj instanceof Xpp3Dom) {
            // This is the <custom> node in the site.xml file

            customNode = (Xpp3Dom) customObj;

            // Acquires <skinConfig> node
            skinNode = customNode.getChild(ConfigToolConstants.SKIN_KEY);

            if (skinNode == null) {
                setSkinConfig(new Xpp3Dom(""));
            } else {
                setSkinConfig(skinNode);
            }
        }
    }

    /**
     * Sets the identifier for the current file.
     * 
     * @param id
     *            the identifier for the current file
     */
    private final void setFileId(final String id) {
        fileId = id;
    }

    /**
     * Sets the skin config node.
     * 
     * @param config
     *            the skin config node.
     */
    private final void setSkinConfig(final Xpp3Dom config) {
        skinConfig = config;
    }

    /**
     * Returns a URL slug created from the received text.
     * <p>
     * A slug is a human-readable version of the text, where all the special
     * characters have been removed, and spaces have been swapped by dashes.
     * <p>
     * For example: <em>This, That & the Other! Various Outré
     * Considerations</em> would become
     * <em>this-that-the-other-various-outre-considerations</em>
     * <p>
     * Of course, this can be applied to any text, not just URLs, but it is
     * usually used in the context of an URL.
     * 
     * @param text
     *            text to generate the slug from
     * @return the slug of the given text
     */
    private final String slug(final String text) {
        final String separator; // Separator for swapping whitespaces
        String corrected;       // Modified string

        checkNotNull(text, "Received a null pointer as the text");

        separator = "-";

        corrected = text.replace('/', '-').replace('\\', '-').replace('.', '-')
                .replace('_', '-');

        // Removes multiple lines
        corrected = getMultipleHyphenPattern().matcher(corrected)
                .replaceAll(separator);
        // Removes white spaces
        corrected = getWhitespacePattern().matcher(corrected)
                .replaceAll(separator);
        // Removes non-latin characters
        corrected = getNonLatinPattern().matcher(corrected).replaceAll("");

        return corrected.toLowerCase();
    }

    @Override
    protected final void configure(final ValueParser values) {
        final Object velocityContext; // Value from the parser
        final ToolContext ctxt;       // Casted context
        final Object decorationObj;   // Value of the decoration key

        checkNotNull(values, "Received a null pointer as values");

        velocityContext = values.get(ConfigToolConstants.VELOCITY_CONTEXT_KEY);

        if (velocityContext instanceof ToolContext) {
            ctxt = (ToolContext) velocityContext;

            loadFileId(ctxt);

            decorationObj = ctxt.get(ConfigToolConstants.DECORATION_KEY);
            if (decorationObj instanceof DecorationModel) {
                processDecoration((DecorationModel) decorationObj);
            }
        }
    }

}
