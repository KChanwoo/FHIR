/**
 * Response message with data.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.global.response.dataMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralDataMessage {

    private String id = "200";
    private String version = "0.1" ;
    private String result = "success";
    private Object data;

    public GeneralDataMessage() {}

    @Builder
    public GeneralDataMessage(Object data) {
        this.data = data;
    }

}
