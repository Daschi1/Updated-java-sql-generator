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
import org.sql.generation.api.grammar.manipulation.AlterTableAction;
import org.sql.generation.api.grammar.manipulation.DropBehaviour;
import org.sql.generation.api.grammar.manipulation.DropTableConstraintDefinition;
import org.sql.generation.implementation.grammar.common.SQLSyntaxElementBase;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class DropTableConstraintDefinitionImpl extends
        SQLSyntaxElementBase<AlterTableAction, DropTableConstraintDefinition>
        implements DropTableConstraintDefinition {

    private final String _constraintName;
    private final DropBehaviour _dropBehaviour;

    public DropTableConstraintDefinitionImpl(final SQLProcessorAggregator processor, final String constraintName,
                                             final DropBehaviour dropBehaviour) {
        this(processor, DropTableConstraintDefinition.class, constraintName, dropBehaviour);
    }

    protected DropTableConstraintDefinitionImpl(final SQLProcessorAggregator processor,
                                                final Class<? extends DropTableConstraintDefinition> realImplementingType, final String constraintName,
                                                final DropBehaviour dropBehaviour) {
        super(processor, realImplementingType);

        NullArgumentException.validateNotNull("Constraint name", constraintName);
        NullArgumentException.validateNotNull("Drop behaviour", dropBehaviour);

        this._constraintName = constraintName;
        this._dropBehaviour = dropBehaviour;
    }

    @Override
    protected boolean doesEqual(final DropTableConstraintDefinition another) {
        return this._constraintName.equals(another.getConstraintName())
                && this._dropBehaviour.equals(another.getDropBehaviour());
    }

    @Override
    public String getConstraintName() {
        return this._constraintName;
    }

    @Override
    public DropBehaviour getDropBehaviour() {
        return this._dropBehaviour;
    }

}
