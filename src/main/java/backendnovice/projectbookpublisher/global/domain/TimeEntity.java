/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Automatically injects create, modify date.
 *
 * changelog :
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 */

package backendnovice.projectbookpublisher.global.domain;

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
