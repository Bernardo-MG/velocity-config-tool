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

package com.bernardomg.velocity.tool.test.unit.skinConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.tools.ToolContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bernardomg.velocity.tool.ConfigTool;
import com.bernardomg.velocity.tool.ConfigToolConstants;

/**
 * Unit tests for {@link ConfigTool}, testing the {@code getFileId} method.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 * @see ConfigTool
 */
public final class TestConfigToolGetFileId {

    /**
     * Default constructor.
     */
    public TestConfigToolGetFileId() {
        super();
    }

    /**
     * Tests that a file with consecutive points gives a slugged file id.
     */
    @Test
    public final void testGetFileId_ConsecutivePoints_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name..something.html");

        Assert.assertEquals(util.getFileId(), "path-to-file-name-something");
    }

    /**
     * Tests that an empty file gives an empty file id.
     */
    @Test
    public final void testGetFileId_EmptyFile_EmptyId() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("");

        Assert.assertEquals(util.getFileId(), "");
    }

    /**
     * Tests that a file with multiple points gives a slugged file id.
     */
    @Test
    public final void testGetFileId_MultiplePoints_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name.something.html");

        Assert.assertEquals(util.getFileId(), "path-to-file-name-something");
    }

    /**
     * Tests that a file with multiple line separators gives a slugged file id.
     */
    @Test
    public final void testGetFileId_MultipleSeparators_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name---something.html");

        Assert.assertEquals(util.getFileId(), "path-to-file-name-something");
    }

    /**
     * Tests that a file without extension gives a slugged file id.
     */
    @Test
    public final void testGetFileId_NoExtension_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name");

        Assert.assertEquals(util.getFileId(), "path-to-file-name");
    }

    /**
     * Tests that a null file gives an empty file id.
     */
    @Test
    public final void testGetFileId_NullFile_EmptyId() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool(null);

        Assert.assertEquals(util.getFileId(), "");
    }

    /**
     * Tests that a file with only an extension gives an empty id.
     */
    @Test
    public final void testGetFileId_OnlyExtension_Empty() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool(".html");

        Assert.assertEquals(util.getFileId(), "");
    }

    /**
     * Tests that a valid file gives a slugged file id.
     */
    @Test
    public final void testGetFileId_ValidFile_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name.html");

        Assert.assertEquals(util.getFileId(), "path-to-file-name");
    }

    /**
     * Returns the utilities class being tested, set up for the tests.
     * 
     * @param fileName
     *            name of the current file
     * @return the utilities class to test
     */
    private final ConfigTool getConfigTool(final String fileName) {
        final ConfigTool util;         // Utilities class to test
        final Map<Object, Object> map; // Configuration map
        final ToolContext context;     // Velocity context

        util = new ConfigTool();

        context = new ToolContext();
        context.put(ConfigToolConstants.CURRENT_FILE_NAME_KEY, fileName);

        map = new HashMap<>();
        map.put(ConfigToolConstants.VELOCITY_CONTEXT_KEY, context);

        util.configure(map);

        return util;
    }

}
