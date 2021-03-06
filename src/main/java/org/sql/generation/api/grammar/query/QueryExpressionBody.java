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

import org.sql.generation.api.grammar.common.NonBooleanExpression;
import org.sql.generation.api.grammar.query.joins.JoinedTable;

/**
 * This is common interface for a queries and joined tables.
 *
 * @author Stanislav Muhametsin
 * @see QueryExpressionBodyActual
 * @see JoinedTable
 */
public interface QueryExpressionBody
        extends NonBooleanExpression {
    /**
     * This syntax element represents the empty query expression body. It is defined for convenience. For example, an
     * {@code UNION} (or any other set operation) between empty query and another query is always just another query.
     *
     * @author Stanislav Muhametsin
     */
    final class EmptyQueryExpressionBody
            implements QueryExpressionBody {
        private EmptyQueryExpressionBody() {
        }

        /**
         * Returns {@link EmptyQueryExpressionBody}.
         */
        @Override
        public Class<? extends QueryExpressionBody> getImplementedType() {
            return EmptyQueryExpressionBody.class;
        }

        /**
         * The singleton instance of {@link EmptyQueryExpressionBody}.
         */
        public static final EmptyQueryExpressionBody INSTANCE = new EmptyQueryExpressionBody();
    }

}
