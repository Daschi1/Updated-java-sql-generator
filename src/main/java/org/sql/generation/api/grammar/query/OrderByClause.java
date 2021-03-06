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

package org.sql.generation.api.grammar.query;

import org.atp.api.Typeable;

import java.util.List;

/**
 * This syntax element represents the {@code ORDER BY} clause of {@code SELECT} statement.
 *
 * @author Stanislav Muhametsin
 */
public interface OrderByClause
        extends Typeable<OrderByClause> {

    /**
     * Returns the ordering columns of this {@code ORDER BY} clause.
     *
     * @return The ordering columns of this {@code ORDER BY} clause.
     */
    List<SortSpecification> getOrderingColumns();
}
