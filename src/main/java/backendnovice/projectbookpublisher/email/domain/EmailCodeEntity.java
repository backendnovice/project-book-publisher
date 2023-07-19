/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : EmailCode 테이블 엔티티 클래스.
 * @changelog :
 * 23-07-10 - backendnovice@gmail.com - 만료여부 필드 추가
 * 23-07-19 - backendnovice@gmail.com - 클래스명 변경 (EmailCode -> EmailCodeEntity)
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 및 수정
 */

package backendnovice.projectbookpublisher.email.domain;

import backendnovice.projectbookpublisher.common.domain.TimeEntity;
import backendnovice.projectbookpublisher.email.vo.CodeType;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
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
public class EmailCodeEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    @Column(name = "code_key", length = 64)
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type")
    private CodeType type;

    @Column(name = "is_expired", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean expired = false;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Builder
    public EmailCodeEntity(String key, CodeType type, MemberEntity memberEntity, boolean expired) {
        this.key = key;
        this.type = type;
        this.memberEntity = memberEntity;
        this.expired = expired;
    }

    /**
     * EmailCodeEntity 의 인증코드 만료여부를 파라미터로 설정한다.
     * @param expired
     *      인증코드 만료여부
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
