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

package org.sql.generation.implementation.grammar.common;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.common.TableNameDirect;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class TableNameDirectImpl extends TableNameImpl<TableNameDirect>
        implements TableNameDirect {
    private final String _tableName;

    public TableNameDirectImpl(final SQLProcessorAggregator processor, final String schemaName, final String tableName) {
        this(processor, TableNameDirect.class, schemaName, tableName);
    }

    protected TableNameDirectImpl(final SQLProcessorAggregator processor, final Class<? extends TableNameDirect> implClass,
                                  final String schemaName, final String tableName) {
        super(processor, implClass, schemaName);
        NullArgumentException.validateNotNull("table name", tableName);

        this._tableName = tableName;
    }

    @Override
    public String getTableName() {
        return this._tableName;
    }

    @Override
    protected boolean doesEqual(final TableNameDirect another) {
        return super.doesEqual(another) && this._tableName.equals(another.getTableName());
    }
}
