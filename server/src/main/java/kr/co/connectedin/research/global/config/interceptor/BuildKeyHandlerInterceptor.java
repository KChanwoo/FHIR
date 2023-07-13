/**
 * Class that implements HandlerInterceptor interface.
 * This contains the logic to be processed before the controller.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.co.connectedin.research.global.config.interceptor;

import kr.co.connectedin.research.global.config.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BuildKeyHandlerInterceptor implements HandlerInterceptor {

    @Value("${build-key}")
    private String buildKey;

    public BuildKeyHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {

    }

    /**
     * If the user's token is invalid, access is restricted.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String key = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("build-key".equals(cookie.getName())) {
                    key = cookie.getValue();
                }
            }
        }

        if (key == null) {
            key = request.getHeader("build-key");
        }

        return buildKey.equals(key);
    }
}
