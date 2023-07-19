/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 회원 관련 데이터 전달 객체 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - Springdoc swagger 주석 적용
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "Member DTO")
public class MemberDTO {
    @Schema(description = "회원 ID (PK)")
    private Long id;

    @Schema(description = "회원 이메일")
    private String email;

    @Schema(description = "회원 비밀번호")
    private String password;

    @Schema(description = "회원 전화번호")
    private String phone;
}
