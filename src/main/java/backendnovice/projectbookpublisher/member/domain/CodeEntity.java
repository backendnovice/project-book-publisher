/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-10
 * @desc : DB의 코드 테이블에 대응하는 객체.
 *
 * 변경 내역 :
 * 2023-07-10 - backendnovice@gmail.com - 코드 만료 여부 추가
 */

package backendnovice.projectbookpublisher.member.domain;

import backendnovice.projectbookpublisher.global.domain.TimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "code")
@Getter
@ToString
@NoArgsConstructor
@Entity
public class CodeEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    @Column(name = "code_value", length = 64)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type")
    private CodeType type;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "is_valid", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isValid = true;

    @Builder
    public CodeEntity(String value, CodeType type, MemberEntity member, boolean isValid) {
        this.value = value;
        this.type = type;
        this.member = member;
        this.isValid = isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
