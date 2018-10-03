package mmarquee.automation.controls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sun.jna.platform.win32.User32;
import mmarquee.automation.*;
import mmarquee.uiautomation.IUIAutomationElement;
import mmarquee.automation.utils.UtilsTest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sun.jna.platform.win32.WinNT;

import mmarquee.automation.Element;
import mmarquee.automation.pattern.ItemContainer;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.uiautomation.IUIAutomation;
import mmarquee.uiautomation.TreeScope;

/**
 * Mocked tests for Application.
 *
 * These tests currently require Windows to be run successfully.
 *
 * @author Mark Humphreys
 * Date 13/01/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { Ole32Wrapper.class })
public class ApplicationTest {
    @BeforeClass
    public static void checkOs() throws Exception {
        Assume.assumeTrue(isWindows());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        instance = new UIAutomation(mocked_automation);

        list = new ArrayList<>();
        list.add(targetElement);
    }

    @Mock
    Element element;
    
    @Mock
    IUIAutomation mocked_automation;
    
    UIAutomation instance;

    @Mock
    mmarquee.automation.pattern.Window window;

    @Mock
    ItemContainer container;

    @Mock
    WinNT.HANDLE handle;

    @InjectMocks
    UIAutomation automation;
    
    List<Element> list;
    
    @Mock
    Element targetElement;

    @Test
    public void testGetWindow_Returns_Window()
            throws AutomationException {

        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.CHILDREN),
                any())).thenReturn(list);
        when(targetElement.getName()).thenReturn("Untitled - Notepad");
        
        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false).automation(instance));

        Window window = app.getWindow(Search.getBuilder("Untitled - Notepad").build());
        assertEquals(targetElement,window.getElement());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test(expected=ElementNotFoundException.class)
    public void testGetWindow_Returns_Exception_When_Element_Not_Found()
            throws AutomationException {

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false).automation(instance));

        app.getWindow(Search.getBuilder("Untitled - Notepad").build());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test
    public void testGetWindow_with_RegExPattern_Returns_Window()
            throws AutomationException, PatternNotFoundException {

        when(element.findAll(BaseAutomationTest.isTreeScope(TreeScope.CHILDREN),
                any())).thenReturn(list);
        when(targetElement.getName()).thenReturn("Untitled - Notepad");
        
        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false).automation(instance));

        Window window = app.getWindow(Search.getBuilder(Pattern.compile("untitled.+",Pattern.CASE_INSENSITIVE)).build());
        assertEquals(targetElement,window.getElement());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test(expected=ElementNotFoundException.class)
    public void testGetWindow_with_RegExPattern_Returns_Exception_When_Element_Not_Found()
            throws AutomationException, PatternNotFoundException {

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false).automation(instance));

        app.getWindow(Search.getBuilder(Pattern.compile("bla\\.+blubb")).build());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test
    public void testSetAttached_Set_To_False() throws Exception {
        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false).automation(instance));

        assertFalse(app.getIsAttached());
    }

    @Test
    public void testSetAttached_Set_To_True() throws Exception {
        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(true).automation(instance));

        assertTrue(app.getIsAttached());
    }

    @Test
    @Ignore("Need to mock the elem getting a name")
    public void testGetWindow_Calls_FindAll_From_Element()
            throws AutomationException, PatternNotFoundException {
        List<Element> list = new ArrayList<>();

        IUIAutomationElement elem = Mockito.mock(IUIAutomationElement.class);

        list.add(new Element(elem));

        when(element.findAll(any(), any())).thenReturn(list);

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(false));

        Window window = app.getWindow(Search.getBuilder("Untitled - Notepad").build());

        verify(element, atLeastOnce()).findAll(any(), any());
    }

    @Test
    public void testClose_Calls_FindWindow() throws Exception {
        User32 user32 = Mockito.mock(User32.class);

        Application app = new Application(
                new ElementBuilder(element).handle(handle).user32(user32).attached(true));

        app.close("Untitled - Notepad");

        verify(user32, atLeastOnce()).FindWindow(any(), any());
    }

    @Test
    public void testClose_with_RegexPattern_Calls_EnumWindows() throws Exception {
        User32 user32 = Mockito.mock(User32.class);
        UtilsTest.setUser32(user32);

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(true).user32(user32));

        app.close(Pattern.compile("Untitled - Notepad"));

        verify(user32, atLeastOnce()).EnumWindows(any(), any());
    }

    @Test
    public void testQuit_Calls_FindWindow() throws Exception {
        User32 user32 = Mockito.mock(User32.class);

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(true).user32(user32));

        app.quit("Untitled - Notepad");

        verify(user32, atLeastOnce()).FindWindow(any(), any());
    }

    @Test
    public void testQuit_with_RegexPattern_Calls_EnumWindows() throws Exception {
        User32 user32 = Mockito.mock(User32.class);
        UtilsTest.setUser32(user32);

        Application app = new Application(
                new ElementBuilder(element).handle(handle).attached(true).user32(user32));

        app.quit(Pattern.compile("Untitled - Notepad"));

        verify(user32, atLeastOnce()).EnumWindows(any(), any());
    }
}
