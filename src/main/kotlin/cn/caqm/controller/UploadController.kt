@file:Suppress("ktlint:standard:no-wildcard-imports")

package cn.caqm.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/upload")
@Tag(name = "文件管理", description = "文件上传下载接口")
class UploadController {
    private val logger = LoggerFactory.getLogger(UploadController::class.java)

    // 文件存储在C盘home目录
    private val uploadDir: Path = Paths.get("C:/home").toAbsolutePath().normalize()

    init {
        try {
            Files.createDirectories(uploadDir)
            logger.info("文件存储目录创建成功: $uploadDir")
        } catch (e: IOException) {
            logger.error("无法创建文件存储目录: $uploadDir", e)
            throw RuntimeException("无法创建文件存储目录", e)
        }
    }

    @PostMapping("/file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(
        summary = "上传文件",
        description = "上传文件到C盘home目录并返回文件路径",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "文件上传成功",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE)],
            ),
        ],
    )
    fun uploadFile(
        @Parameter(description = "文件", required = true)
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<Map<String, Any>> {
        try {
            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
            val originalFilename = file.originalFilename ?: "unnamed_file"
            val fileName = "${timestamp}_$originalFilename"

            val targetLocation = uploadDir.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

            // 只保存文件名作为路径，不包含完整的系统路径
            val storedFilePath = fileName

            val response =
                mapOf(
                    "success" to true,
                    "message" to "文件上传成功",
                    "filePath" to storedFilePath, // 仅保存文件名，不包含系统路径
                    "fileName" to originalFilename,
                    "fileSize" to file.size,
                )

            logger.info("文件上传成功: $storedFilePath (实际路径: $targetLocation)")
            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            logger.error("文件上传失败", e)
            val response =
                mapOf(
                    "success" to false,
                    "message" to "文件上传失败: ${e.message}",
                )
            return ResponseEntity.badRequest().body(response)
        }
    }

    @GetMapping("/file")
    @Operation(
        summary = "下载文件",
        description = "根据文件路径下载文件",
        parameters = [
            Parameter(
                name = "filePath",
                description = "文件名，例如: 20240423_123456_example.txt",
                required = true,
                `in` = ParameterIn.QUERY,
            ),
        ],
        responses = [
            ApiResponse(responseCode = "200", description = "文件下载成功"),
            ApiResponse(responseCode = "404", description = "文件不存在"),
        ],
    )
    fun downloadFile(
        @RequestParam filePath: String,
    ): ResponseEntity<Resource> {
        try {
            // 日志调试
            logger.info("接收到下载请求: filePath=$filePath")

            // 获取文件名 - 如果传入的是完整路径，则从路径中提取文件名
            val fileName =
                if (filePath.contains("/")) {
                    filePath.substringAfterLast('/')
                } else {
                    filePath // 假设只传入了文件名
                }

            // 构建绝对文件路径
            val fullPath = uploadDir.resolve(fileName).normalize()
            logger.info("尝试查找文件: $fullPath")

            // 检查文件是否存在
            val file = fullPath.toFile()
            if (!file.exists()) {
                logger.error("文件不存在: $fullPath")
                // 列出目录内容以助调试
                val directoryContents = uploadDir.toFile().listFiles()?.joinToString(", ") { it.name } ?: "空目录"
                logger.info("目录 $uploadDir 中的文件: $directoryContents")
                return ResponseEntity.notFound().build()
            }

            // 确保文件路径安全 (防止路径遍历攻击)
            if (!fullPath.startsWith(uploadDir)) {
                logger.error("访问目录外的文件被拒绝: $fullPath")
                return ResponseEntity.badRequest().build()
            }

            val resource = UrlResource(fullPath.toUri())

            // 设置文件名编码
            val encodedFileName =
                URLEncoder
                    .encode(fileName, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20")

            logger.info("文件已找到，准备下载: $fullPath")

            return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$encodedFileName\"")
                .body(resource)
        } catch (e: Exception) {
            logger.error("文件下载失败", e)
            throw RuntimeException("文件下载失败: ${e.message}", e)
        }
    }

    @GetMapping("/list")
    @Operation(summary = "列出所有文件", description = "列出上传目录中的所有文件")
    fun listFiles(): ResponseEntity<Map<String, Any>> {
        try {
            val files = uploadDir.toFile().listFiles()
            val fileList =
                files?.map { file ->
                    mapOf(
                        "name" to file.name,
                        "path" to file.name, // 只返回文件名作为路径
                        "size" to file.length(),
                        "lastModified" to file.lastModified(),
                    )
                } ?: emptyList()

            return ResponseEntity.ok(
                mapOf(
                    "success" to true,
                    "message" to "文件列表获取成功",
                    "files" to fileList,
                ),
            )
        } catch (e: Exception) {
            logger.error("获取文件列表失败", e)
            return ResponseEntity.badRequest().body(
                mapOf(
                    "success" to false,
                    "message" to "获取文件列表失败: ${e.message}",
                ),
            )
        }
    }

    @GetMapping("/file/test")
    @Operation(summary = "测试接口", description = "返回一个简单消息，用于测试API是否正常工作")
    fun testApi(): Map<String, Any> {
        val directoryExists = Files.exists(uploadDir)
        val isDirectory = Files.isDirectory(uploadDir)
        val isReadable = Files.isReadable(uploadDir)
        val isWritable = Files.isWritable(uploadDir)

        val fileCount =
            try {
                Files.list(uploadDir).count()
            } catch (e: Exception) {
                -1
            }

        return mapOf(
            "message" to "文件上传下载API正常工作中",
            "uploadDir" to uploadDir.toString(),
            "directoryExists" to directoryExists,
            "isDirectory" to isDirectory,
            "isReadable" to isReadable,
            "isWritable" to isWritable,
            "fileCount" to fileCount,
        )
    }
}
