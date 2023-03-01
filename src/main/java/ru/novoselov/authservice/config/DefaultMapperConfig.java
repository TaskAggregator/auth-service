package ru.novoselov.authservice.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        nullValueCheckStrategy =  NullValueCheckStrategy.ALWAYS,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public class DefaultMapperConfig {
}
