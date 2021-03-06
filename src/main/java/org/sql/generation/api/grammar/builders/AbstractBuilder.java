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

package org.sql.generation.api.grammar.builders;

/**
 * A common interface for all builders.
 *
 * @param <ExpressionType> The type of the expression being built.
 * @author Stanislav Muhametsin
 */
public interface AbstractBuilder<ExpressionType> {
    /**
     * Returns the expression which was being built by this builder.
     *
     * @return The expression built by this builder.
     */
    ExpressionType createExpression();
}
