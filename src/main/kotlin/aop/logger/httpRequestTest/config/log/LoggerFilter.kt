package aop.logger.httpRequestTest.config.log

import mu.KLogging
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap

@WebFilter(urlPatterns = ["/*"])
class LoggerFilter : Filter{
    companion object : KLogging()

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse?, chain: FilterChain) {
        val requestWrapper = ContentCachingRequestWrapper((request as HttpServletRequest))
        val responseWrapper = ContentCachingResponseWrapper((response as HttpServletResponse?)!!)
        val start = System.currentTimeMillis()
        chain.doFilter(requestWrapper, responseWrapper)
        val end = System.currentTimeMillis()
        logger.info(
            "\n" +
                    "[REQUEST] {} - {} {} - {}\n" +
                    "Headers : {}\n" +
                    "Request : {}\n" +
                    "Response : {}\n",
            request.method,
            request.requestURI,
            responseWrapper.status,
            (end - start) / 1000.0,
            getHeaders(request),
            getRequestBody(requestWrapper),
            getResponseBody(responseWrapper)
        )
    }

    private fun getHeaders(request: HttpServletRequest): MutableMap<Any, Any> {
        val headerMap: MutableMap<Any, Any> = HashMap<Any, Any>()
        val headerArray: Enumeration<*> = request.headerNames
        while (headerArray.hasMoreElements()) {
            val headerName = headerArray.nextElement() as String
            headerMap[headerName] = request.getHeader(headerName)
        }
        return headerMap
    }

    private fun getRequestBody(request: ContentCachingRequestWrapper): String? {
        val wrapper = WebUtils.getNativeRequest(
            request,
            ContentCachingRequestWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                try {
                    return String(buf, 0, buf.size, charset(wrapper.characterEncoding))
                } catch (e: UnsupportedEncodingException) {
                    return " - "
                }
            }
        }
        return " - "
    }

    @Throws(IOException::class)
    private fun getResponseBody(response: HttpServletResponse): String {
        var payload: String? = null
        val wrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        if (wrapper != null) {
            val buf = wrapper.contentAsByteArray
            if (buf.isNotEmpty()) {
                payload = String(buf, 0, buf.size, charset(wrapper.characterEncoding))
                wrapper.copyBodyToResponse()
            }
        }
        return payload ?: " - "
    }

    override fun destroy() {}
}