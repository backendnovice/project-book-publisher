/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 페이지 관련 데이터 전달 객체 클래스.
 * @changelog :
 */

package backendnovice.projectbookpublisher.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Pagination DTO")
public class PaginationDTO {
    @Schema(description = "현재 페이지")
    private int current;

    @Schema(description = "시작 페이지")
    private int start;

    @Schema(description = "끝 페이지")
    private int end;

    @Schema(description = "검색 태그")
    private String tag;

    public PaginationDTO(Page<?> pages) {
        this.current = pages.getPageable().getPageNumber();
        this.start = Math.max(current - 4, 1);
        this.end = Math.min(current + 5, pages.getTotalPages());
    }
}
