package org.sql.generation.implementation.grammar.common.datatypes;

import org.atp.spi.TypeableImpl;
import org.sql.generation.api.grammar.common.datatypes.SQLDataType;
import org.sql.generation.api.grammar.common.datatypes.SQLTime;

/**
 * @author Stanislav Muhametsin
 */
public final class SQLTimeImpl extends TypeableImpl<SQLDataType, SQLTime>
        implements SQLTime {
    private final Integer _precision;
    private final Boolean _withTimeZone;

    public SQLTimeImpl(final Integer precision, final Boolean withTimeZone) {
        super(SQLTime.class);
        this._precision = precision;
        this._withTimeZone = withTimeZone;
    }

    @Override
    protected boolean doesEqual(final SQLTime another) {
        return TypeableImpl.bothNullOrEquals(this._precision, another.getPrecision())
                && TypeableImpl.bothNullOrEquals(this._withTimeZone, another.isWithTimeZone());
    }

    /**
     * Returns the precision for this {@code TIME}. May be {@code null}.
     *
     * @return The precision for this {@code TIME}.
     */
    @Override
    public Integer getPrecision() {
        return this._precision;
    }

    /**
     * Returns whether the {@code TIME} should be with time zone. May be {@code null} if no choice specified.
     *
     * @return
     */
    @Override
    public Boolean isWithTimeZone() {
        return this._withTimeZone;
    }

    public static final SQLTime PLAIN_TIME = new SQLTimeImpl(null, null);

    public static final SQLTime PLAIN_TIME_WITHOUT_TZ = new SQLTimeImpl(null, false);

    public static final SQLTime PLAIN_TIME_WITH_TZ = new SQLTimeImpl(null, true);
}