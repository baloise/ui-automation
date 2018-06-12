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
package mmarquee.automation;

/**
 * Automation Property Identifiers.
 *
 * @author Mark Humphreys
 * Date 28/01/2016.
 * See - https://msdn.microsoft.com/en-us/library/windows/desktop/ee671199(v=vs.85).aspx
 */
public enum PropertyID {
    RuntimeId(30000),
    BoundingRectangle(30001),
    ProcessId(30002),
    ControlType(30003),
    LocalizedControlType(30004),
    Name(30005),
    AcceleratorKey(30006),
    AccessKey(30007),
    HasKeyboardFocus(30008),
    IsKeyboardFocusable(30009),
    IsEnabled(30010),
    AutomationId(30011),
    ClassName(30012),
    HelpText(30013),
    ClickablePoint(30014),
    Culture(30015),
    IsControlElement(30016),
    IsContentElement(30017),
    LabeledBy(30018),
    IsPassword(30019),
    NativeWindowHandle(30020),
    ItemType(30021),
    IsOffscreen(30022),
    Orientation(30023),
    FrameworkId(30024),
    IsRequiredForForm(30025),
    ItemStatus(30026),
    IsDockPatternAvailable(30027),
    IsExpandCollapsePatternAvailable(30028),
    IsGridItemPatternAvailable(30029),
    IsGridPatternAvailable(30030),
    IsInvokePatternAvailable(30031),
    IsMultipleViewPatternAvailable(30032),
    IsRangeValuePatternAvailable(30033),
    IsScrollPatternAvailable(30034),
    IsScrollItemPatternAvailable(30035),
    IsSelectionItemPatternAvailable(30036),
    IsSelectionPatternAvailable(30037),
    IsTablePatternAvailable(30038),
    IsTableItemPatternAvailable(30039),
    IsTextPatternAvailable(30040),
    IsTogglePatternAvailable(30041),
    IsTransformPatternAvailable(30042),
    IsValuePatternAvailable(30043),
    IsWindowPatternAvailable(30044),

    GridRowCount(30062),
    GridColumnCount(30063),
    WindowIsModal(30077),
    WindowIsTopmost(30078),

    IsLegacyIAccessiblePatternAvailable(30090),
    LegacyIAccessibleChildId(30091),
    LegacyIAccessibleName(30092),
    LegacyIAccessibleValue(30093),
    LegacyIAccessibleDescription(30094),
    LegacyIAccessibleRole(30095),
    LegacyIAccessibleState(30096),
    LegacyIAccessibleHelp(30097),
    LegacyIAccessibleKeyboardShortcut(30098),
    LegacyIAccessibleSelection(30099),
    LegacyIAccessibleDefaultAction(30100),
    AriaRole(30101),
    AriaProperties(30102),
    IsDataValidForForm(30103),
    ControllerFor(30104),
    DescribedBy(30105),
    FlowsTo(30106),
    ProviderDescription(30107),
    IsItemContainerPatternAvailable(30108),
    IsVirtualizedItemPatternAvailable(30109),
    IsSynchronizedInputPatternAvailable(30110),
    IsObjectModelPatternAvailable(30112),
    IsAnnotationPatternAvailable(30118),
    IsTextPattern2Available(30119),
    IsStylesPatternAvailable(30127),
    IsSpreadsheetPatternAvailable(30128),
    IsSpreadsheetItemPatternAvailable(30132),
    IsTransformPattern2Available(30134),
    LiveSetting(30135),
    IsTextChildPatternAvailable(30136),
    IsDragPatternAvailable(30137),
    IsDropTargetPatternAvailable(30141),
    FlowsFrom(30148),
    IsTextEditPatternAvailable(30149),
    IsPeripheral(30150),
    IsCustomNavigationPatternAvailable(30151),
    PositionInSet(30152),
    SizeOfSet(30153),
    Level(30154),
    AnnotationTypes(30155),
    AnnotationObjects(30156),
    LandmarkType(30157),
    LocalizedLandmark(30158),
    FullDescription(30159),
    FillColor(30160),
    OutlineColor(30161),
    FillType(30162),
    OutlineThickness(30164),
    CenterPoint(30165),
    Rotation(30166),
    Size(30167);

    /**
     * The actual value.
     */
    private int value;

    /**
     * Gets the value.
     * @return The actual value
     */
    public int getValue() {
        return this.value;
    }

    PropertyID(int value) {
        this.value = value;
    }
}


