/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 공통 응답 데이터 전달 객체 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(name = "Common response DTO")
public class ResponseDTO<T> {
    @Schema(description = "HTTP 상태 코드")
    private int code;

    @Schema(description = "HTTP 응답 메시지")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    /**
     * 데이터를 포함하는 API 의 정상적인 응답을 생성하고 반환한다.
     * @param message
     *     응답 메시지
     * @param data
     *     응답 데이터
     * @return
     *     ResponseDTO
     * @param <T>
     *     제네릭 타입
     */
    public static <T> ResponseDTO<T> onSuccess(String message, T data) {
        return new ResponseDTO<>(HttpStatus.OK.value(), message, data);
    }

    /**
     * 데이터를 포함하지 않는 API 의 비정상적인 응답을 생성하고 반환한다.
     * @param message
     *     응답 메시지
     * @return
     *     ResponseDTO
     * @param <T>
     *     제네릭 타입
     */
    public static <T> ResponseDTO<T> onFailure(String message) {
        return new ResponseDTO<>(HttpStatus.NOT_FOUND.value(), message, null);
    }
}
