/**
 * Error response message.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseMessage {

    private String id;
    private String version = "0.1" ;
    private String result = "fail";
    private int errorCode;
    private Object message;

    @Builder
    public ErrorResponseMessage(String id, int errorCode, Object message) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
    }


}
