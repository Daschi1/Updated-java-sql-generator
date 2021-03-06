/*
 * Copyright (c) 2010, Stanislav Muhametsin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.sql.generation.api.grammar.definition.table;


/**
 * This enum represents the three different ways of matching foreign keys. May be {@link #FULL}, {@link #PARTIAL}, or
 * {@link #SIMPLE}.
 *
 * @author Stanislav Muhametsin
 */
public final class MatchType {

    /**
     * Represents the full match ({@code MATCH FULL}).
     */
    public static final MatchType FULL = new MatchType();

    /**
     * Represents the partial match ({@code MATCH PARTIAL}).
     */
    public static final MatchType PARTIAL = new MatchType();

    /**
     * Represents the simple match ({@code MATCH SIMPLE}).
     */
    public static final MatchType SIMPLE = new MatchType();
}
