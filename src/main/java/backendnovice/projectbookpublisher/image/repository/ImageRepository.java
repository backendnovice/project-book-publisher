/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Provides queries to perform I/O for 'image' table.
 *
 * changelog :
 **/

package backendnovice.projectbookpublisher.image.repository;

import backendnovice.projectbookpublisher.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
