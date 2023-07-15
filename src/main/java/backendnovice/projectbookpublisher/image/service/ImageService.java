/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Defines image-related feature methods.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.image.service;

import backendnovice.projectbookpublisher.image.domain.Image;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;

public interface ImageService {
    /**
     * Save image data to DB.
     * @param imageDTO
     *      ImageDTO
     * @return
     *      Image entity
     */
    Image saveImage(ImageDTO imageDTO);

    /**
     * Convert imageDTO to image entity.
     * @param imageDTO
     *      Image DTO
     * @return
     *      Image entity
     */
    default Image dtoToEntity(ImageDTO imageDTO) {
        return Image.builder()
                .uuid(imageDTO.getUuid())
                .extenstion(imageDTO.getExtension())
                .path(imageDTO.getPath())
                .build();
    }

    /**
     * Convert image entity to image DTO.
     * @param image
     *      Image entity
     * @return
     *      Image DTO
     */
    default ImageDTO entityToDto(Image image) {
        return ImageDTO.builder()
                .uuid(image.getUuid())
                .extension(image.getExtenstion())
                .path(image.getPath())
                .build();
    }
}
