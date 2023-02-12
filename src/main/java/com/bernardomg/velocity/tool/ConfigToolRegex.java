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

import java.util.regex.Pattern;

/**
 * Constants for regular expressions used by the configuration tool.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class ConfigToolRegex {

    /**
     * Regex for multiple hyphens.
     */
    public static final Pattern MULTIPLE_HYPHEN = Pattern.compile("-+");

    /**
     * Regex for non-latin characters.
     */
    public static final Pattern NON_LATIN       = Pattern.compile("[^\\w-]");

    /**
     * Regex for whitespaces.
     */
    public static final Pattern WHITESPACE      = Pattern.compile("[\\s]");

    /**
     * Private constructor to avoid initialization.
     */
    private ConfigToolRegex() {
        super();
    }

}
