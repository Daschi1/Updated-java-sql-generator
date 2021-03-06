package org.sql.generation.implementation.grammar.common.datatypes;

import org.atp.spi.TypeableImpl;
import org.sql.generation.api.grammar.common.datatypes.Decimal;
import org.sql.generation.api.grammar.common.datatypes.SQLDataType;

/**
 * @author Stanislav Muhametsin
 */
public final class DecimalImpl extends TypeableImpl<SQLDataType, Decimal>
        implements Decimal {
    private final Integer _precision;
    private final Integer _scale;

    public DecimalImpl(final Integer precision, final Integer scale) {
        super(Decimal.class);

        this._precision = precision;
        this._scale = scale;
    }

    @Override
    protected boolean doesEqual(final Decimal another) {
        return TypeableImpl.bothNullOrEquals(this._precision, another.getPrecision())
                && TypeableImpl.bothNullOrEquals(this._scale, another.getScale());
    }

    /**
     * Returns the precision (first integer) for this {@code DECIMAL}.
     *
     * @return The precision for this {@code DECIMAL}.
     */
    @Override
    public Integer getPrecision() {
        return this._precision;
    }

    /**
     * Returns the scale (second integer) for this {@code DECIMAL}.
     *
     * @return The precision for this {@code DECIMAL}.
     */
    @Override
    public Integer getScale() {
        return this._scale;
    }

    /**
     * This instance represents {@code DECIMAL} without precision and scale.
     */
    public static final Decimal PLAIN_DECIMAL = new DecimalImpl(null, null);

}