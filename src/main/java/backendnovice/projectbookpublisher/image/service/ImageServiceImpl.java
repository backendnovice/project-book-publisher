/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-15
 * @desc : Implements image-related feature methods.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.image.service;

import backendnovice.projectbookpublisher.image.domain.Image;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;
import backendnovice.projectbookpublisher.image.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(ImageDTO imageDTO) {
        return imageRepository.save(dtoToEntity(imageDTO));
    }
}
