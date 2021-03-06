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

package org.sql.generation.implementation.grammar.literals;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.literals.StringLiteral;
import org.sql.generation.implementation.grammar.common.NonBooleanExpressionImpl;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class StringLiteralImpl extends NonBooleanExpressionImpl<StringLiteral>
        implements StringLiteral {

    private final String _literal;

    public StringLiteralImpl(final SQLProcessorAggregator processor, final String literal) {
        this(processor, StringLiteral.class, literal);
    }

    protected StringLiteralImpl(final SQLProcessorAggregator processor, final Class<? extends StringLiteral> implClass,
                                final String literal) {
        super(processor, implClass);
        NullArgumentException.validateNotNull("literal", literal);

        this._literal = literal;
    }

    @Override
    public String getString() {
        return this._literal;
    }

    @Override
    protected boolean doesEqual(final StringLiteral another) {
        return this._literal.equals(another.getString());
    }
}
