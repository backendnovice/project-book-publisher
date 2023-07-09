/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-09
 * @desc : DB의 코드 테이블에 대응하는 객체.
 *
 * 변경 내역 :
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
    private Long id;

    @Column(name = "code_value", length = 64)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_type")
    private CodeType type;

    @ManyToOne
    @JoinColumn(name = "member")
    private MemberEntity member;

    @Builder
    public CodeEntity(String value, CodeType type, MemberEntity member) {
        this.value = value;
        this.type = type;
        this.member = member;
    }
}
