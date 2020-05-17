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

package org.remarker.dom;

import static java.util.Objects.requireNonNull;

public final class Text extends Content
{
    private final String value;

    Text(String value)
    {
        this.value = requireNonNull(value);
    }

    public String getValue()
    {
        return value;
    }

    @Override
    void appendTextTo(StringBuilder builder)
    {
        builder.append(value);
    }
}
