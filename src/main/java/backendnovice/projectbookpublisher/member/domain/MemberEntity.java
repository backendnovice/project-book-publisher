/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-29
 * @desc : DB의 회원 테이블에 대응하는 객체.
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.global.domain.TimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "member")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public MemberEntity(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    /**
     * 이메일을 수정하는 메소드.
     * @param email
     *      이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 비밀번호를 수정하는 메소드.
     * @param password
     *      비밀번호
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 전화번호를 수정하는 메소드.
     * @param phone
     *      전화번호
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
