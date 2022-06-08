package com.formssi.mall.common.convert;

import org.dozer.DozerConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class LocalDateLongConverter extends DozerConverter<LocalDate, Long> {

    public LocalDateLongConverter() {
        super(LocalDate.class, Long.class);
    }

    @Override
    public LocalDate convertFrom(Long source, LocalDate destination) {
        if (null == source || source <= 0L) return null;
        return Instant.ofEpochMilli(source).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    @Override
    public Long convertTo(LocalDate source, Long destination) {
        if (null == source) return 0L;
        return source.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }
}