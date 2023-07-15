/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Match 'image' table in DB.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.image.domain;

import backendnovice.projectbookpublisher.book.domain.Book;
import backendnovice.projectbookpublisher.common.domain.Times;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "image")
@Entity
@Getter
@NoArgsConstructor
public class Image extends Times {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_uuid", nullable = false)
    private String uuid;

    @Column(name = "image_extension", nullable = false)
    private String extenstion;

    @Column(name = "image_path", nullable = false)
    private String path;

    @OneToOne(mappedBy = "image")
    private Book book;

    @Builder
    public Image(String uuid, String extenstion, String path) {
        this.uuid = uuid;
        this.extenstion = extenstion;
        this.path = path;
    }
}
