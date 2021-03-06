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

package org.sql.generation.implementation.grammar.manipulation;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.common.TableNameDirect;
import org.sql.generation.api.grammar.manipulation.DropBehaviour;
import org.sql.generation.api.grammar.manipulation.DropTableOrViewStatement;
import org.sql.generation.api.grammar.manipulation.ObjectType;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class DropTableOrViewStatementImpl extends DropStatementImpl<DropTableOrViewStatement>
        implements DropTableOrViewStatement {

    private final TableNameDirect _name;

    public DropTableOrViewStatementImpl(final SQLProcessorAggregator processor, final ObjectType whatToDrop,
                                        final DropBehaviour dropBehaviour, final TableNameDirect name) {
        this(processor, DropTableOrViewStatement.class, whatToDrop, dropBehaviour, name);
    }

    protected DropTableOrViewStatementImpl(final SQLProcessorAggregator processor,
                                           final Class<? extends DropTableOrViewStatement> realImplementingType, final ObjectType whatToDrop,
                                           final DropBehaviour dropBehaviour, final TableNameDirect name) {
        super(processor, realImplementingType, whatToDrop, dropBehaviour);

        NullArgumentException.validateNotNull("Table name", name);

        this._name = name;
    }

    @Override
    protected boolean doesEqual(final DropTableOrViewStatement another) {
        return this._name.equals(another.getTableName()) && super.doesEqual(another);
    }

    @Override
    public TableNameDirect getTableName() {
        return this._name;
    }

}
