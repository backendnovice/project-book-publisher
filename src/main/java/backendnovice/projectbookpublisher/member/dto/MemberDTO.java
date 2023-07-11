/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Member data transfer object.
 *
 * changelog :
 * 2023-06-30 - backendnovice@gmail.com - Apply springdoc swagger annotations
 * 2023-06-30 - backendnovice@gmail.com - Modify coding annotations
 */

package backendnovice.projectbookpublisher.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "Member DTO")
public class MemberDTO {
    @Schema(description = "Member ID. (PK)")
    private Long id;

    @Schema(description = "Member email address.")
    private String email;

    @Schema(description = "Member password.")
    private String password;

    @Schema(description = "Member phone number.")
    private String phone;
}
