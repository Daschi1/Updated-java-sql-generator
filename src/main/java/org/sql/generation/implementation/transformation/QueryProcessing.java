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

import org.atp.api.Typeable;
import org.slf4j.LoggerFactory;
import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.booleans.BooleanExpression;
import org.sql.generation.api.grammar.common.NonBooleanExpression;
import org.sql.generation.api.grammar.common.SQLConstants;
import org.sql.generation.api.grammar.common.ValueExpression;
import org.sql.generation.api.grammar.literals.LiteralExpression;
import org.sql.generation.api.grammar.query.*;
import org.sql.generation.api.grammar.query.ColumnReferences.ColumnReferenceInfo;
import org.sql.generation.implementation.grammar.booleans.BooleanUtils;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Stanislav Muhametsin
 */
public class QueryProcessing {

    public static void processOptionalBooleanExpression(final SQLProcessorAggregator processor,
                                                        final StringBuilder builder,
                                                        final BooleanExpression expression, final String prefix, final String name) {
        if (expression != null && !BooleanUtils.isEmpty(expression)) {
            QueryProcessing.processOptional(processor, builder, expression, prefix, name);
        }
    }

    public static void processOptional(final SQLProcessorAggregator processor, final StringBuilder builder,
                                       final Typeable<?> element,
                                       final String prefix, final String name) {
        if (element != null) {
            builder.append(prefix);
            if (name != null) {
                builder.append(name).append(SQLConstants.TOKEN_SEPARATOR);
            }
            processor.process(element, builder);
        }
    }

    public static class QueryExpressionBinaryProcessor extends
            AbstractProcessor<QueryExpressionBodyBinary> {
        private static final Map<SetOperation, String> _defaultSetOperations;

        static {
            final Map<SetOperation, String> operations = new HashMap<>();
            operations.put(SetOperation.EXCEPT, "EXCEPT");
            operations.put(SetOperation.INTERSECT, "INTERSECT");
            operations.put(SetOperation.UNION, "UNION");
            _defaultSetOperations = operations;
        }

        private final Map<SetOperation, String> _setOperations;

        public QueryExpressionBinaryProcessor() {
            this(QueryExpressionBinaryProcessor._defaultSetOperations);
        }

        public QueryExpressionBinaryProcessor(final Map<SetOperation, String> setOperations) {
            super(QueryExpressionBodyBinary.class);
            NullArgumentException.validateNotNull("set operations", setOperations);

            this._setOperations = setOperations;
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final QueryExpressionBodyBinary body,
                                 final StringBuilder builder) {
            final Boolean leftIsNotEmpty =
                    body.getLeft() != QueryExpressionBody.EmptyQueryExpressionBody.INSTANCE;
            if (leftIsNotEmpty) {
                builder.append(SQLConstants.OPEN_PARENTHESIS);
                processor.process(body.getLeft(), builder);
                builder.append(SQLConstants.CLOSE_PARENTHESIS).append(SQLConstants.NEWLINE);
                this.processSetOperation(body.getSetOperation(), builder);

                builder.append(SQLConstants.TOKEN_SEPARATOR);
                ProcessorUtils.processSetQuantifier(body.getSetQuantifier(), builder);

                final CorrespondingSpec correspondingCols = body.getCorrespondingColumns();
                if (correspondingCols != null) {
                    builder.append(SQLConstants.TOKEN_SEPARATOR);
                    processor.process(correspondingCols, builder);
                }

                builder.append(SQLConstants.NEWLINE).append(SQLConstants.OPEN_PARENTHESIS);
            }
            processor.process(body.getRight(), builder);
            if (leftIsNotEmpty) {
                builder.append(SQLConstants.CLOSE_PARENTHESIS);
            }
        }

        protected void processSetOperation(final SetOperation operation, final StringBuilder builder) {
            builder.append(this._setOperations.get(operation));
        }
    }

    public static class QuerySpecificationProcessor extends AbstractProcessor<QuerySpecification> {

        public QuerySpecificationProcessor() {
            this(QuerySpecification.class);
        }

