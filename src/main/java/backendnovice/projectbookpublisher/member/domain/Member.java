/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Match 'member' table in DB.
 *
 * changelog :
 * 2023-06-29 - backendnovice@gmail.com - Remove @Setter annotation
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 * 2023-07-04 - backendnovice@gmail.com - Temporarily fixed role with 'USER'
 * 2023-07-09 - backendnovice@gmail.com - Add enabled column
 * 2023-07-10 - backendnovice@gmail.com - Map EmailCode relationship
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.email.domain.EmailCode;
import backendnovice.projectbookpublisher.global.domain.TimeEntity;
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
public class Member extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String email;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private RoleType roles = RoleType.USER;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean enabled = false;

    @OneToMany(mappedBy = "member")
    private List<EmailCode> emailCodes = new ArrayList<>();

    @Builder
    public Member(String email, String password, String phone, boolean enabled) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.enabled = enabled;
    }

    /**
     * Set email with parameter.
     * @param email
     *      email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set password with parameter.
     * @param password
     *      password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set phone with parameter.
     * @param phone
     *      phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Set email verification with parameter.
     * @param enabled
     *      email verification
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
