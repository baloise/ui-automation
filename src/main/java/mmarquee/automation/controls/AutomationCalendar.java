/*
 * Copyright 2016 inpwtepydjuf@gmail.com
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

import mmarquee.automation.AutomationElement;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.pattern.Value;

/**
 * Created by inpwt on 16/02/2016.
 *
 * Wrapper for the Calendar element.
 *
 * Implements IGridProvider, IScrollProvider, ITableProvider, IValueProvider
 */
public class AutomationCalendar extends AutomationBase {
    private Value valuePattern;

    /**
     * Constructor for the AutomationCalendar.
     * @param element The underlying automation element
     */
    public AutomationCalendar(AutomationElement element) {
        super(element);
        try {
            this.valuePattern = this.getValuePattern();
        } catch (PatternNotFoundException ex) {
            // Handle this nicely somehow
        }
    }

    /**
     * Gets the current value of the control
     * @return The current value
     */
    public String getValue() {
        return this.valuePattern.value();
    }
}