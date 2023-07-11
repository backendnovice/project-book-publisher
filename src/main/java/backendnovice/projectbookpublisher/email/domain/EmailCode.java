/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : Match 'email_code' table in DB.
 *
 * changelog :
 * 2023-07-10 - backendnovice@gmail.com - Add expired column
 */

package backendnovice.projectbookpublisher.email.domain;

import backendnovice.projectbookpublisher.global.domain.TimeEntity;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "email_code")
@Getter
@ToString
@NoArgsConstructor
@Entity
public class EmailCode extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    @Column(name = "code_key", length = 64)
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type")
    private CodeType type;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_expired", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean expired = false;

    @Builder
    public EmailCode(String key, CodeType type, Member member, boolean expired) {
        this.key = key;
        this.type = type;
        this.member = member;
        this.expired = expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
