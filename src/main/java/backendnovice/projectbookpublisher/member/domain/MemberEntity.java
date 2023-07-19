/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Member 테이블 엔티티 클래스.
 * @changelog :
 * 23-06-29 - backendnovice@gmail.com - @Setter 어노테이션 제거
 * 23-07-04 - backendnovice@gmail.com - Role을 임시로 USER로 고정
 * 23-07-09 - backendnovice@gmail.com - enabled 필드 추가
 * 23-07-10 - backendnovice@gmail.com - EmailCode 관계 필드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.email.domain.EmailCodeEntity;
import backendnovice.projectbookpublisher.common.domain.TimeEntity;
import backendnovice.projectbookpublisher.member.vo.RoleType;
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
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private RoleType roles = RoleType.USER;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean enabled = false;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<EmailCodeEntity> emailCodeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<BookEntity> bookEntities = new ArrayList<>();

    @Builder
    public MemberEntity(String email, String password, String phone, boolean enabled) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
    }

    /**
     * BookEntity의 email을 파라미터값으로 대체한다.
     * @param email
     *      회원 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * BookEntity의 password을 파라미터값으로 대체한다.
     * @param password
     *      회원 비밀번호
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * BookEntity의 phone을 파라미터값으로 대체한다.
     * @param phone
     *      회원 전화번호
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * BookEntity의 enabled를 파라미터값으로 대체한다.
     * @param enabled
     *      회원 인증여부
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
