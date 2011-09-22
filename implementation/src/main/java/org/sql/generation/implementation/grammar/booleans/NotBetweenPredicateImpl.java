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

import org.sql.generation.api.grammar.booleans.NotBetweenPredicate;
import org.sql.generation.api.grammar.common.NonBooleanExpression;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class NotBetweenPredicateImpl extends MultiPredicateImpl<NotBetweenPredicate>
    implements NotBetweenPredicate
{

    public NotBetweenPredicateImpl( SQLProcessorAggregator processor, NonBooleanExpression left,
        NonBooleanExpression minimum, NonBooleanExpression maximum )
    {
        this( processor, NotBetweenPredicate.class, left, minimum, maximum );
    }

    protected NotBetweenPredicateImpl( SQLProcessorAggregator processor,
        Class<? extends NotBetweenPredicate> predicateClass, NonBooleanExpression left, NonBooleanExpression minimum,
        NonBooleanExpression maximum )
    {
        super( processor, predicateClass, left, minimum, maximum );
    }

    public NonBooleanExpression getMaximum()
    {
        return this.getRights().get( 1 );
    }

    public NonBooleanExpression getMinimum()
    {
        return this.getRights().get( 0 );
    }

}
