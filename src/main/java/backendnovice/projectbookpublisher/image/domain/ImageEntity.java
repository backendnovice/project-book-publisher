/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Image 테이블 엔티티 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 클래스명 변경 (Image -> ImageEntity), 필드명 변경 (extension -> ext)
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.image.domain;

import backendnovice.projectbookpublisher.book.domain.BookEntity;
import backendnovice.projectbookpublisher.common.domain.TimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "image")
@Entity
@Getter
@NoArgsConstructor
public class ImageEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_uuid", nullable = false)
    private String uuid;

    @Column(name = "image_extension", nullable = false)
    private String ext;

    @Column(name = "image_path", nullable = false)
    private String path;

    @OneToOne(mappedBy = "image")
    private BookEntity bookEntity;

    @Builder
    public ImageEntity(String uuid, String ext, String path) {
        this.uuid = uuid;
        this.ext = ext.replace("image/", ".");
        this.path = path;
    }
}
