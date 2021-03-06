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

package org.sql.generation.implementation.grammar.builders.definition;

import org.sql.generation.api.grammar.builders.definition.TableDefinitionBuilder;
import org.sql.generation.api.grammar.common.TableNameDirect;
import org.sql.generation.api.grammar.definition.table.TableCommitAction;
import org.sql.generation.api.grammar.definition.table.TableContentsSource;
import org.sql.generation.api.grammar.definition.table.TableDefinition;
import org.sql.generation.api.grammar.definition.table.TableScope;
import org.sql.generation.implementation.grammar.common.SQLBuilderBase;
import org.sql.generation.implementation.grammar.definition.table.TableDefinitionImpl;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class TableDefinitionBuilderImpl extends SQLBuilderBase
        implements TableDefinitionBuilder {

    private TableScope _scope;
    private TableNameDirect _name;
    private TableCommitAction _commitAction;
    private TableContentsSource _contents;

    public TableDefinitionBuilderImpl(final SQLProcessorAggregator processor) {
        super(processor);
    }

    @Override
    public TableDefinition createExpression() {
        return new TableDefinitionImpl(this.getProcessor(), this._commitAction, this._contents, this._name,
                this._scope);
    }

    @Override
    public TableDefinitionBuilder setTableScope(final TableScope scope) {
        this._scope = scope;
        return this;
    }

    @Override
    public TableDefinitionBuilder setTableName(final TableNameDirect tableName) {
        this._name = tableName;
        return this;
    }

    @Override
    public TableDefinitionBuilder setCommitAction(final TableCommitAction commitAction) {
        this._commitAction = commitAction;
        return this;
    }

    @Override
    public TableDefinitionBuilder setTableContentsSource(final TableContentsSource contents) {
        this._contents = contents;
        return this;
    }

    @Override
    public TableScope getTableScope() {
        return this._scope;
    }

    @Override
    public TableNameDirect getTableName() {
        return this._name;
    }

    @Override
    public TableCommitAction getCommitAction() {
        return this._commitAction;
    }

    @Override
    public TableContentsSource getTableContentsSource() {
        return this._contents;
    }

}
