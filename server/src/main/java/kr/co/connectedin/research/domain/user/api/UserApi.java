/**
 * Controller that performs all logic such as inquiry, creation, modification, deletion of user
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.04.27
 */
package kr.co.connectedin.research.domain.user.api;

import kr.co.connectedin.research.domain.menu.service.MenuService;
import kr.co.connectedin.research.domain.user.domain.User;
import kr.ac.yonsei.maist.domain.user.dto.*;
import kr.co.connectedin.research.domain.user.dto.*;
import kr.co.connectedin.research.domain.user.service.UserService;
import kr.co.connectedin.research.global.config.security.JwtTokenProvider;
import kr.co.connectedin.research.global.response.ResponseMessage;
import kr.co.connectedin.research.global.response.dataMessage.GeneralDataMessage;
import kr.co.connectedin.research.global.response.dataMessage.PagingDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApi {

    private final UserService userService;
    private final MenuService menuService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * Login user.
     * @param dto id, password
     * @return ResponseEntity<ResponseDataMessage> token
     */
    @PostMapping("/user/login")
    public ResponseEntity<GeneralDataMessage> login(HttpServletResponse response, @Valid @RequestBody LoginRequestDto dto) throws Exception {
        UserInformationDto user = userService.findByIdAndPassword(dto.getId(), dto.getPassword()); // 유효성 확인

        if ("Y".equals(user.getIsAdminYN())) {
            user.setAdmin(true);
        }

        String token = jwtTokenProvider.createToken(user); // 토큰 발행

        Cookie cookie = new Cookie("token", token);
        cookie.setPath(contextPath);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(JwtTokenProvider.tokenValidSecond);
        cookie.setComment("SameSite=Strict;");

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(LoginResponseDto.builder().name(user.getName()).departmentName(user.getDepartmentName())
                        .positionName(user.getPositionName()).admin(user.isAdmin()).build())
                .build();

        response.addCookie(cookie);
        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<PagingDataMessage> findUser(@Valid FindUserRequestDto dto) throws Exception {
        HashMap<String, Object> searchMap = new HashMap<>();
        searchMap.put("department", dto.getDepartment());
        searchMap.put("level", dto.getLevel());
        searchMap.put("keyword", dto.getKeyword());

        List<UserListResponseDto> userList = null;

        dto.setSearchSpecificationMap(searchMap);
        long totalRecordCount = userService.countUser(dto);
        if(totalRecordCount > 0) {
            dto.setTotalRecordCount(totalRecordCount);
            userList = userService.findUser(dto);
        }

        PagingDataMessage responseMessage = PagingDataMessage.builder()
                .totalPages(dto.getTotalPageCount())
                .totalElements(totalRecordCount)
                .elementsPerPage(dto.getElementsPerPage())
                .data(userList)
                .build();

        return new ResponseEntity<PagingDataMessage>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/auth/user")
    public ResponseEntity<GeneralDataMessage> auth(HttpServletRequest request) throws Exception {
        boolean auth = true;

        try {
            jwtTokenProvider.getUser(request, false);
        } catch (Exception e){
            auth = false;
        }

        GeneralDataMessage response = GeneralDataMessage.builder().data(AuthorizationCheckResponseDto.builder().auth(auth).build()).build();

        return new ResponseEntity<GeneralDataMessage>(response, HttpStatus.OK);
    }

    /**
     * Logout user.
     * @param response HttpServletResponse
     * @return ResponseEntity<ResponseMessage>
     */
    @PostMapping("/user/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletResponse response) throws Exception {

        Cookie cookie = new Cookie("token", null);
        cookie.setPath(contextPath);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setComment("SameSite=Strict");

        response.addCookie(cookie);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Create user by admin
     * @param dto user information
     * @return ResponseEntity<ResponseMessage> success or fail
     */
    @PostMapping("/user")
    public ResponseEntity<ResponseMessage> createUser(@Valid @RequestBody UserCreateRequestDto dto) throws Exception {

        User user = userService.createUser(dto); // user 생성

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update user information.
     * @param loginId user login id
     * @param dto user information
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/user/{loginId}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable String loginId,
                                                      @Valid @RequestBody UserUpdateRequestDto dto) throws Exception {
        userService.updateUser(loginId, dto);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Get user information.
     * @param loginId user id
     * @return ResponseEntity<ResponseDataMessage> user information
     */
    @GetMapping("/user/{loginId}")
    public ResponseEntity<GeneralDataMessage> findUserByUserId(@PathVariable String loginId) throws Exception {
        List<UserInformationDto> user = userService.findUserByLoginId(loginId);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(user.get(0))
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/user/{loginId}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String loginId) throws Exception {
        userService.deleteUserByLoginId(loginId);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Check whether the user ID exists.
     * @param loginId User Id
     * @return ResponseEntity<ResponseDataMessage> true or false
     */
    @GetMapping("/user/id/{loginId}")
    public ResponseEntity<GeneralDataMessage> userExist(@PathVariable String loginId) throws Exception {
        UserExistResponseDto userExist = userService.userExist(loginId);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(userExist)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Reset user password.
     * @param loginId User Id
     * @param dto UserPwdUpdateRequestDto
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/user/password/{loginId}")
    public ResponseEntity<ResponseMessage> updateUserPassword(@PathVariable String loginId, @Valid @RequestBody UserPwdUpdateRequestDto dto) throws Exception {
        userService.update(loginId, dto);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }
}
