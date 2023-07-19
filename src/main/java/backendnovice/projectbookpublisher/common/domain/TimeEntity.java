/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 테이블 공통 엔티티 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 클래스명 변경 (Time -> TimeEntity)
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeEntity {
    @CreatedDate
    @Column(name = "date_create", updatable = false)
    private LocalDateTime dateCreate;

    @LastModifiedDate
    @Column(name = "date_modify")
    private LocalDateTime dateModify;
}
