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
import org.sql.generation.api.grammar.query.QueryExpressionBody;
import org.sql.generation.implementation.grammar.common.NonBooleanExpressionImpl;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class QueryExpressionImpl extends NonBooleanExpressionImpl<QueryExpression>
        implements QueryExpression {

    private final QueryExpressionBody _body;

    public QueryExpressionImpl(final SQLProcessorAggregator processor, final QueryExpressionBody body) {
        this(processor, QueryExpression.class, body);
    }

    protected QueryExpressionImpl(final SQLProcessorAggregator processor, final Class<? extends QueryExpression> implClass,
                                  final QueryExpressionBody body) {
        super(processor, implClass);
        NullArgumentException.validateNotNull("query expression body", body);

        this._body = body;
    }

    @Override
    public QueryExpressionBody getQueryExpressionBody() {
        return this._body;
    }

    @Override
    protected boolean doesEqual(final QueryExpression another) {
        return this._body.equals(another.getQueryExpressionBody());
    }
}
