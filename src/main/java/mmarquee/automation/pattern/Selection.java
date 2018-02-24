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
package mmarquee.automation.pattern;

import java.util.List;

import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import mmarquee.automation.AutomationElement;
import mmarquee.automation.AutomationException;
import mmarquee.automation.ConversionFailure;
import mmarquee.automation.PatternID;
import mmarquee.automation.PropertyID;
import mmarquee.automation.uiautomation.IUIAutomationElement;
import mmarquee.automation.uiautomation.IUIAutomationElementArray;
import mmarquee.automation.uiautomation.IUIAutomationElementArrayConverter;
import mmarquee.automation.uiautomation.IUIAutomationElementConverter;
import mmarquee.automation.uiautomation.IUIAutomationSelectionPattern;
import mmarquee.automation.uiautomation.IUIAutomationSelectionPattern2;
import mmarquee.automation.uiautomation.IUIAutomationSelectionPattern2Convertor;
import mmarquee.automation.uiautomation.IUIAutomationSelectionPatternConverter;

/**
 *
 * Wrapper for the Selection pattern.
 *
 * @author Mark Humphreys
 * Date 25/02/2016.
 */
public class Selection extends BasePattern {

    /**
     * Constructor for the value pattern
     * @param element The automation element for which the pattern is valid
     * @throws AutomationException If something goes wrong
     */
    public Selection(final AutomationElement element) throws AutomationException {
    	super(element);
        this.IID = IUIAutomationSelectionPattern.IID;
        this.patternID = PatternID.Selection;
        this.availabilityPropertyID = PropertyID.IsSelectionPatternAvailable;
    }

    IUIAutomationSelectionPattern rawPattern;

    /**
     * Gets the pointer.
     * @return Underlying pointer
     * @throws AutomationException Automation has gone wrong
     */
    private IUIAutomationSelectionPattern getPattern() throws AutomationException {
    	return getPattern(rawPattern, this::convertPointerToInterface);
    }

    /**
     * Gets the current selection.
     * @return The current selection
     * @throws AutomationException Something has gone wrong
     */
    public List<AutomationElement> getCurrentSelection() throws AutomationException {

        PointerByReference pbr = new PointerByReference();

        final int res = this.getPattern().getCurrentSelection(pbr);
        if (res != 0) {
            throw new AutomationException(res);
        }

        return collectionToList(getAutomationElementArrayFromReference(pbr));
    }

    /**
     * Convert the unknown pointer to selection pattern.
     *
     * @param pUnknown The unknown pointer
     * @return IUIAutomationSelectionPattern the converted pointer
     */
    public IUIAutomationSelectionPattern convertPointerToInterface(PointerByReference pUnknown) {
        return IUIAutomationSelectionPatternConverter.pointerToInterface(pUnknown);
    }

    /**
     * Convert the unknown pointer to an array.
     *
     * @param pUnknown The unknown pointer
     * @return IUIAutomationSelectionPattern the converted pointer
     */
    public IUIAutomationElementArray convertPointerToElementArray(PointerByReference pUnknown) {
        return IUIAutomationElementArrayConverter.pointerToInterface(pUnknown);
    }

    /**
     * Gets the current selection.
     * @return List of selected items
     * @throws AutomationException Something has gone wrong
     */
    public List<AutomationElement> getSelection() throws AutomationException {
        return getCurrentSelection();
    }

    /**
     * Returns whether the selection supports multiple selection.
     * @return Value from automation
     * @throws AutomationException Something has gone wrong
     */
    public boolean canSelectMultiple() throws AutomationException {
        IntByReference ibr = new IntByReference();

        final int res = this.getPattern().getCurrentCanSelectMultiple(ibr);
        if (res != 0) {
            throw new AutomationException(res);
        }

        return (ibr.getValue() == 1);
    }

    /**
     * Gets the currently selected item from the IUIAutomationSelectionPattern2 interface.
     * @return The currently selected element, if possible
     * @throws AutomationException Error, or unable to convert to IUIAutomationSelectionPattern2
     */
    public AutomationElement getCurrentSelectedItem() throws AutomationException {
        PointerByReference pbr = new PointerByReference();

        IUIAutomationSelectionPattern2 pattern2 = this.getPattern2();

        final int res = pattern2.getCurrentSelection(pbr);
        if (res != 0) {
            throw new AutomationException(res);
        }

        PointerByReference pUnknown = new PointerByReference();

        WinNT.HRESULT result = this.rawPattern.QueryInterface(
                new Guid.REFIID(IUIAutomationElement.IID), pUnknown);

        if (COMUtils.SUCCEEDED(result)) {
            return new AutomationElement(IUIAutomationElementConverter.pointerToInterface(pUnknown));
        } else {
            throw new ConversionFailure("IUIAutomationElement");
        }
    }

    /**
     * Gets the IUIAutomationSelectionPattern2 interface, if possible.
     * @return The IUIAutomationSelectionPattern2 interface
     * @throws AutomationException Not able to convert interface
     */
    private IUIAutomationSelectionPattern2 getPattern2()
            throws AutomationException {

        PointerByReference pUnknown = new PointerByReference();

        WinNT.HRESULT result = this.getPattern().QueryInterface(
                new Guid.REFIID(IUIAutomationSelectionPattern2.IID), pUnknown);

        if (COMUtils.SUCCEEDED(result)) {
            return IUIAutomationSelectionPattern2Convertor.pointerToInterface(pUnknown);
        } else {
            throw new ConversionFailure("IUIAutomationElement");
        }
    }
}
