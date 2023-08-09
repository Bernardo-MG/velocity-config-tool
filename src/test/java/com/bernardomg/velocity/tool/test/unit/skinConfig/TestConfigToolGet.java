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

import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.doxia.site.decoration.DecorationModel;
import org.apache.velocity.tools.ToolContext;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.velocity.tool.ConfigTool;
import com.bernardomg.velocity.tool.ConfigToolKeys;

@ExtendWith(MockitoExtension.class)
@DisplayName("Getting configuration")
public final class TestConfigToolGet {

    @Mock
    private DecorationModel deco;

    /**
     * Default constructor.
     */
    public TestConfigToolGet() {
        super();
    }

    @Test
    @DisplayName("Returns the expected key")
    public final void testGet_ExpectedKey() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("key", "value");

        Assert.assertEquals("key", util.get("key")
            .getName());
    }

    @Test
    @DisplayName("Returns the expected value")
    public final void testGet_ExpectedValue() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("key", "value");

        Assert.assertEquals("value", util.get("key")
            .getValue());
    }

    @Test
    @DisplayName("When no data exists for the key a null is returned")
    public final void testGet_NotExisting_ReturnsNull() {
        final ConfigTool util; // Utilities class to test

        util = getConfigTool("", "");

        Assert.assertNull(util.get("key"));
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
    private final ConfigTool getConfigTool(final String key, final String value) {
        final ConfigTool          util;        // Utilities class to test
        final Map<String, Object> map;         // Configuration map
        final ToolContext         context;     // Velocity context
        final Xpp3Dom             customNode;  // <custom> node
        final Xpp3Dom             skinNode;    // <skinConfig> node
        final Xpp3Dom             valueNode;   // Node with the test value
        final String              currentFile; // Current page

        currentFile = "page";

        // Creates test value node
        valueNode = new Xpp3Dom(key);
        valueNode.setValue(value);

        // Creates skin node
        skinNode = new Xpp3Dom(ConfigToolKeys.SKIN);
        skinNode.addChild(valueNode);

        // Creates custom data node
        customNode = new Xpp3Dom("custom");
        customNode.addChild(skinNode);

        // Creates decoration model
        given(deco.getCustom()).willReturn(customNode);

        // Creates utilities class
        util = new ConfigTool();

        // Creates context
        context = new ToolContext();
        context.put(ConfigToolKeys.DECORATION, deco);
        context.put(ConfigToolKeys.CURRENT_FILE_NAME, currentFile);

        // Prepares configuration
        map = new HashMap<>();
        map.put(ConfigToolKeys.VELOCITY_CONTEXT, context);

        util.configure(map);

        return util;
    }

}
