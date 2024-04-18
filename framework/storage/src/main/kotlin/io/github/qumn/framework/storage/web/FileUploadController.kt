package io.github.qumn.framework.storage.web

import io.github.qumn.framework.storage.model.Files
import io.github.qumn.framework.storage.model.URL
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import org.apache.commons.io.FilenameUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/files")
class FileUploadController(
    val files: Files,
) {
    @PostMapping("/upload")
    fun upload(@RequestPart("file") multipartFile: MultipartFile): Rsp<URL> {
        val ext = FilenameUtils.getExtension(multipartFile.originalFilename)
        return files.save(multipartFile.inputStream, ext).toURL().toRsp()
    }
}