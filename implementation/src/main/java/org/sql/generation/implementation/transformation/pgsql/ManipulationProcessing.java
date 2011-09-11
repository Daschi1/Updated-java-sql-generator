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

package org.sql.generation.implementation.transformation.pgsql;

import org.sql.generation.api.grammar.common.SQLConstants;
import org.sql.generation.api.grammar.manipulation.DropTableOrViewStatement;
import org.sql.generation.api.grammar.manipulation.pgsql.PgSQLDropTableOrViewStatement;
import org.sql.generation.implementation.transformation.ManipulationProcessing.DropTableOrViewStatementProcessor;
import org.sql.generation.implementation.transformation.ProcessorUtils;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class ManipulationProcessing
{

    public static class PgSQLDropTableOrViewStatementProcessor extends DropTableOrViewStatementProcessor
    {

        @Override
        protected void doProcess( SQLProcessorAggregator aggregator, DropTableOrViewStatement object,
            StringBuilder builder )
        {
            builder.append( "DROP" ).append( SQLConstants.TOKEN_SEPARATOR )
                .append( this.getObjectTypes().get( object.whatToDrop() ) ).append( SQLConstants.TOKEN_SEPARATOR );

            Boolean useIfExists = ((PgSQLDropTableOrViewStatement) object).useIfExists();
            if( useIfExists )
            {
                builder.append( "IF EXISTS" ).append( SQLConstants.TOKEN_SEPARATOR );
            }

            aggregator.process( object.getTableName(), builder );

            ProcessorUtils.processDropBehaviour( object.getDropBehaviour(), builder );
        }
    }
}
