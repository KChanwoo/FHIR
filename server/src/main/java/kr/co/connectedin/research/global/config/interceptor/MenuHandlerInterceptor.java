/**
 * Class that implements HandlerInterceptor interface.
 * This contains the logic to be processed before the controller.
 *
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.global.config.interceptor;

import kr.ac.yonsei.maist.domain.menu.dto.Method;
import kr.ac.yonsei.maist.domain.menu.service.MenuService;
import kr.ac.yonsei.maist.domain.user.dto.UserInformationDto;
import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import kr.ac.yonsei.maist.global.error.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

@Component
public class MenuHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private MenuService menuService;

    private JwtTokenProvider jwtTokenProvider;

    @Value("${spring.profiles.active}")
    private String active;

    public MenuHandlerInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private boolean checkUrl(HttpServletRequest request, Object handler, UserInformationDto userInfo) throws Exception {

        //MethodHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            @SuppressWarnings("rawtypes") Class classOfAnnotation = null;
            Method httpMethod = Method.valueOf(request.getMethod());
            String pathUrl = null;
            String[] value = null;
            java.lang.reflect.Method method = handlerMethod.getMethod();
            Annotation[] annotations = method.getDeclaredAnnotations();
            List<Annotation> annotationList = Arrays.asList(annotations);
            switch(httpMethod) {
                case GET:
                    Annotation get = annotationList.stream().filter((o) ->
                        o instanceof GetMapping
                    ).findFirst().orElse(null);
                    value = get == null ? null : ((GetMapping) get).value();
                    break;
                case POST:
                    Annotation post = annotationList.stream().filter((o) ->
                            o instanceof PostMapping
                    ).findFirst().orElse(null);
                    value = post == null ? null : ((PostMapping) post).value();
                    break;
                case PUT:
                    Annotation put = annotationList.stream().filter((o) ->
                            o instanceof PutMapping
                    ).findFirst().orElse(null);
                    value = put == null ? null : ((PutMapping) put).value();
                    break;
                case DELETE:
                    Annotation delete = annotationList.stream().filter((o) ->
                            o instanceof DeleteMapping
                    ).findFirst().orElse(null);
                    value = delete == null ? null : ((DeleteMapping) delete).value();
                    break;
            }

            if (value == null || value.length == 0) {
                return false;
            }

            pathUrl = request.getContextPath() + value[0];

            return menuService.isAllowedToAccess(userInfo == null ? "" : userInfo.getLoginId(), httpMethod, pathUrl);
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return userInfo != null;
        }

        return false;
    }

    /**
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInformationDto userDto = null;
        try {
            userDto = jwtTokenProvider.getUser(request, true);
        } catch (Exception e) {
            // do nothing
        }

        boolean res = this.checkUrl(request, handler, userDto);

        if (!res) {
            if (userDto == null) {
                throw new IllegalAccessException();
            }
            throw new UnauthorizedException();
        }

        return true;
    }
}
