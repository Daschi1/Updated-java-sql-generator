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

package org.sql.generation.implementation.grammar.modification;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.modification.SetClause;
import org.sql.generation.api.grammar.modification.UpdateSource;
import org.sql.generation.implementation.grammar.common.SQLSyntaxElementBase;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * @author Stanislav Muhametsin
 */
public class SetClauseImpl extends SQLSyntaxElementBase<SetClause, SetClause>
        implements SetClause {

    private final String _target;

    private final UpdateSource _source;

    public SetClauseImpl(final SQLProcessorAggregator processor, final String updateTarget, final UpdateSource updateSource) {
        this(processor, SetClause.class, updateTarget, updateSource);
    }

    protected SetClauseImpl(final SQLProcessorAggregator processor, final Class<? extends SetClause> expressionClass,
                            final String updateTarget, final UpdateSource updateSource) {
        super(processor, expressionClass);
        NullArgumentException.validateNotNull("update target", updateTarget);
        NullArgumentException.validateNotNull("source", updateSource);

        this._target = updateTarget;
        this._source = updateSource;
    }

    @Override
    public UpdateSource getUpdateSource() {
        return this._source;
    }

    @Override
    public String getUpdateTarget() {
        return this._target;
    }

    @Override
    protected boolean doesEqual(final SetClause another) {
        return this._target.equals(another.getUpdateTarget()) && this._source.equals(another.getUpdateSource());
    }

}
