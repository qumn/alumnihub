package io.github.qumn.framework.storage.infrastructure

import com.qiniu.storage.Configuration
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.qumn.framework.storage.model.Files
import io.github.qumn.framework.storage.model.URN
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class QiniuFiles(
    @Value("\${alumnihub.qiniu.access_key}")
    val accessKey: String,
    @Value("\${alumnihub.qiniu.secret_key}")
    val secretKey: String,
    @Value("\${alumnihub.qiniu.bucket}")
    val bucket: String,
    @Value("\${alumnihub.qiniu.domain}")
    val domain: String,
) : Files {
    val auth: Auth = Auth.create(accessKey, secretKey)
    val uploadManager = UploadManager(Configuration());
    val logger = KotlinLogging.logger {}

    override fun save(inputStream: InputStream, ext: String): URN {
        val urn = URN.generate(ext)
        val uploadToken = auth.uploadToken(bucket, urn.name)
        uploadManager.put(inputStream, urn.name, uploadToken, null, null)
        logger.debug { "upload ${urn} to qiniu success" }
        return urn
    }

}