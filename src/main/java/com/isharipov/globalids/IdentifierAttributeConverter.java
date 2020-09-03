package com.isharipov.globalids;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public abstract class IdentifierAttributeConverter<I extends Identifier> implements AttributeConverter<Identifier, String> {
    public String convertToDatabaseColumn(Identifier attribute) {
        return attribute.formattedValue();
    }

    public Identifier convertToEntityAttribute(String dbData) {
        return instance(dbData);
    }

    protected abstract I instance(String value);
}
