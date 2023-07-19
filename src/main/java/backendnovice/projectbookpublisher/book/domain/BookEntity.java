/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Book 테이블 엔티티 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 클래스명 변경 (Book -> BookEntity), 필드명 변경 (content -> description)
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.book.domain;

import backendnovice.projectbookpublisher.book.vo.BookType;
import backendnovice.projectbookpublisher.common.domain.TimeEntity;
import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import backendnovice.projectbookpublisher.member.domain.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class BookEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_author")
    private String author;

    @Column(name = "book_content")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_type")
    private BookType type;

    @Column(name = "date_publish")
    private LocalDate datePublish;

    @Column(name = "is_permitted", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean permitted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageEntity imageEntity;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Builder
    public BookEntity(String title, String author, String description, BookType type, LocalDate datePublish
            , boolean permitted, ImageEntity imageEntity, MemberEntity memberEntity) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.type = type;
        this.datePublish = datePublish;
        this.permitted = permitted;
        this.imageEntity = imageEntity;
        this.memberEntity = memberEntity;
    }

    /**
     * BookEntity의 ImageEntity를 파라미터로 설정한다.
     * @param imageEntity
     *      ImageEntity
     */
    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    /**
     * BookEntity의 MemberEntity를 파라미터로 설정한다.
     * @param memberEntity
     *      MemberEntity
     */
    public void setMemberEntity(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    /**
     * BookEntity의 게시 허용 여부를 파라미터로 설정한다.
     * @param permitted
     *      permitted
     */
    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }
}
