/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 이미지와 관련된 서비스 메소드를 정의하는 클래스.
 * @changelog :
 * 23-07-15 - backendnovice@gmail.com - 이미지 저장 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 이미지명 반환 메소드 정의
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.image.service;

import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * 이미지를 로컬 저장소에 저장한다.
     * @param image
     *      이미지 파일 객체
     * @return
     *      ImageEntity
     */
    public ImageEntity saveImageToFile(MultipartFile image);

    /**
     * 확장자를 포함한 이미지명을 반환한다.
     * @param id
     *      이미지 ID
     * @return
     *      이미지명
     */
    String getImageFullName(Long id);
}
