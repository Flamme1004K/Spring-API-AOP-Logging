package aop.logger.httpRequestTest.article.domain

import java.time.LocalDateTime

data class Article(
    val id: Long,
    val subject: String,
    val content: String,
    val createDate: LocalDateTime
) {
    constructor(id: Long, subject: String, content: String) : this(
        id = id, subject = subject, content = content, createDate = LocalDateTime.now()
    )
}