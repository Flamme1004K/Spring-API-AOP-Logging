package aop.logger.httpRequestTest.article.presentation

import aop.logger.httpRequestTest.article.service.ArticleService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(private val articleService :ArticleService) {

    companion object : KLogging()

    @GetMapping("/log")
    fun callLogRainBow() {
        logger.trace("TRACE LOG")
        logger.debug("DEBUG LOG")
        logger.info("INFO LOG")
        logger.warn("WARN LOG")
        logger.error("ERROR LOG")
    }

    @PostMapping("/article")
    fun callArticleInfo(@RequestBody request : Map<String, Any>)  = articleService.getArticleList()
}