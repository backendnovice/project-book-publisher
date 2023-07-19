/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Image 테이블 입출력을 담당하는 레포지토리 인터페이스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 **/

package backendnovice.projectbookpublisher.image.repository;

import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
