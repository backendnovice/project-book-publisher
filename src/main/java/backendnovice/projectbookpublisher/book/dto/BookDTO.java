/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 책 관련 데이터 전달 객체 클래스.
 * @changelog :
 * 23-07-16 - backendnovice@gmail.com - ImageDTO 필드 제거
 * 23-07-19 - backendnovice@gmail.com - filename 필드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
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
    @Schema(description = "책 ID (PK)")
    private Long id;

    @Schema(description = "책 제목")
    private String title;

    @Schema(description = "책 저자")
    private String author;

    @Schema(description = "책 설명")
    private String description;

    @Schema(description = "책 작성일자")
    private LocalDate datePublish;

    @Schema(description = "책 종류")
    private BookType type;
    
    @Schema(description = "이미지 파일명")
    private String filename;
}
