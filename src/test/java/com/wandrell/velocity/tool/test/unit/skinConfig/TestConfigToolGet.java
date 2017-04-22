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

package com.wandrell.velocity.tool.test.unit.skinConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.velocity.tools.ToolContext;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.velocity.tool.ConfigTool;
import com.wandrell.velocity.tool.ConfigToolConstants;

/**
 * Unit tests for {@link ConfigTool}, testing the {@code isTrue} method.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 * @see ConfigTool
 */
public final class TestConfigToolGet {

    /**
     * Default constructor.
     */
    public TestConfigToolGet() {
        super();
    }

    /**
     * Tests that the get method returns a node with the the expected key.
     */
    @Test
    public final void testGet_ExpectedKey() {
        final ConfigTool util; // Utilities class to test

        util = getSkinConfigUtils("key", "value");

        Assert.assertEquals(util.get("key").getName(), "key");
    }

    /**
     * Tests that the get method returns a node with the the expected value.
     */
    @Test
    public final void testGet_ExpectedValue() {
        final ConfigTool util; // Utilities class to test

        util = getSkinConfigUtils("key", "value");

        Assert.assertEquals(util.get("key").getValue(), "value");
    }

    /**
     * Tests that the get method returns a null for not existing values.
     */
    @Test
    public final void testGet_NotExisting_ReturnsNull() {
        final ConfigTool util; // Utilities class to test

        util = getSkinConfigUtils("", "");

        Assert.assertEquals(util.get("key"), null);
    }

    /**
     * Returns the utilities class being tested, set up for the tests.
     * 
     * @param key
     *            key for the value set
     * @param value
     *            value for the value set
     * @return the utilities class to test
     */
    private final ConfigTool getSkinConfigUtils(final String key,
            final String value) {
        final ConfigTool util;         // Utilities class to test
        final Map<Object, Object> map; // Configuration map
        final ToolContext context;     // Velocity context
        final DecorationModel deco;    // Decoration model
        final Xpp3Dom customNode;      // <custom> node
        final Xpp3Dom skinNode;        // <skinConfig> node
        final Xpp3Dom valueNode;       // Node with the test value
        final String currentFile;      // Current page

        currentFile = "page";

        // Creates test value node
        valueNode = new Xpp3Dom(key);
        valueNode.setValue(value);

        // Creates skin node
        skinNode = new Xpp3Dom(ConfigToolConstants.SKIN_KEY);
        skinNode.addChild(valueNode);

        // Creates custom data node
        customNode = new Xpp3Dom("custom");
        customNode.addChild(skinNode);

        // Creates decoration model
        deco = Mockito.mock(DecorationModel.class);
        Mockito.when(deco.getCustom()).thenReturn(customNode);

        // Creates utilities class
        util = new ConfigTool();

        // Creates context
        context = new ToolContext();
        context.put(ConfigToolConstants.DECORATION_KEY, deco);
        context.put(ConfigToolConstants.CURRENT_FILE_NAME_KEY, currentFile);

        // Prepares configuration
        map = new HashMap<>();
        map.put(ConfigToolConstants.VELOCITY_CONTEXT_KEY, context);

        util.configure(map);

        return util;
    }

}
