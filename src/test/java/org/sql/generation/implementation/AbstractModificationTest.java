/*
 * Copyright (c) 2012, Stanislav Muhametsin. All Rights Reserved.
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

package org.sql.generation.implementation;

import org.junit.Test;
import org.sql.generation.api.grammar.factories.LiteralFactory;
import org.sql.generation.api.grammar.factories.ModificationFactory;
import org.sql.generation.api.grammar.factories.QueryFactory;
import org.sql.generation.api.grammar.factories.TableReferenceFactory;
import org.sql.generation.api.grammar.modification.InsertStatement;
import org.sql.generation.api.vendor.SQLVendor;

public abstract class AbstractModificationTest extends AbstractSQLSyntaxTest {

    @Test
    public void modification1() {
        // INSERT INTO schema.table
        // VALUES (5, 'String', SELECT column FROM schema.other_table);

        final SQLVendor vendor = this.getVendor();

        final QueryFactory q = vendor.getQueryFactory();
        final TableReferenceFactory t = vendor.getTableReferenceFactory();
        final ModificationFactory m = vendor.getModificationFactory();
        final LiteralFactory l = vendor.getLiteralFactory();

        final InsertStatement insert = this.getVendor().getModificationFactory().insert()
                .setTableName(t.tableName("schema", "table"))
                .setColumnSource(
                        m.columnSourceByValues().addValues(
                                l.n(5),
                                l.s("String"),
                                q.simpleQueryBuilder()
                                        .select("column")
                                        .from(t.tableName("schema", "other_table"))
                                        .createExpression()
                        ).createExpression()
                )
                .createExpression();

        this.logStatement("Table modification", vendor, insert);
    }
}
