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

package org.sql.generation.implementation.transformation;

import org.sql.generation.api.grammar.common.ColumnNameList;
import org.sql.generation.api.grammar.common.SQLConstants;
import org.sql.generation.api.grammar.query.ColumnReferenceByExpression;
import org.sql.generation.api.grammar.query.ColumnReferenceByName;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

import java.util.Iterator;

/**
 * @author Stanislav Muhametsin
 */
public class ColumnProcessing {

    public static class ColumnReferenceByNameProcessor extends AbstractProcessor<ColumnReferenceByName> {
        public ColumnReferenceByNameProcessor() {
            super(ColumnReferenceByName.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final ColumnReferenceByName columnRef,
                                 final StringBuilder builder) {
            final String tableName = columnRef.getTableName();
            if (ProcessorUtils.notNullAndNotEmpty(tableName)) {
                builder.append(tableName).append(SQLConstants.TABLE_COLUMN_SEPARATOR);
            }

            builder.append(columnRef.getColumnName());
        }
    }

    public static class ColumnReferenceByExpressionProcessor extends AbstractProcessor<ColumnReferenceByExpression> {

        public ColumnReferenceByExpressionProcessor() {
            super(ColumnReferenceByExpression.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final ColumnReferenceByExpression columnRef,
                                 final StringBuilder builder) {
            processor.process(columnRef.getExpression(), builder);
        }
    }

    public static class ColumnNamesProcessor extends AbstractProcessor<ColumnNameList> {
        public ColumnNamesProcessor() {
            super(ColumnNameList.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final ColumnNameList object, final StringBuilder builder) {
            builder.append(SQLConstants.OPEN_PARENTHESIS);
            final Iterator<String> iter = object.getColumnNames().iterator();
            while (iter.hasNext()) {
                builder.append(iter.next());
                if (iter.hasNext()) {
                    builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                }
            }
            builder.append(SQLConstants.CLOSE_PARENTHESIS);
        }
    }
}
