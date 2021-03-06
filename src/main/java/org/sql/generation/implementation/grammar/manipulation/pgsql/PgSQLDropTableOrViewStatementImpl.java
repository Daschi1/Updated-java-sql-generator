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

package org.sql.generation.implementation.grammar.manipulation.pgsql;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.common.TableNameDirect;
import org.sql.generation.api.grammar.manipulation.DropBehaviour;
import org.sql.generation.api.grammar.manipulation.DropTableOrViewStatement;
import org.sql.generation.api.grammar.manipulation.ObjectType;
import org.sql.generation.api.grammar.manipulation.pgsql.PgSQLDropTableOrViewStatement;
import org.sql.generation.implementation.grammar.manipulation.DropTableOrViewStatementImpl;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class PgSQLDropTableOrViewStatementImpl extends DropTableOrViewStatementImpl
        implements PgSQLDropTableOrViewStatement {

    private final Boolean _useIfExists;

    public PgSQLDropTableOrViewStatementImpl(final SQLProcessorAggregator processor, final ObjectType whatToDrop,
                                             final DropBehaviour dropBehaviour, final TableNameDirect name, final Boolean useIfExists) {
        this(processor, PgSQLDropTableOrViewStatement.class, whatToDrop, dropBehaviour, name, useIfExists);
    }

    protected PgSQLDropTableOrViewStatementImpl(final SQLProcessorAggregator processor,
                                                final Class<? extends PgSQLDropTableOrViewStatement> realImplementingType, final ObjectType whatToDrop,
                                                final DropBehaviour dropBehaviour, final TableNameDirect name, final Boolean useIfExists) {
        super(processor, realImplementingType, whatToDrop, dropBehaviour, name);

        NullArgumentException.validateNotNull("Use IF EXISTS", useIfExists);

        this._useIfExists = useIfExists;
    }

    @Override
    protected boolean doesEqual(final DropTableOrViewStatement another) {
        return super.doesEqual(another)
                && this._useIfExists.equals(((PgSQLDropTableOrViewStatement) another).useIfExists());
    }

    @Override
    public Boolean useIfExists() {
        return this._useIfExists;
    }
}
