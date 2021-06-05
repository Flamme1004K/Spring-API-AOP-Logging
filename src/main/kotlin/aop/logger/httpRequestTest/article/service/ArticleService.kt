package aop.logger.httpRequestTest.article.service

import aop.logger.httpRequestTest.article.domain.Article
import org.springframework.stereotype.Service

@Service
class ArticleService {
    private val articleList = listOf(
        Article(1L, "제목이다!1", "내용이다!1"),
        Article(2L, "제목이다!2", "내용이다!2"),
        Article(3L, "제목이다!3", "내용이다!3"),
        Article(4L, "제목이다!4", "내용이다!4"),
        Article(5L, "제목이다!5", "내용이다!5")
    )


    fun getArticleList(): List<Article> = articleList
}