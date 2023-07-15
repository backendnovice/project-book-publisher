/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Image data transfer object.
 *
 * changelog :
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
    @Schema(description = "Image ID. (PK)")
    private Long id;

    @Schema(description = "Image file uuid.")
    private String uuid;

    @Schema(description = "Image file extension.")
    private String extension;

    @Schema(description = "Image file path.")
    private String path;
}
