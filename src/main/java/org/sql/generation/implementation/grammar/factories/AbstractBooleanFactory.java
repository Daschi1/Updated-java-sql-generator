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

package org.sql.generation.implementation.grammar.factories;

import org.sql.generation.api.grammar.booleans.InPredicate;
import org.sql.generation.api.grammar.builders.booleans.BooleanBuilder;
import org.sql.generation.api.grammar.common.NonBooleanExpression;
import org.sql.generation.api.grammar.factories.BooleanFactory;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.implementation.grammar.common.SQLFactoryBase;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public abstract class AbstractBooleanFactory extends SQLFactoryBase
        implements BooleanFactory {

    protected AbstractBooleanFactory(final SQLVendor vendor, final SQLProcessorAggregator processor) {
        super(vendor, processor);
    }

    @Override
    public BooleanBuilder booleanBuilder() {
        return this.booleanBuilder(null);
    }

    @Override
    public InPredicate in(final NonBooleanExpression what, final NonBooleanExpression... values) {
        return this.inBuilder(what).addValues(values).createExpression();
    }
}
