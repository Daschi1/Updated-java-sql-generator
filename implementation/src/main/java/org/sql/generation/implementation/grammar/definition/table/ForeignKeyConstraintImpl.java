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

package org.sql.generation.implementation.grammar.definition.table;

import org.atp.spi.TypeableImpl;
import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.common.ColumnNameList;
import org.sql.generation.api.grammar.common.TableNameDirect;
import org.sql.generation.api.grammar.definition.table.ForeignKeyConstraint;
import org.sql.generation.api.grammar.definition.table.MatchType;
import org.sql.generation.api.grammar.definition.table.ReferentialAction;
import org.sql.generation.api.grammar.definition.table.TableConstraint;
import org.sql.generation.implementation.grammar.common.SQLSyntaxElementBase;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class ForeignKeyConstraintImpl extends SQLSyntaxElementBase<TableConstraint, ForeignKeyConstraint>
    implements ForeignKeyConstraint
{

    private final ColumnNameList _sourceColumns;
    private final TableNameDirect _targetTableName;
    private final ColumnNameList _targetColumns;
    private final MatchType _matchType;
    private final ReferentialAction _onDelete;
    private final ReferentialAction _onUpdate;

    public ForeignKeyConstraintImpl( SQLProcessorAggregator processor, ColumnNameList sourceColumns,
        TableNameDirect targetTableName, ColumnNameList targetColumns, MatchType matchType, ReferentialAction onDelete,
        ReferentialAction onUpdate )
    {
        this( processor, ForeignKeyConstraint.class, sourceColumns, targetTableName, targetColumns, matchType,
            onDelete, onUpdate );
    }

    protected ForeignKeyConstraintImpl( SQLProcessorAggregator processor,
        Class<? extends ForeignKeyConstraint> realImplementingType, ColumnNameList sourceColumns,
        TableNameDirect targetTableName, ColumnNameList targetColumns, MatchType matchType, ReferentialAction onDelete,
        ReferentialAction onUpdate )
    {
        super( processor, realImplementingType );

        NullArgumentException.validateNotNull( "Source columns", sourceColumns );
        NullArgumentException.validateNotNull( "Target table name", targetTableName );

        this._sourceColumns = sourceColumns;
        this._targetTableName = targetTableName;
        this._targetColumns = targetColumns;
        this._matchType = matchType;
        this._onDelete = onDelete;
        this._onUpdate = onUpdate;
    }

    @Override
    protected boolean doesEqual( ForeignKeyConstraint another )
    {
        return this._targetTableName.equals( another.getTargetTableName() )
            && this._sourceColumns.equals( another.getSourceColumns() )
            && TypeableImpl.bothNullOrEquals( this._targetColumns, another.getTargetColumns() )
            && TypeableImpl.bothNullOrEquals( this._matchType, another.getMatchType() )
            && TypeableImpl.bothNullOrEquals( this._onDelete, another.getOnDelete() )
            && TypeableImpl.bothNullOrEquals( this._onUpdate, another.getOnUpdate() );
    }

    public MatchType getMatchType()
    {
        return this._matchType;
    }

    public ReferentialAction getOnDelete()
    {
        return this._onDelete;
    }

    public ReferentialAction getOnUpdate()
    {
        return this._onUpdate;
    }

    public ColumnNameList getSourceColumns()
    {
        return this._sourceColumns;
    }

    public ColumnNameList getTargetColumns()
    {
        return this._targetColumns;
    }

    public TableNameDirect getTargetTableName()
    {
        return this._targetTableName;
    }

}
