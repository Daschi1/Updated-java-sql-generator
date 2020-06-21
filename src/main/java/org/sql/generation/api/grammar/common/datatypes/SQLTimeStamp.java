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

package org.sql.generation.api.grammar.common.datatypes;

/**
 * @author Stanislav Muhametsin
 */
public interface SQLTimeStamp
        extends SQLDataType, ParametrizableDataType {

    /**
     * Returns the precision for this {@code TIMESTAMP}. May be {@code null}.
     *
     * @return The precision for this {@code TIMESTAMP}.
     */
    Integer getPrecision();

    /**
     * Returns whether the {@code TIMESTAMP} should be with time zone. May be {@code null} if no choice specified.
     *
     * @return Boolean if the {@code TIMESTAMP} is within the timezone.
     */
    Boolean isWithTimeZone();

}