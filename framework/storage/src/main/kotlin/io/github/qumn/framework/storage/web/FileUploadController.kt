package io.github.qumn.framework.storage.web

import io.github.qumn.framework.storage.model.Files
import io.github.qumn.framework.web.common.Rsp
import io.github.qumn.framework.web.common.toRsp
import org.apache.commons.io.FilenameUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/files")
class FileUploadController(
    val files: Files,
) {
    @PostMapping("/upload")
    fun upload(@RequestParam("file") multipartFile: MultipartFile): Rsp<String> {
        val ext = FilenameUtils.getExtension(multipartFile.originalFilename)
        val url = files.save(multipartFile.inputStream, ext).toURL()
        return url.location.toRsp()
    }
}