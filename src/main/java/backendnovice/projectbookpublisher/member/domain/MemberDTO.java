/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-28
 * @desc : 회원 데이터 전송 객체.
 */

package backendnovice.projectbookpublisher.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
    private Long id;

    private String email;

    private String password;

    private String phone;
}
