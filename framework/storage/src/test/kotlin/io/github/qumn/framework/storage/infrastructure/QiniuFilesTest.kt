package io.github.qumn.framework.storage.infrastructure

import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempfile
import java.io.File

class QiniuFilesTest : StringSpec({
    "upload file should work" {
        val qiniuFiles = QiniuFiles("s4SPEO65jbQcaYETip7cdGah3WjAiriKvjM7ucXt", "6MC03xfL7ugL-wNV5sSJv-6kiAj3E3arZwwpsI81", "alumnihub", "storage.qumn.xyz")
        val tempfile = tempfile("tempFile", ".txt")
        tempfile.writeText("test uploading file to qiniu")
        qiniuFiles.save(tempfile.inputStream() ,"txt")
    }
})
