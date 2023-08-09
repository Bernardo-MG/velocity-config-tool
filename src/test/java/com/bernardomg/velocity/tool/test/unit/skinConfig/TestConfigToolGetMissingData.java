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
@DisplayName("Getting configuration with missing data")
public final class TestConfigToolGetMissingData {

    @Mock
    private DecorationModel deco;

    /**
     * Default constructor.
     */
    public TestConfigToolGetMissingData() {
        super();
    }

    @Test
    @DisplayName("When missing skin configuration no data is returned")
    public final void testGet_NoSkinConfig_ReturnsNull() {
        final ConfigTool util; // Utilities class to test

        util = getConfigToolNoSkinNode();

        Assert.assertNull(util.get("key"));
    }

    /**
     * Returns the utilities class being tested, set up for the tests.
     * <p>
     * The contained configuration is missing the skin config node.
     *
     * @return the utilities class to test
     */
    private final ConfigTool getConfigToolNoSkinNode() {
        final ConfigTool          util;        // Utilities class to test
        final Map<String, Object> map;         // Configuration map
        final ToolContext         context;     // Velocity context
        final Xpp3Dom             customNode;  // <custom> node
        final String              currentFile; // Current page

        currentFile = "page";

        // Creates custom data node
        customNode = new Xpp3Dom("custom");

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
