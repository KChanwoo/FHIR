/**
 * Class to configure Spring related configuration.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.06
 */
package kr.co.connectedin.research.global.config;

import kr.co.connectedin.research.global.config.interceptor.BuildKeyHandlerInterceptor;
import kr.co.connectedin.research.global.config.interceptor.MenuHandlerInterceptor;
import kr.co.connectedin.research.global.lib.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${path.upload}")
    private String baseUploadDir;

    private static final String[] TOKEN_EXCLUDE_PATHS = {
            "/user/login" // Login
            , "/error"
            , "/index"
            , "/example/*"
    };

    private static final String[] BUILD_API_INCLUDE_PATHS = {
            "/system/*"
    };

    @Autowired
    private MenuHandlerInterceptor menuHandlerInterceptor;

    @Autowired
    private BuildKeyHandlerInterceptor buildKeyHandlerInterceptor;

    /**
     * Add an encryptor that intercepts the request before it reaches the controller.
     * Add the URL that the interceptor should apply to and the URL that should be excluded.
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> menuHandlerExclude = Arrays.asList(TOKEN_EXCLUDE_PATHS);
        List<String> apiHandlerInclude = Arrays.asList(BUILD_API_INCLUDE_PATHS);
        ArrayList<String> all = new ArrayList<>();
        all.addAll(menuHandlerExclude);
        all.addAll(apiHandlerInclude);

        registry.addInterceptor(menuHandlerInterceptor) // 메뉴 유효성 확인
                .addPathPatterns("/**")
                .excludePathPatterns(all);

        registry.addInterceptor(buildKeyHandlerInterceptor) // 빌드키 유효성 확인 (nextjs에서 사용)
                .addPathPatterns(BUILD_API_INCLUDE_PATHS);
    }

    /**
     * Add resource.
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + Util.removeDuplicatedSlash(baseUploadDir))
                .setCacheControl(CacheControl.maxAge(60, TimeUnit.MINUTES));
    }
}
