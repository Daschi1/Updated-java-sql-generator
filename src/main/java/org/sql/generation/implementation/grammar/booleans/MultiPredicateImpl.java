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

package org.sql.generation.implementation.grammar.booleans;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.booleans.MultiPredicate;
import org.sql.generation.api.grammar.common.NonBooleanExpression;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Muhametsin
 */
public abstract class MultiPredicateImpl<ExpressionType extends MultiPredicate> extends
        AbstractBooleanExpression<ExpressionType>
        implements MultiPredicate {

    private final NonBooleanExpression _left;

    private final List<NonBooleanExpression> _rights;

    protected MultiPredicateImpl(final SQLProcessorAggregator processor, final Class<? extends ExpressionType> expressionClass,
                                 final NonBooleanExpression left, final NonBooleanExpression... rights) {
        this(processor, expressionClass, left, Arrays.asList(rights));
    }

    protected MultiPredicateImpl(final SQLProcessorAggregator processor, final Class<? extends ExpressionType> expressionClass,
                                 final NonBooleanExpression left, final List<NonBooleanExpression> rights) {
        super(processor, expressionClass);
        NullArgumentException.validateNotNull("left", left);
        NullArgumentException.validateNotNull("rights", rights);

        for (final NonBooleanExpression exp : rights) {
            NullArgumentException.validateNotNull("right", exp);
        }

        this._left = left;
        this._rights = Collections.unmodifiableList(rights);
    }

    @Override
    public NonBooleanExpression getLeft() {
        return this._left;
    }

    @Override
    public List<NonBooleanExpression> getRights() {
        return this._rights;
    }

    @Override
    protected boolean doesEqual(final ExpressionType another) {
        return this._left.equals(another.getLeft()) && this._rights.equals(another.getRights());
    }
}
