package aop.logger.httpRequestTest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan


@SpringBootApplication
@ServletComponentScan
class HttpRequestTestApplication

fun main(args: Array<String>) {
	runApplication<HttpRequestTestApplication>(*args)
}

