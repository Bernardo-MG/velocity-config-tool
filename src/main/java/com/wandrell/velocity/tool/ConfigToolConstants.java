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

import org.apache.velocity.tools.config.DefaultKey;

/**
 * Constants for the configuration tool.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@DefaultKey("config")
public final class ConfigToolConstants {

    /**
     * The key identifying the current file name in the velocity context.
     */
    public static final String CURRENT_FILE_NAME_KEY = "currentFileName";

    /**
     * The key identifying the decoration in the velocity context.
     */
    public static final String DECORATION_KEY        = "decoration";

    /**
     * The key identifying the Maven project.
     */
    public static final String MAVEN_PROJECT_KEY     = "project";

    /**
     * Key for the skin configuration.
     * <p>
     * This is the name of the node inside the site.xml file where the skin's
     * configuration is stored.
     * <p>
     * It will be a node inside the custom node, with the project node at the
     * root, like this:
     * 
     * <pre>
     * {@code <project>
     *   <custom>
     *      <skinConfig></skinConfig>
     *   </custom>
     * </project>}
     * </pre>
     * <p>
     * That is, if the default value of skinConfig is kept.
     */
    public static final String SKIN_KEY              = "skinConfig";

    /**
     * The key identifying the velocity context.
     */
    public static final String VELOCITY_CONTEXT_KEY  = "velocityContext";

    /**
     * Private constructor to avoid initialization.
     */
    private ConfigToolConstants() {
        super();
    }

}
