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
import mmarquee.automation.UIAutomation;
import mmarquee.automation.pattern.Value;
import mmarquee.uiautomation.IUIAutomation;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Mark Humphreys
 * Date 28/11/2016.
 */
public class DataGridCellTest {
    static {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
    }

    @Test
    public void testValue() throws Exception {
        Element element = Mockito.mock(Element.class);
        Value value = Mockito.mock(Value.class);
        when(value.isAvailable()).thenReturn(true);

        when(value.value()).thenReturn("VALUE");

        IUIAutomation mocked_automation = Mockito.mock(IUIAutomation.class);
        UIAutomation instance = new UIAutomation(mocked_automation);

        DataGridCell cell =
                new DataGridCell(new ElementBuilder(element).addPattern(value).automation(instance));

        String val = cell.getValue();

        assertTrue(val.equals("VALUE"));
    }

    @Test
    public void testGetCellName() throws Exception {
        Element element = Mockito.mock(Element.class);
        Value value = Mockito.mock(Value.class);

        when(element.getName()).thenReturn("NAME");

        IUIAutomation mocked_automation = Mockito.mock(IUIAutomation.class);
        UIAutomation instance = new UIAutomation(mocked_automation);

        DataGridCell cell =
                new DataGridCell(new ElementBuilder(element).addPattern(value).automation(instance));

        String val = cell.getName();

        assertTrue(val.equals("NAME"));
    }
}