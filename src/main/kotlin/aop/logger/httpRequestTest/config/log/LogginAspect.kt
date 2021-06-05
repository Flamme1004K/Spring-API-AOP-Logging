package aop.logger.httpRequestTest.config.log

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.apache.http.entity.ContentType
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest


@Component
@Aspect
class LogginAspect {
    companion object : KLogging()

    @Pointcut(
        "within(@org.springframework.stereotype.Repository *)" +
                " || within(@org.springframework.stereotype.Service *)" +
                " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    fun setPointcut() {
    }


    @Around("setPointcut()")
    @Throws(Throwable::class)
    fun logging(joinPoint: ProceedingJoinPoint): Any? {
        val result: Any = joinPoint.proceed()
        val request: HttpServletRequest =
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request // request 정보를 가져온다.
        val params = mutableMapOf<String, Any>()

        if (request.contentType != null && request.contentType.contains(ContentType.MULTIPART_FORM_DATA.mimeType)) {
            return result
        }

        try {
            params["controller"] = joinPoint.signature.declaringType.simpleName
            params["method"] = joinPoint.signature.name
            params["methodParameter"] = ObjectMapper().writeValueAsString(joinPoint.args)
            params["header"] = request.getHeader("key")?:"".plus(request.getHeader("token")?:"")
            params["log_time"] = Date()
            params["request_uri"] = request.requestURI
            params["http_method"] = request.method
        } catch (e: Exception) {
            logger.error("LoggerAspect error", e)
        }
        logger.info("params : {}", params);
        return result
    }

}