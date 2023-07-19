/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 이미지와 관련된 서비스 메소드를 구현하는 클래스.
 * @changelog :
 * 23-07-15 - backendnovice@gmail.com - 이미지 저장 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 이미지명 반환 메소드 구현
 * 23-07-19 - backendnovice@gmail.com - 엔티티 <-> DTO 변환 메소드 추가
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
 */

package backendnovice.projectbookpublisher.image.service;

import backendnovice.projectbookpublisher.image.domain.ImageEntity;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;
import backendnovice.projectbookpublisher.image.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Value("${image.upload.directory}")
    private String filepath;

    @Override
    public ImageEntity saveImageToFile(MultipartFile image) {
        try {
            String uuid = UUID.randomUUID().toString();
            String ext = ".png";

            File file = new File(filepath + uuid + ext);
            image.transferTo(file);

            return saveImageToDB(uuid, ext);
        }catch(IOException e) {
            throw new RuntimeException("Failed to upload image.");
        }
    }

    @Override
    public String getImageFullName(Long id) {
        Optional<ImageEntity> image = imageRepository.findById(id);

        if(image.isPresent()) {
            ImageEntity _imageEntity = image.get();
            return _imageEntity.getUuid() + _imageEntity.getExt();
        }else {
            throw new IllegalArgumentException("There is no ID in Image table.");
        }
    }

    /**
     * 이미지 정보를 DB에 저장한다.
     * @param uuid
     *      이미지 UUID
     * @param ext
     *      이미지 확장자
     */
    private ImageEntity saveImageToDB(String uuid, String ext) {
        ImageEntity imageEntity = ImageEntity.builder()
                .uuid(uuid)
                .ext(ext)
                .path(filepath)
                .build();

        return imageRepository.save(imageEntity);
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
     * @param imageEntity
     *      ImageEntity
     * @return
     *      ImageDTO
     */
    private ImageDTO convertToDto(ImageEntity imageEntity) {
        return ImageDTO.builder()
                .uuid(imageEntity.getUuid())
                .ext(imageEntity.getExt())
                .path(imageEntity.getPath())
                .build();
    }
}
