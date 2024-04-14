package io.github.qumn.framework.storage.model

import java.io.InputStream

interface Files {
    fun save(inputStream: InputStream, ext: String) : URN
}