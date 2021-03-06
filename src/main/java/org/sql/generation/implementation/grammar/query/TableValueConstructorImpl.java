/*
 * Copyright (c) 2011, Stanislav Muhametsin. All Rights Reserved.
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
import org.sql.generation.api.grammar.query.RowValueConstructor;
import org.sql.generation.api.grammar.query.TableValueConstructor;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Muhametsin
 */
public class TableValueConstructorImpl extends QueryExpressionBodyImpl<TableValueConstructor>
        implements TableValueConstructor {

    private final List<RowValueConstructor> _rows;

    public TableValueConstructorImpl(final SQLProcessorAggregator processor, final List<RowValueConstructor> rows) {
        this(processor, TableValueConstructor.class, rows);
    }

    protected TableValueConstructorImpl(final SQLProcessorAggregator processor,
                                        final Class<? extends TableValueConstructor> expressionClass, final List<RowValueConstructor> rows) {
        super(processor, expressionClass);

        NullArgumentException.validateNotNull("rows", rows);

        this._rows = Collections.unmodifiableList(rows);
    }

    @Override
    public List<RowValueConstructor> getRows() {
        return this._rows;
    }

    @Override
    protected boolean doesEqual(final TableValueConstructor other) {
        return this._rows.equals(other.getRows());
    }

}
