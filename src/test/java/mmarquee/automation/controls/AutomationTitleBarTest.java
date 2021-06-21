/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.controls;

import mmarquee.automation.Element;
import mmarquee.automation.pattern.ItemContainer;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Mark Humphreys
 * Date 03/12/2016.
 *
 * Tests for TitleBar.
 */
public class AutomationTitleBarTest {

    @BeforeClass
    public static void checkOs() throws Exception {
        Assume.assumeTrue(isWindows());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
    @Test
    public void testName_Returns_Name_From_Element() throws Exception {
        Element element = Mockito.mock(Element.class);
        ItemContainer container = Mockito.mock(ItemContainer.class);

        when(element.getName()).thenReturn("NAME");

        TitleBar ctrl = new TitleBar(
                new ElementBuilder(element).addPattern(container));

        String name = ctrl.getName();

        assertTrue(name.equals("NAME"));
    }

    @Test
    public void testGetMenu() throws Exception {
        Element element = Mockito.mock(Element.class);
        ItemContainer container = Mockito.mock(ItemContainer.class);

        when(element.getName()).thenReturn("NAME");

        Element elem = Mockito.mock(Element.class);

        when(element.findFirst(any(), any())).thenReturn(elem);

        TitleBar tb = new TitleBar(
                new ElementBuilder(element).addPattern(container));

        MainMenu menu = tb.getMenuBar();

        verify(element, atLeastOnce()).findFirst(any(),any());
    }
}
