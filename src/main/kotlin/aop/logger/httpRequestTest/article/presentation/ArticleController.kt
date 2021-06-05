package aop.logger.httpRequestTest.article.presentation

import aop.logger.httpRequestTest.article.service.ArticleService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(private val articleService :ArticleService) {

    @PostMapping("/article")
    fun callArticleInfo(@RequestBody request : Map<String, Any>)  = articleService.getArticleList()
}