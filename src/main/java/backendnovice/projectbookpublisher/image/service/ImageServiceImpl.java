/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-21
 * @desc      : 이미지와 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-15 - backendnovice@gmail.com - 이미지 저장 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 이미지명 반환 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 엔티티 <-> DTO 변환 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 * 23-07-21 - backendnovice@gmail.com - 예외 처리 및 변환 메소드 수정
 */

package backendnovice.projectbookpublisher.image.service;

import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;
import backendnovice.projectbookpublisher.image.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Value("${image.upload.directory}")
    private String path;

    @Override
    @Transactional
    public ImageEntity saveImageToFile(MultipartFile image) {
        String uuid = UUID.randomUUID().toString();
        String ext = ".png";

        ImageDTO imageDTO = ImageDTO.builder()
                .uuid(uuid)
                .ext(ext)
                .path(path)
                .build();

        try {
            File file = new File(path + uuid + ext);

            image.transferTo(file);

            return saveImageToDB(imageDTO);
        }catch(IOException e) {
            log.error("이미지 업로드에 실패했습니다: {}", imageDTO);

            return null;
        }
    }

    @Override
    public String getFullNameById(ImageDTO imageDTO) {
        try {
            ImageEntity image = imageRepository.findById(imageDTO.getId())
                    .orElseThrow(NoSuchElementException::new);

            return image.getUuid() + image.getExt();
        }catch (NoSuchElementException e) {
            log.error("ID와 일치하는 이미지가 존재하지 않습니다 : {}", imageDTO.getId());

            return null;
        }
    }

    /**
     * 이미지 정보를 DB에 저장한다.
     * @param image
     *      ImageDTO
     */
    private ImageEntity saveImageToDB(ImageDTO image) {
        return imageRepository.save(convertToEntity(image));
    }

    /**
     * ImageDTO를 ImageEntity로 변환한다.
     * @param imageDTO
     *      ImageDTO
     * @return
     *      ImageEntity
     */
    private ImageEntity convertToEntity(ImageDTO imageDTO) {
        return ImageEntity.builder()
                .uuid(imageDTO.getUuid())
                .ext(imageDTO.getExt())
                .path(imageDTO.getPath())
                .build();
    }

    /**
     * ImageEntity를 ImageDTO로 변환한다.
     * @param image
     *      ImageEntity
     * @return
     *      ImageDTO
     */
    private ImageDTO convertToDto(ImageEntity image) {
        return ImageDTO.builder()
                .id(image.getId())
                .uuid(image.getUuid())
                .ext(image.getExt())
                .path(image.getPath())
                .build();
    }
}
