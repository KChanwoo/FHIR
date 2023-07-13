package kr.co.connectedin.research.domain.menu.api;

import kr.co.connectedin.research.domain.menu.dto.MenuAccessResponseDto;
import kr.co.connectedin.research.domain.menu.dto.Method;
import kr.co.connectedin.research.domain.menu.service.MenuService;
import kr.co.connectedin.research.domain.user.dto.UserInformationDto;
import kr.co.connectedin.research.global.config.security.JwtTokenProvider;
import kr.co.connectedin.research.global.response.dataMessage.GeneralDataMessage;
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
