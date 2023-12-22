package io.github.qumn.starter.axon.config

import com.thoughtworks.xstream.XStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class XStreamConfig {
    @Bean
    fun xStream(): XStream {
        val xStream = XStream()
        xStream.allowTypesByWildcard(arrayOf("io.github.qumn.**"))
        return xStream
    }
}
