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

import org.sql.generation.api.grammar.common.ColumnNameList;

/**
 * This constraint defines the columns to be unique ({@code UNIQUE(col1, col2, ...)}).
 *
 * @author Stanislav Muhametsin
 */
public interface UniqueConstraint
        extends TableConstraint {

    /**
     * Returns the kind of uniqueness: either primary key constraint ({@code PRIMARY KEY}), or normal uniqueness
     * constraint ({@code UNIQUE}).
     *
     * @return The kind of uniqueness.
     * @see UniqueSpecification
     */
    UniqueSpecification getUniquenessKind();

    /**
     * Returns the names of columns that are unique.
     *
     * @return The names of columns that are unique.
     */
    ColumnNameList getColumnNameList();

}