        public QuerySpecificationProcessor(final Class<? extends QuerySpecification> queryClass) {
            super(queryClass);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final QuerySpecification query,
                                 final StringBuilder builder) {
            builder.append(SQLConstants.SELECT).append(SQLConstants.TOKEN_SEPARATOR);
            ProcessorUtils.processSetQuantifier(query.getColumns().getSetQuantifier(), builder);
            builder.append(SQLConstants.TOKEN_SEPARATOR);

            processor.process(query.getColumns(), builder);
            processor.process(query.getFrom(), builder);
            QueryProcessing.processOptionalBooleanExpression(processor, builder, query.getWhere(),
                    SQLConstants.NEWLINE, SQLConstants.WHERE);
            processor.process(query.getGroupBy(), builder);
            QueryProcessing.processOptionalBooleanExpression(processor, builder,
                    query.getHaving(),
                    SQLConstants.NEWLINE, SQLConstants.HAVING);
            processor.process(query.getOrderBy(), builder);
            Typeable<?> first = null;
            Typeable<?> second = null;
            if (this.isOffsetBeforeLimit(processor)) {
                first = query.getOffsetSpecification();
                second = query.getLimitSpecification();
            } else {
                first = query.getLimitSpecification();
                second = query.getOffsetSpecification();
            }

            if (first != null || second != null) {
                this.processLimitAndOffset(processor, builder, first, second);
            }

            if (query.getOrderBy() == null
                    && (query.getOffsetSpecification() != null || query.getLimitSpecification() != null)) {
                LoggerFactory.getLogger(this.getClass().getName()).warn(
                        "Spotted query with " + SQLConstants.OFFSET_PREFIX + " and/or "
                                + SQLConstants.LIMIT_PREFIX
                                + " clause, but without ORDER BY. The result will be unpredictable!"
                                + "\n" + "Query: "
                                + builder.toString());
            }
        }

        protected boolean isOffsetBeforeLimit(final SQLProcessorAggregator processor) {
            return true;
        }

        protected void processLimitAndOffset(final SQLProcessorAggregator processor,
                                             final StringBuilder builder,
                                             final Typeable<?> first, final Typeable<?> second) {
            QueryProcessing.processOptional(processor, builder, first, SQLConstants.NEWLINE, null);
            QueryProcessing
                    .processOptional(processor, builder, second, SQLConstants.NEWLINE, null);
        }

    }

    public static class SelectColumnsProcessor extends AbstractProcessor<SelectColumnClause> {
        public SelectColumnsProcessor() {
            super(SelectColumnClause.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final SelectColumnClause select,
                                 final StringBuilder builder) {
            if (select instanceof ColumnReferences) {
                final Iterator<ColumnReferenceInfo> iter =
                        ((ColumnReferences) select).getColumns().iterator();
                while (iter.hasNext()) {
                    final ColumnReferenceInfo info = iter.next();
                    aggregator.process(info.getReference(), builder);
                    final String alias = info.getAlias();
                    if (ProcessorUtils.notNullAndNotEmpty(alias)) {
                        builder.append(SQLConstants.TOKEN_SEPARATOR)
                                .append(SQLConstants.ALIAS_DEFINER)
                                .append(SQLConstants.TOKEN_SEPARATOR).append(alias);
                    }

                    if (iter.hasNext()) {
                        builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                    }
                }
            } else {
                builder.append(SQLConstants.ASTERISK);
            }
        }
    }

    public static class FromProcessor extends AbstractProcessor<FromClause> {
        public FromProcessor() {
            super(FromClause.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final FromClause from,
                                 final StringBuilder builder) {
            if (!from.getTableReferences().isEmpty()) {
                builder.append(SQLConstants.NEWLINE).append(SQLConstants.FROM)
                        .append(SQLConstants.TOKEN_SEPARATOR);
                final Iterator<TableReference> iter = from.getTableReferences().iterator();
                while (iter.hasNext()) {
                    aggregator.process(iter.next().asTypeable(), builder);
                    if (iter.hasNext()) {
                        builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                    }
                }
            }
        }
    }

