/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : 회원 데이터 전송 객체.
 *
 * 변경 내역 :
 * 2023-06-30 - backendnovice@gmail.com - Springdoc Swagger 적용
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
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
public class MemberDTO {
    @Schema(description = "회원 PK")
    private Long id;

    @Schema(description = "회원 이메일")
    private String email;

    @Schema(description = "회원 비밀번호")
    private String password;

    @Schema(description = "회원 전화번호")
    private String phone;
}
