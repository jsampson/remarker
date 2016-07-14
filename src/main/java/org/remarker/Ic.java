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
        return attribute("ic-action", value);
    }

    public static Attribute AddClass(String value)
    {
        return attribute("ic-add-class", value);
    }

    public static Attribute AppendFrom(String value)
    {
        return attribute("ic-append-from", value);
    }

    public static Attribute AttrSrc(String value)
    {
        return attribute("ic-attr-src", value);
    }

    public static Attribute Confirm(String value)
    {
        return attribute("ic-confirm", value);
    }

    public static Attribute DeleteFrom(String value)
    {
        return attribute("ic-delete-from", value);
    }

    public static Attribute Deps(String value)
    {
        return attribute("ic-deps", value);
    }

    public static Attribute GetFrom(String value)
    {
        return attribute("ic-get-from", value);
    }

    public static Attribute GlobalInclude(String value)
    {
        return attribute("ic-global-include", value);
    }

    public static Attribute Include(String value)
    {
        return attribute("ic-include", value);
    }

    public static Attribute Indicator(String value)
    {
        return attribute("ic-indicator", value);
    }

    public static Attribute LimitChildren(String value)
    {
        return attribute("ic-limit-children", value);
    }

    public static Attribute LocalVars(String value)
    {
        return attribute("ic-local-vars", value);
    }

    public static Attribute OnBeforeSend(String value)
    {
        return attribute("ic-on-beforeSend", value);
    }

    public static Attribute OnBeforeTrigger(String value)
    {
        return attribute("ic-on-beforeTrigger", value);
    }

    public static Attribute OnComplete(String value)
    {
        return attribute("ic-on-complete", value);
    }

    public static Attribute OnError(String value)
    {
        return attribute("ic-on-error", value);
    }

    public static Attribute OnSuccess(String value)
    {
        return attribute("ic-on-success", value);
    }

    public static Attribute PatchTo(String value)
    {
        return attribute("ic-patch-to", value);
    }

    public static Attribute PausePolling(String value)
    {
        return attribute("ic-pause-polling", value);
    }

    public static Attribute Poll(String value)
    {
        return attribute("ic-poll", value);
    }

    public static Attribute PollRepeats(String value)
    {
        return attribute("ic-poll-repeats", value);
    }

    public static Attribute PostErrorsTo(String value)
    {
        return attribute("ic-post-errors-to", value);
    }

    public static Attribute PostTo(String value)
    {
        return attribute("ic-post-to", value);
    }

    public static Attribute PrependFrom(String value)
    {
        return attribute("ic-prepend-from", value);
    }

    public static Attribute Prompt(String value)
    {
        return attribute("ic-prompt", value);
    }

    public static Attribute PushUrl(String value)
    {
        return attribute("ic-push-url", value);
    }

    public static Attribute PutTo(String value)
    {
        return attribute("ic-put-to", value);
    }

    public static Attribute RemoveAfter(String value)
    {
        return attribute("ic-remove-after", value);
    }

    public static Attribute RemoveClass(String value)
    {
        return attribute("ic-remove-class", value);
    }

    public static Attribute ReplaceTarget(String value)
    {
        return attribute("ic-replace-target", value);
    }

    public static Attribute ScrollOffset(String value)
    {
        return attribute("ic-scroll-offset", value);
    }

    public static Attribute ScrollToTarget(String value)
    {
        return attribute("ic-scroll-to-target", value);
    }

    public static Attribute SelectFromResponse(String value)
    {
        return attribute("ic-select-from-response", value);
    }

    public static Attribute Src(String value)
    {
        return attribute("ic-src", value);
    }

    public static Attribute StyleSrc(String value)
    {
        return attribute("ic-style-src", value);
    }

    public static Attribute Target(String value)
    {
        return attribute("ic-target", value);
    }

    public static Attribute TransitionDuration(String value)
    {
        return attribute("ic-transition-duration", value);
    }

    public static Attribute TriggerDelay(String value)
    {
        return attribute("ic-trigger-delay", value);
    }

    public static Attribute TriggerOn(String value)
    {
        return attribute("ic-trigger-on", value);
    }

    public static Attribute Verb(String value)
    {
        return attribute("ic-verb", value);
    }

    private static Attribute attribute(String name, String value)
    {
        if (value == null)
        {
            return null;
        }
        else
        {
            return new Attribute(name, value, true);
        }
    }
}
