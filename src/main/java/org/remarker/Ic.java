/*
 * Copyright 2009-2020 by Justin T. Sampson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.remarker;

import static org.remarker.Html.extendedAttribute;

/**
 * @see <a href="http://intercoolerjs.org/reference.html">Intercooler Reference</a>
 */
@SuppressWarnings("unused")
public final class Ic
{
    private Ic()
    {
        // to prevent instantiation
    }

    public static Attribute Action(String value)
    {
        return extendedAttribute("ic-action", value);
    }

    public static Attribute AddClass(String value)
    {
        return extendedAttribute("ic-add-class", value);
    }

    public static Attribute AppendFrom(String value)
    {
        return extendedAttribute("ic-append-from", value);
    }

    public static Attribute AttrSrc(String value)
    {
        return extendedAttribute("ic-attr-src", value);
    }

    public static Attribute Confirm(String value)
    {
        return extendedAttribute("ic-confirm", value);
    }

    public static Attribute DeleteFrom(String value)
    {
        return extendedAttribute("ic-delete-from", value);
    }

    public static Attribute Deps(String value)
    {
        return extendedAttribute("ic-deps", value);
    }

    public static Attribute GetFrom(String value)
    {
        return extendedAttribute("ic-get-from", value);
    }

    public static Attribute GlobalInclude(String value)
    {
        return extendedAttribute("ic-global-include", value);
    }

    public static Attribute Include(String value)
    {
        return extendedAttribute("ic-include", value);
    }

    public static Attribute Indicator(String value)
    {
        return extendedAttribute("ic-indicator", value);
    }

    public static Attribute LimitChildren(String value)
    {
        return extendedAttribute("ic-limit-children", value);
    }

    public static Attribute LocalVars(String value)
    {
        return extendedAttribute("ic-local-vars", value);
    }

    public static Attribute OnBeforeSend(String value)
    {
        return extendedAttribute("ic-on-beforeSend", value);
    }

    public static Attribute OnBeforeTrigger(String value)
    {
        return extendedAttribute("ic-on-beforeTrigger", value);
    }

    public static Attribute OnComplete(String value)
    {
        return extendedAttribute("ic-on-complete", value);
    }

    public static Attribute OnError(String value)
    {
        return extendedAttribute("ic-on-error", value);
    }

    public static Attribute OnSuccess(String value)
    {
        return extendedAttribute("ic-on-success", value);
    }

    public static Attribute PatchTo(String value)
    {
        return extendedAttribute("ic-patch-to", value);
    }

    public static Attribute PausePolling(String value)
    {
        return extendedAttribute("ic-pause-polling", value);
    }

    public static Attribute Poll(String value)
    {
        return extendedAttribute("ic-poll", value);
    }

    public static Attribute PollRepeats(String value)
    {
        return extendedAttribute("ic-poll-repeats", value);
    }

    public static Attribute PostErrorsTo(String value)
    {
        return extendedAttribute("ic-post-errors-to", value);
    }

    public static Attribute PostTo(String value)
    {
        return extendedAttribute("ic-post-to", value);
    }

    public static Attribute PrependFrom(String value)
    {
        return extendedAttribute("ic-prepend-from", value);
    }

    public static Attribute Prompt(String value)
    {
        return extendedAttribute("ic-prompt", value);
    }

    public static Attribute PushUrl(String value)
    {
        return extendedAttribute("ic-push-url", value);
    }

    public static Attribute PutTo(String value)
    {
        return extendedAttribute("ic-put-to", value);
    }

    public static Attribute RemoveAfter(String value)
    {
        return extendedAttribute("ic-remove-after", value);
    }

    public static Attribute RemoveClass(String value)
    {
        return extendedAttribute("ic-remove-class", value);
    }

    public static Attribute ReplaceTarget(String value)
    {
        return extendedAttribute("ic-replace-target", value);
    }

    public static Attribute ScrollOffset(String value)
    {
        return extendedAttribute("ic-scroll-offset", value);
    }

    public static Attribute ScrollToTarget(String value)
    {
        return extendedAttribute("ic-scroll-to-target", value);
    }

    public static Attribute SelectFromResponse(String value)
    {
        return extendedAttribute("ic-select-from-response", value);
    }

    public static Attribute Src(String value)
    {
        return extendedAttribute("ic-src", value);
    }

    public static Attribute StyleSrc(String value)
    {
        return extendedAttribute("ic-style-src", value);
    }

    public static Attribute Target(String value)
    {
        return extendedAttribute("ic-target", value);
    }

    public static Attribute TransitionDuration(String value)
    {
        return extendedAttribute("ic-transition-duration", value);
    }

    public static Attribute TriggerDelay(String value)
    {
        return extendedAttribute("ic-trigger-delay", value);
    }

    public static Attribute TriggerFrom(String value)
    {
        return extendedAttribute("ic-trigger-from", value);
    }

    public static Attribute TriggerOn(String value)
    {
        return extendedAttribute("ic-trigger-on", value);
    }

    public static Attribute Verb(String value)
    {
        return extendedAttribute("ic-verb", value);
    }
}
