package aop.logger.httpRequestTest.article.presentation

import aop.logger.httpRequestTest.article.service.ArticleService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@ExtendWith(SpringExtension::class)
@WebMvcTest
class ArticleControllerTest{

    @MockBean lateinit var articleService: ArticleService
    @MockBean lateinit var articleController: ArticleController
    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var mapper: ObjectMapper

    @Test
    fun testAopLogger() {
        val result = mockMvc.post("/article") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(mapOf("flamme" to "test"))
            accept = MediaType.APPLICATION_JSON
        }.andDo { print() }.andExpect {
            status { isOk() }
        }.andReturn()
        println("responseDate = ${result.response.contentAsString}")
    }
}

