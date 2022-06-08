package com.formssi.mall.common.convert;

import org.dozer.DozerConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeLongConverter extends DozerConverter<LocalDateTime, Long> {

    public LocalDateTimeLongConverter() {
        super(LocalDateTime.class, Long.class);
    }

    @Override
    public LocalDateTime convertFrom(Long source, LocalDateTime destination) {
        if (null == source || source <= 0L) return null;
        return Instant.ofEpochMilli(source).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    @Override
    public Long convertTo(LocalDateTime source, Long destination) {
        if (null == source) return 0L;
        return source.toEpochSecond(ZoneOffset.ofHours(8));
    }
}