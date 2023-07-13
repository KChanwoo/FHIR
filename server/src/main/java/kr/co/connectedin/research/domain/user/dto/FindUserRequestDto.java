package kr.co.connectedin.research.domain.user.dto;

import kr.co.connectedin.research.global.dto.PagingDto;
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
