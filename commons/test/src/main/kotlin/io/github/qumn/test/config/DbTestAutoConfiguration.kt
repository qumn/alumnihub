package io.github.qumn.test.config

import io.github.qumn.starter.ktorm.KtormAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Import

@AutoConfiguration
@Import(
    value = [
        KtormAutoConfiguration::class,
        DataSourceAutoConfiguration::class,
    ]
)
public class DbTestAutoConfiguration