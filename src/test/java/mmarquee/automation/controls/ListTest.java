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

import com.sun.jna.ptr.PointerByReference;

import mmarquee.automation.Element;
import mmarquee.automation.BaseAutomationTest;
import mmarquee.automation.ElementNotFoundException;
import mmarquee.automation.ItemNotFoundException;
import mmarquee.automation.pattern.Selection;
import mmarquee.uiautomation.IUIAutomationElement;
import mmarquee.uiautomation.TreeScope;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Mark Humphreys
 * Date 01/12/2016.
 *
 * Tests for List
 */
public class ListTest {

    @BeforeClass
    public static void checkOs() throws Exception {
        Assume.assumeTrue(isWindows());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    @Mock
    Element element;
    @Mock
    Element targetElement;
    @Mock Selection selection;

    @Mock
    IUIAutomationElement listElement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testName_Gets_Value_From_Element() throws Exception {
        when(element.getName()).thenReturn("NAME");

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        String name = list.getName();

        assertTrue(name.equals("NAME"));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetItems_By_Index_Throws_Exception_When_Not_Found() throws Exception {
        java.util.List result = new ArrayList<>();
        result.add(new Element(listElement));

        when(element.findAll(any(TreeScope.class), any(PointerByReference.class))).thenReturn(result);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        list.getItem(1);
    }

    @Test
    public void testGetItems_By_Index_Mocked() throws Exception {
        java.util.List result = new ArrayList<>();
        result.add(new Element(listElement));

        when(element.findAll(any(TreeScope.class), any(PointerByReference.class))).thenReturn(result);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getItem(0);

        assertTrue(item != null);
    }

    @Test
    public void testGetItems() throws Exception {
        java.util.List result = new ArrayList<>();
        result.add(new Element(listElement));

        when(element.findAll(any(TreeScope.class), any(PointerByReference.class))).thenReturn(result);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        java.util.List items = list.getItems();

        assertTrue(items.size() == 1);
    }

    @Test
    public void test_GetItem_By_Index() throws Exception {
        java.util.List items = new ArrayList<>();
        items.add(targetElement);
        
        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.CHILDREN),
                any())).thenReturn(items);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getItem(0);
        assertEquals(targetElement,item.getElement());

        verify(element, atLeastOnce()).findAll(any(), any());
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void test_GetItem_By_Index_Throws_Exception_When_Not_found() throws Exception {
        java.util.List items = new ArrayList<>();
        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.CHILDREN),
                any())).thenReturn(items);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        list.getItem(99);
    }

    @Test
    public void test_GetItem_By_Name() throws Exception {
        when(element.findFirst(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenReturn(targetElement);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getItem("myName");
        assertEquals(targetElement,item.getElement());

        verify(element, atLeastOnce()).findFirst(any(), any());
    }

    @Test(expected=ElementNotFoundException.class)
    public void test_GetItem_By_Name_Throws_Exception_When_Not_found() throws Exception {
        when(element.findFirst(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenThrow(new ElementNotFoundException());

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        list.getItem("unknownName");
    }

    @Test
    public void test_GetItem_By_Name_with_RegExPattern() throws Exception {
        java.util.List items = new ArrayList<>();
        items.add(targetElement);
        
        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenReturn(items);
        when(targetElement.getName()).thenReturn("myWorld");

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getItem(Pattern.compile("my.*"));
        assertEquals(targetElement,item.getElement());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test(expected=ItemNotFoundException.class)
    public void test_GetItem_By_Name_with_RegExPattern_Throws_Exception_When_Not_found() throws Exception {
        java.util.List items = new ArrayList<>();
        items.add(targetElement);
        
        when(targetElement.getName()).thenReturn("myWorld");
        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenReturn(items);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        list.getItem(Pattern.compile("unknown"));
    }

    @Test
    public void test_GetItem_By_AutomationId() throws Exception {
        when(element.findFirst(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenReturn(targetElement);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getItemByAutomationId("myID");
        assertEquals(targetElement,item.getElement());

        verify(element, atLeastOnce()).findFirst(any(), any());
    }
    
    @Test(expected=ElementNotFoundException.class)
    public void test_GetItem_By_AutomationId_Throws_Exception_When_Not_found() throws Exception {
        when(element.findFirst(BaseAutomationTest.isTreeScope(TreeScope.DESCENDANTS), any())).thenThrow(new ElementNotFoundException());

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

       list.getItemByAutomationId("unknownID");
    }

    @Test
    public void test_GetSelectedItems_Returns_Items_When_List_Not_Empty() throws Exception {
        java.util.List mockItems = new ArrayList<>();
        mockItems.add(targetElement);

        when(selection.getCurrentSelection()).thenReturn(mockItems);
        when(selection.isAvailable()).thenReturn(true);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        java.util.List<ListItem> items = list.getSelectedItems();

        assertEquals(mockItems.size(), items.size());
        assertEquals(targetElement, items.get(0).getElement());
    }

    @Test
    public void test_GetSelectedItems_Returns_No_Items_When_List_Empty() throws Exception {
        java.util.List mockItems = new ArrayList<>();
        when(selection.getCurrentSelection()).thenReturn(mockItems);
        when(selection.isAvailable()).thenReturn(true);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        java.util.List items = list.getSelectedItems();

        assertEquals(0, items.size());
    }

    @Test
    public void test_GetSelectedItem_Returns_Item_When_List_Not_Empty() throws Exception {
        java.util.List mockItems = new ArrayList<>();
        mockItems.add(targetElement);

        when(selection.getCurrentSelection()).thenReturn(mockItems);
        when(selection.isAvailable()).thenReturn(true);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        ListItem item = list.getSelectedItem();

        assertEquals(targetElement, item.getElement());
    }

    @Test(expected=ElementNotFoundException.class)
    public void test_GetSelectedItem_ThrowsException_When_List_Empty() throws Exception {
        java.util.List mockItems = new ArrayList<>();
        when(selection.getCurrentSelection()).thenReturn(mockItems);
        when(selection.isAvailable()).thenReturn(true);

        List list = new List(
                new ElementBuilder(element).addPattern(selection));

        list.getSelectedItem();
    }
}
