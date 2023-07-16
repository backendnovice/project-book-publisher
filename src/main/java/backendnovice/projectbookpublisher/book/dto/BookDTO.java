/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Book data transfer object.
 *
 * changelog :
 * 2023-07-16 - backendnovice@gmail.com - Remove image dto
 */

package backendnovice.projectbookpublisher.book.dto;

import backendnovice.projectbookpublisher.book.vo.BookType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Book DTO")
public class BookDTO {
    @Schema(description = "Book ID. (PK)")
    private Long id;

    @Schema(description = "Book title.")
    private String title;

    @Schema(description = "Book author.")
    private String author;

    @Schema(description = "Book content.")
    private String content;

    @Schema(description = "Book publish date.")
    private LocalDate datePublish;

    @Schema(description = "Book type.")
    private BookType type;
}
