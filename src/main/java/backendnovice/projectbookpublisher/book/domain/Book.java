/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Match 'book' table in DB.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.book.domain;

import backendnovice.projectbookpublisher.book.vo.BookType;
import backendnovice.projectbookpublisher.common.domain.Times;
import backendnovice.projectbookpublisher.image.domain.Image;
import backendnovice.projectbookpublisher.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Book extends Times {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_author")
    private String author;

    @Column(name = "book_content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_type")
    private BookType type;

    @Column(name = "date_publish")
    private LocalDate datePublish;

    @Column(name = "is_permitted", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean permitted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Book(String title, String author, String content, BookType type, LocalDate datePublish
            , boolean permitted, Image image, Member member) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.type = type;
        this.datePublish = datePublish;
        this.permitted = permitted;
        this.image = image;
        this.member = member;
    }

    /**
     * Set Image with parameter.
     * @param image
     *      Image entity
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Set permitted by admin role user.
     * @param permitted
     *      permitted by admin
     */
    public void setPermitted(boolean permitted) {
        this.permitted = permitted;
    }
}
