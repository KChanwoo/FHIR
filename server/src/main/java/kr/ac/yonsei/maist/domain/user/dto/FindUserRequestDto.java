package kr.ac.yonsei.maist.domain.user.dto;

import kr.ac.yonsei.maist.global.dto.PagingDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindUserRequestDto extends PagingDto {
    private String department;
    private String level;
    private String keyword;
}
