/**
 * Controller that performs all logic such as inquiry, creation, modification, deletion of NX machine
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.co.connectedin.research.domain.log.api;

import kr.co.connectedin.research.domain.log.service.LogService;
import kr.co.connectedin.research.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogApi {

    private final LogService logService;
    private final UserService userService;
}
