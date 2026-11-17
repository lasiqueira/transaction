package com.lasiqueira.transaction.api.serializer.v1;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneySerializer extends ValueSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializationContext context) {
        jgen.writeString(value.setScale(2, RoundingMode.HALF_UP).toString());
    }
}
