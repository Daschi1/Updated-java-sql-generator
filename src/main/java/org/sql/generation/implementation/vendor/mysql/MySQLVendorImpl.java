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

package org.sql.generation.implementation.vendor.mysql;

import org.sql.generation.api.vendor.MySQLVendor;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.implementation.transformation.mysql.MySQLProcessor;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;
import org.sql.generation.implementation.vendor.DefaultVendor;

/**
 * @author Stanislav Muhametsin
 */
public class MySQLVendorImpl extends DefaultVendor
        implements MySQLVendor {

    protected static final ProcessorCallback MYSQL_PROCESSOR = new ProcessorCallback() {
        @Override
        public SQLProcessorAggregator get(final SQLVendor vendor) {
            return new MySQLProcessor(vendor);
        }
    };

    private boolean _legacyLimit;

    public MySQLVendorImpl() {
        super(MySQLVendorImpl.MYSQL_PROCESSOR);

        this._legacyLimit = false;
    }

    @Override
    public boolean legacyLimit() {
        return this._legacyLimit;
    }

    @Override
    public void setLegacyLimit(final boolean useLegacyLimit) {
        this._legacyLimit = useLegacyLimit;
    }

}
