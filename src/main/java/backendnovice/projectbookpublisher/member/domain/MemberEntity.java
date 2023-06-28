/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-28
 * @desc : DB의 회원 테이블에 대응하는 객체.
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.global.domain.TimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name = "member")
public class MemberEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_pw", nullable = false)
    private String password;

    @Column(name = "member_tel")
    private String phone;
}
