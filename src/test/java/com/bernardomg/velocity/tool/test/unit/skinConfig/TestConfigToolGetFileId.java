/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2023 the original author or authors.
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
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.velocity.tool.ConfigTool;
import com.bernardomg.velocity.tool.ConfigToolKeys;

@DisplayName("Get file ID")
public final class TestConfigToolGetFileId {

    /**
     * Default constructor.
     */
    public TestConfigToolGetFileId() {
        super();
    }

    @Test
    @DisplayName("Consecutive points are transformed when slugging")
    public final void testGetFileId_ConsecutivePoints_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name..something.html");

        Assert.assertEquals("path-to-file-name-something", util.getFileId());
    }

    @Test
    @DisplayName("An empty file gives an empty slug")
    public final void testGetFileId_EmptyFile_EmptyId() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("");

        Assert.assertEquals("", util.getFileId());
    }

    @Test
    @DisplayName("Points are transformed when slugging")
    public final void testGetFileId_MultiplePoints_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name.something.html");

        Assert.assertEquals("path-to-file-name-something", util.getFileId());
    }

    @Test
    @DisplayName("Consecutive separators are transformed when slugging")
    public final void testGetFileId_MultipleSeparators_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name---something.html");

        Assert.assertEquals("path-to-file-name-something", util.getFileId());
    }

    @Test
    @DisplayName("Files with no extension are slugged")
    public final void testGetFileId_NoExtension_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name");

        Assert.assertEquals("path-to-file-name", util.getFileId());
    }

    @Test
    @DisplayName("A null file gives an empty slug")
    public final void testGetFileId_NullFile_EmptyId() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool(null);

        Assert.assertEquals("", util.getFileId());
    }

    @Test
    @DisplayName("A file with only an extension gives an empty slug")
    public final void testGetFileId_OnlyExtension_Empty() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool(".html");

        Assert.assertEquals("", util.getFileId());
    }

    @Test
    @DisplayName("A valid file gives a slugged file ID")
    public final void testGetFileId_ValidFile_Slugged() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("path-to\\file_name.html");

        Assert.assertEquals("path-to-file-name", util.getFileId());
    }

    /**
     * Returns the utilities class being tested, set up for the tests.
     *
     * @param fileName
     *            name of the current file
     * @return the utilities class to test
     */
    private final ConfigTool getConfigTool(final String fileName) {
        final ConfigTool          util;    // Utilities class to test
        final Map<String, Object> map;     // Configuration map
        final ToolContext         context; // Velocity context

        util = new ConfigTool();

        context = new ToolContext();
        context.put(ConfigToolKeys.CURRENT_FILE_NAME, fileName);

        map = new HashMap<>();
        map.put(ConfigToolKeys.VELOCITY_CONTEXT, context);

        util.configure(map);

        return util;
    }

}