    public static class QueryExpressionProcessor extends AbstractProcessor<QueryExpression> {
        public QueryExpressionProcessor() {
            super(QueryExpression.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final QueryExpression object,
                                 final StringBuilder builder) {
            processor.process(object.getQueryExpressionBody(), builder);
        }
    }

    public static class CorrespondingSpecProcessor extends AbstractProcessor<CorrespondingSpec> {
        public CorrespondingSpecProcessor() {
            super(CorrespondingSpec.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final CorrespondingSpec object,
                                 final StringBuilder builder) {
            builder.append("CORRESPONDING");
            if (object.getColumnList() != null) {
                builder.append(SQLConstants.TOKEN_SEPARATOR).append("BY")
                        .append(SQLConstants.TOKEN_SEPARATOR);
                processor.process(object.getColumnList(), builder);
            }
        }
    }

    public static class SortSpecificationProcessor extends AbstractProcessor<SortSpecification> {
        private static final Map<Ordering, String> _defaultOrderingStrings;

        static {
            final Map<Ordering, String> map = new HashMap<>();
            map.put(Ordering.ASCENDING, "ASC");
            map.put(Ordering.DESCENDING, "DESC");
            _defaultOrderingStrings = map;
        }

        private final Map<Ordering, String> _orderingStrings;

        public SortSpecificationProcessor() {
            this(SortSpecificationProcessor._defaultOrderingStrings);
        }

        public SortSpecificationProcessor(final Map<Ordering, String> orderingStrings) {
            super(SortSpecification.class);
            NullArgumentException.validateNotNull("ordering strings", orderingStrings);
            this._orderingStrings = orderingStrings;
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final SortSpecification object,
                                 final StringBuilder builder) {
            processor.process(object.getValueExpression(), builder);
            builder.append(SQLConstants.TOKEN_SEPARATOR).append(
                    this._orderingStrings.get(object.getOrderingSpecification()));
        }
    }

    public static class OrdinaryGroupingSetProcessor extends AbstractProcessor<OrdinaryGroupingSet> {
        public OrdinaryGroupingSetProcessor() {
            super(OrdinaryGroupingSet.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator processor, final OrdinaryGroupingSet object,
                                 final StringBuilder builder) {
            final Iterator<NonBooleanExpression> iter = object.getColumns().iterator();
            while (iter.hasNext()) {
                processor.process(iter.next(), builder);
                if (iter.hasNext()) {
                    builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                }
            }
        }
    }

    public static class GroupByProcessor extends AbstractProcessor<GroupByClause> {
        public GroupByProcessor() {
            super(GroupByClause.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final GroupByClause groupBy,
                                 final StringBuilder builder) {
            if (!groupBy.getGroupingElements().isEmpty()) {
                builder.append(SQLConstants.NEWLINE).append(SQLConstants.GROUP_BY)
                        .append(SQLConstants.TOKEN_SEPARATOR);
                final Iterator<GroupingElement> iter = groupBy.getGroupingElements().iterator();
                while (iter.hasNext()) {
                    aggregator.process(iter.next(), builder);
                    if (iter.hasNext()) {
                        builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                    }
                }
            }
        }
    }

    public static class OrderByProcessor extends AbstractProcessor<OrderByClause> {
        public OrderByProcessor() {
            super(OrderByClause.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final OrderByClause orderBy,
                                 final StringBuilder builder) {
            if (!orderBy.getOrderingColumns().isEmpty()) {
                builder.append(SQLConstants.NEWLINE).append(SQLConstants.ORDER_BY)
                        .append(SQLConstants.TOKEN_SEPARATOR);
                final Iterator<SortSpecification> iter = orderBy.getOrderingColumns().iterator();
                while (iter.hasNext()) {
                    aggregator.process(iter.next(), builder);
                    if (iter.hasNext()) {
                        builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                    }
                }
            }
        }
    }

