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
        return Attribute.simple("ic-action", value);
    }

    public static Attribute AddClass(String value)
    {
        return Attribute.simple("ic-add-class", value);
    }

    public static Attribute AppendFrom(String value)
    {
        return Attribute.simple("ic-append-from", value);
    }

    public static Attribute AttrSrc(String value)
    {
        return Attribute.simple("ic-attr-src", value);
    }

    public static Attribute Confirm(String value)
    {
        return Attribute.simple("ic-confirm", value);
    }

    public static Attribute DeleteFrom(String value)
    {
        return Attribute.simple("ic-delete-from", value);
    }

    public static Attribute Deps(String value)
    {
        return Attribute.simple("ic-deps", value);
    }

    public static Attribute GetFrom(String value)
    {
        return Attribute.simple("ic-get-from", value);
    }

    public static Attribute GlobalInclude(String value)
    {
        return Attribute.simple("ic-global-include", value);
    }

    public static Attribute Include(String value)
    {
        return Attribute.simple("ic-include", value);
    }

    public static Attribute Indicator(String value)
    {
        return Attribute.simple("ic-indicator", value);
    }

    public static Attribute LimitChildren(String value)
    {
        return Attribute.simple("ic-limit-children", value);
    }

    public static Attribute LocalVars(String value)
    {
        return Attribute.simple("ic-local-vars", value);
    }

    public static Attribute OnBeforeSend(String value)
    {
        return Attribute.simple("ic-on-beforeSend", value);
    }

    public static Attribute OnBeforeTrigger(String value)
    {
        return Attribute.simple("ic-on-beforeTrigger", value);
    }

    public static Attribute OnComplete(String value)
    {
        return Attribute.simple("ic-on-complete", value);
    }

    public static Attribute OnError(String value)
    {
        return Attribute.simple("ic-on-error", value);
    }

    public static Attribute OnSuccess(String value)
    {
        return Attribute.simple("ic-on-success", value);
    }

    public static Attribute PatchTo(String value)
    {
        return Attribute.simple("ic-patch-to", value);
    }

    public static Attribute PausePolling(String value)
    {
        return Attribute.simple("ic-pause-polling", value);
    }

    public static Attribute Poll(String value)
    {
        return Attribute.simple("ic-poll", value);
    }

    public static Attribute PollRepeats(String value)
    {
        return Attribute.simple("ic-poll-repeats", value);
    }

    public static Attribute PostErrorsTo(String value)
    {
        return Attribute.simple("ic-post-errors-to", value);
    }

    public static Attribute PostTo(String value)
    {
        return Attribute.simple("ic-post-to", value);
    }

    public static Attribute PrependFrom(String value)
    {
        return Attribute.simple("ic-prepend-from", value);
    }

    public static Attribute Prompt(String value)
    {
        return Attribute.simple("ic-prompt", value);
    }

    public static Attribute PushUrl(String value)
    {
        return Attribute.simple("ic-push-url", value);
    }

    public static Attribute PutTo(String value)
    {
        return Attribute.simple("ic-put-to", value);
    }

    public static Attribute RemoveAfter(String value)
    {
        return Attribute.simple("ic-remove-after", value);
    }

    public static Attribute RemoveClass(String value)
    {
        return Attribute.simple("ic-remove-class", value);
    }

    public static Attribute ReplaceTarget(String value)
    {
        return Attribute.simple("ic-replace-target", value);
    }

    public static Attribute ScrollOffset(String value)
    {
        return Attribute.simple("ic-scroll-offset", value);
    }

    public static Attribute ScrollToTarget(String value)
    {
        return Attribute.simple("ic-scroll-to-target", value);
    }

    public static Attribute SelectFromResponse(String value)
    {
        return Attribute.simple("ic-select-from-response", value);
    }

    public static Attribute Src(String value)
    {
        return Attribute.simple("ic-src", value);
    }

    public static Attribute StyleSrc(String value)
    {
        return Attribute.simple("ic-style-src", value);
    }

    public static Attribute Target(String value)
    {
        return Attribute.simple("ic-target", value);
    }

    public static Attribute TransitionDuration(String value)
    {
        return Attribute.simple("ic-transition-duration", value);
    }

    public static Attribute TriggerDelay(String value)
    {
        return Attribute.simple("ic-trigger-delay", value);
    }

    public static Attribute TriggerFrom(String value)
    {
        return Attribute.simple("ic-trigger-from", value);
    }

    public static Attribute TriggerOn(String value)
    {
        return Attribute.simple("ic-trigger-on", value);
    }

    public static Attribute Verb(String value)
    {
        return Attribute.simple("ic-verb", value);
    }
}
