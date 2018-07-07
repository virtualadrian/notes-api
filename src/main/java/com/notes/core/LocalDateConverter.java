package com.notes.core;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
        return Optional.ofNullable(locDate)
            .map(Date::valueOf)
            .orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
        return Optional.ofNullable(sqlDate)
            .map(Date::toLocalDate)
            .orElse(null);
    }
}
