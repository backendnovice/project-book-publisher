/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-11
 * @desc : Common response data transfer object.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(name = "Common response DTO")
public class ResponseDTO<T> {
    @Schema(description = "HTTP status code.")
    private int code;

    @Schema(description = "HTTP message.")
    private String message;

    @Schema(description = "Response generic data.")
    private T data;

    /**
     * Respond code(200), message, data together when successful.
     * @param message
     *     Response message
     * @param data
     *     Response data
     * @return
     *     Response DTO
     * @param <T>
     *     Generic type
     */
    public static <T> ResponseDTO<T> onSuccess(String message, T data) {
        return new ResponseDTO<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * Respond code(404), message together when unsuccessful.
     * @param message
     *     Response message
     * @return
     *     Response DTO
     * @param <T>
     *     Generic type
     */
    public static <T> ResponseDTO<T> onFailure(String message) {
        return new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), message, null);
    }
}
