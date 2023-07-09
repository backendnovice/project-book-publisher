/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : DB의 회원 테이블에 대응하는 객체.
 *
 * 변경 내역 :
 * 2023-06-29 - backendnovice@gmail.com - id 무결성 위해 코드 수정
 * 2023-06-30 - backendnovice@gmail.com - 코드화 주석 변경 내역 추가
 * 2023-07-04 - backendnovice@gmail.com - Role 값 USER로 임시로 고정
 * 2023-07-09 - backendnovice@gmail.com - 활성화 여부 추가 & 코드 엔티티 매핑
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.global.domain.TimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole roles = MemberRole.USER;

    @Column(name = "is_verified", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isEnabled = false;

    @OneToMany(mappedBy = "codes")
    private List<CodeEntity> codes = new ArrayList<>();

    @Builder
    public MemberEntity(String email, String password, String phone, boolean isEnabled) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isEnabled = isEnabled;
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

    /**
     * 이메일 검증여부를 수정하는 메소드.
     * @param isVerified
     *      이메일 검증여부
     */
    public void setIsEnabled(boolean isVerified) {
        this.isEnabled = isVerified;
    }
}
