package kr.ac.yonsei.maist.domain.menu.api;

import kr.ac.yonsei.maist.domain.menu.dto.MenuAccessResponseDto;
import kr.ac.yonsei.maist.domain.menu.dto.Method;
import kr.ac.yonsei.maist.domain.menu.service.MenuService;
import kr.ac.yonsei.maist.domain.user.dto.UserInformationDto;
import kr.ac.yonsei.maist.global.config.security.JwtTokenProvider;
import kr.ac.yonsei.maist.global.response.dataMessage.GeneralDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MenuApi {
    private final MenuService menuService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/menu/access")
    public ResponseEntity<GeneralDataMessage> access(HttpServletRequest request, @RequestParam(name = "url") String url) throws Exception {

        UserInformationDto userDto = jwtTokenProvider.getUser(request, false);

        boolean isAccess = menuService.isAllowedToAccess(userDto.getLoginId(), Method.PAGE, url);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(MenuAccessResponseDto.builder().access(isAccess).build())
                .build();
        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }
}
