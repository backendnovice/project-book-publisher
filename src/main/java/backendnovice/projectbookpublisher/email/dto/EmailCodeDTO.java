/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 코드 관련 데이터 전달 객체 클래스.
 * @changelog :
 */

package backendnovice.projectbookpublisher.email.dto;

import backendnovice.projectbookpublisher.email.vo.CodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Email DTO")
public class EmailCodeDTO {
    @Schema(name = "코드 ID")
    private Long id;

    @Schema(name = "코드 키")
    private String key;

    @Schema(name = "코드 종류")
    private CodeType type;
}