    public static class TableValueConstructorProcessor extends
            AbstractProcessor<TableValueConstructor> {
        public TableValueConstructorProcessor() {
            super(TableValueConstructor.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final TableValueConstructor object,
                                 final StringBuilder builder) {
            builder.append("VALUES").append(SQLConstants.TOKEN_SEPARATOR);
            final Iterator<RowValueConstructor> iter = object.getRows().iterator();
            while (iter.hasNext()) {
                aggregator.process(iter.next(), builder);
                if (iter.hasNext()) {
                    builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                }
            }
        }
    }

    public static class RowSubQueryProcessor extends AbstractProcessor<RowSubQuery> {
        public RowSubQueryProcessor() {
            super(RowSubQuery.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final RowSubQuery object,
                                 final StringBuilder builder) {
            builder.append(SQLConstants.OPEN_PARENTHESIS);
            aggregator.process(object.getQueryExpression(), builder);
            builder.append(SQLConstants.CLOSE_PARENTHESIS);
        }
    }

    public static class RowDefinitionProcessor extends AbstractProcessor<RowDefinition> {
        public RowDefinitionProcessor() {
            super(RowDefinition.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final RowDefinition object,
                                 final StringBuilder builder) {
            builder.append(SQLConstants.OPEN_PARENTHESIS);
            final Iterator<ValueExpression> vals = object.getRowElements().iterator();
            while (vals.hasNext()) {
                aggregator.process(vals.next(), builder);
                if (vals.hasNext()) {
                    builder.append(SQLConstants.COMMA).append(SQLConstants.TOKEN_SEPARATOR);
                }
            }
            builder.append(SQLConstants.CLOSE_PARENTHESIS);
        }
    }

    public static class OffsetSpecificationProcessor extends AbstractProcessor<OffsetSpecification> {

        public OffsetSpecificationProcessor() {
            super(OffsetSpecification.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final OffsetSpecification object,
                                 final StringBuilder builder) {
            final String prefix = this.getPrefix(aggregator);
            if (prefix != null) {
                builder.append(prefix).append(SQLConstants.TOKEN_SEPARATOR);
            }
            final NonBooleanExpression skip = object.getSkip();
            final boolean isComplex = !(skip instanceof LiteralExpression);
            if (isComplex) {
                builder.append(SQLConstants.OPEN_PARENTHESIS).append(SQLConstants.NEWLINE);
            }
            aggregator.process(skip, builder);
            if (isComplex) {
                builder.append(SQLConstants.CLOSE_PARENTHESIS);
            }
            final String postfix = this.getPostfix(aggregator);
            if (postfix != null) {
                builder.append(SQLConstants.TOKEN_SEPARATOR).append(postfix);
            }
        }

        protected String getPrefix(final SQLProcessorAggregator processor) {
            return SQLConstants.OFFSET_PREFIX;
        }

        protected String getPostfix(final SQLProcessorAggregator processor) {
            return SQLConstants.OFFSET_POSTFIX;
        }
    }

    public static class LimitSpecificationProcessor extends AbstractProcessor<LimitSpecification> {
        public LimitSpecificationProcessor() {
            super(LimitSpecification.class);
        }

        @Override
        protected void doProcess(final SQLProcessorAggregator aggregator, final LimitSpecification object,
                                 final StringBuilder builder) {
            final NonBooleanExpression count = this.getRealCount(object.getCount());
            if (count != null) {
                final String prefix = this.getPrefix(aggregator);
                if (prefix != null) {
                    builder.append(prefix).append(SQLConstants.TOKEN_SEPARATOR);
                }
                final boolean isComplex = !(count instanceof LiteralExpression);
                if (isComplex) {
                    builder.append(SQLConstants.OPEN_PARENTHESIS).append(SQLConstants.NEWLINE);
                }
                aggregator.process(count, builder);
                if (isComplex) {
                    builder.append(SQLConstants.CLOSE_PARENTHESIS);
                }
                final String postfix = this.getPostfix(aggregator);
                if (postfix != null) {
                    builder.append(SQLConstants.TOKEN_SEPARATOR).append(postfix);
                }
            }
        }

        protected NonBooleanExpression getRealCount(final NonBooleanExpression limitCount) {
            return limitCount;
        }

        protected String getPrefix(final SQLProcessorAggregator processor) {
            return SQLConstants.LIMIT_PREFIX;
        }

        protected String getPostfix(final SQLProcessorAggregator processor) {
            return SQLConstants.LIMIT_POSTFIX;
        }

    }
}
