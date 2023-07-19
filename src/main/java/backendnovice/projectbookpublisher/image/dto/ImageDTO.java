/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 이미지 관련 데이터 전달 객체 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Image DTO")
public class ImageDTO {
    @Schema(description = "이미지 ID (PK)")
    private Long id;

    @Schema(description = "이미지 UUID")
    private String uuid;

    @Schema(description = "이미지 확장자")
    private String ext;

    @Schema(description = "이미지 경로")
    private String path;
}
