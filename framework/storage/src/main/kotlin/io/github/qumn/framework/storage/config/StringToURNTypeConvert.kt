package io.github.qumn.framework.storage.config

import io.github.qumn.framework.storage.model.URN
import org.springframework.core.convert.converter.Converter


class StringToURNTypeConvert : Converter<String, URN> {
    override fun convert(name: String): URN {
        return URN(name)
    }
}
