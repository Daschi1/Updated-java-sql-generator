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

package org.sql.generation.implementation.grammar.query;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.query.QueryExpression;
import org.sql.generation.api.grammar.query.TableAlias;
import org.sql.generation.api.grammar.query.TableReferenceByExpression;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class TableReferenceByExpressionImpl extends TableReferencePrimaryImpl<TableReferenceByExpression>
        implements TableReferenceByExpression {

    private final QueryExpression _expression;

    public TableReferenceByExpressionImpl(final SQLProcessorAggregator processor, final QueryExpression expression,
                                          final TableAlias alias) {
        this(processor, TableReferenceByExpression.class, expression, alias);
    }

    protected TableReferenceByExpressionImpl(final SQLProcessorAggregator processor,
                                             final Class<? extends TableReferenceByExpression> implClass, final QueryExpression expression, final TableAlias alias) {
        super(processor, implClass, alias);

        NullArgumentException.validateNotNull("collection expression", expression);

        this._expression = expression;
    }

    @Override
    public QueryExpression getQuery() {
        return this._expression;
    }

}
