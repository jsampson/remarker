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

package org.remarker.ext;

import org.remarker.dom.Attribute;

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
        return Attribute.quotedString("ic-action", value);
    }

    public static Attribute AddClass(String value)
    {
        return Attribute.quotedString("ic-add-class", value);
    }

    public static Attribute AppendFrom(String value)
    {
        return Attribute.quotedString("ic-append-from", value);
    }

    public static Attribute AttrSrc(String value)
    {
        return Attribute.quotedString("ic-attr-src", value);
    }

    public static Attribute Confirm(String value)
    {
        return Attribute.quotedString("ic-confirm", value);
    }

    public static Attribute DeleteFrom(String value)
    {
        return Attribute.quotedString("ic-delete-from", value);
    }

    public static Attribute Deps(String value)
    {
        return Attribute.quotedString("ic-deps", value);
    }

    public static Attribute GetFrom(String value)
    {
        return Attribute.quotedString("ic-get-from", value);
    }

    public static Attribute GlobalInclude(String value)
    {
        return Attribute.quotedString("ic-global-include", value);
    }

    public static Attribute Include(String value)
    {
        return Attribute.quotedString("ic-include", value);
    }

    public static Attribute Indicator(String value)
    {
        return Attribute.quotedString("ic-indicator", value);
    }

    public static Attribute LimitChildren(String value)
    {
        return Attribute.quotedString("ic-limit-children", value);
    }

    public static Attribute LocalVars(String value)
    {
        return Attribute.quotedString("ic-local-vars", value);
    }

    public static Attribute OnBeforeSend(String value)
    {
        return Attribute.quotedString("ic-on-beforeSend", value);
    }

    public static Attribute OnBeforeTrigger(String value)
    {
        return Attribute.quotedString("ic-on-beforeTrigger", value);
    }

    public static Attribute OnComplete(String value)
    {
        return Attribute.quotedString("ic-on-complete", value);
    }

    public static Attribute OnError(String value)
    {
        return Attribute.quotedString("ic-on-error", value);
    }

    public static Attribute OnSuccess(String value)
    {
        return Attribute.quotedString("ic-on-success", value);
    }

    public static Attribute PatchTo(String value)
    {
        return Attribute.quotedString("ic-patch-to", value);
    }

    public static Attribute PausePolling(String value)
    {
        return Attribute.quotedString("ic-pause-polling", value);
    }

    public static Attribute Poll(String value)
    {
        return Attribute.quotedString("ic-poll", value);
    }

    public static Attribute PollRepeats(String value)
    {
        return Attribute.quotedString("ic-poll-repeats", value);
    }

    public static Attribute PostErrorsTo(String value)
    {
        return Attribute.quotedString("ic-post-errors-to", value);
    }

    public static Attribute PostTo(String value)
    {
        return Attribute.quotedString("ic-post-to", value);
    }

    public static Attribute PrependFrom(String value)
    {
        return Attribute.quotedString("ic-prepend-from", value);
    }

    public static Attribute Prompt(String value)
    {
        return Attribute.quotedString("ic-prompt", value);
    }

    public static Attribute PushUrl(String value)
    {
        return Attribute.quotedString("ic-push-url", value);
    }

    public static Attribute PutTo(String value)
    {
        return Attribute.quotedString("ic-put-to", value);
    }

    public static Attribute RemoveAfter(String value)
    {
        return Attribute.quotedString("ic-remove-after", value);
    }

    public static Attribute RemoveClass(String value)
    {
        return Attribute.quotedString("ic-remove-class", value);
    }

    public static Attribute ReplaceTarget(String value)
    {
        return Attribute.quotedString("ic-replace-target", value);
    }

    public static Attribute ScrollOffset(String value)
    {
        return Attribute.quotedString("ic-scroll-offset", value);
    }

    public static Attribute ScrollToTarget(String value)
    {
        return Attribute.quotedString("ic-scroll-to-target", value);
    }

    public static Attribute SelectFromResponse(String value)
    {
        return Attribute.quotedString("ic-select-from-response", value);
    }

    public static Attribute Src(String value)
    {
        return Attribute.quotedString("ic-src", value);
    }

    public static Attribute StyleSrc(String value)
    {
        return Attribute.quotedString("ic-style-src", value);
    }

    public static Attribute Target(String value)
    {
        return Attribute.quotedString("ic-target", value);
    }

    public static Attribute TransitionDuration(String value)
    {
        return Attribute.quotedString("ic-transition-duration", value);
    }

    public static Attribute TriggerDelay(String value)
    {
        return Attribute.quotedString("ic-trigger-delay", value);
    }

    public static Attribute TriggerFrom(String value)
    {
        return Attribute.quotedString("ic-trigger-from", value);
    }

    public static Attribute TriggerOn(String value)
    {
        return Attribute.quotedString("ic-trigger-on", value);
    }

    public static Attribute Verb(String value)
    {
        return Attribute.quotedString("ic-verb", value);
    }
}
