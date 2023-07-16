/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-16
 * @desc : Defines book-related feature methods.
 *
 * changelog :
 * 2023-07-16 - backendnovice@gmail.com - Define book select feature
 */

package backendnovice.projectbookpublisher.book.service;

import backendnovice.projectbookpublisher.book.domain.Book;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.image.domain.Image;

public interface BookService {
    /**
     * Process book registration.
     * @param bookDTO
     *      BookDTO
     * @param image
     *      Image entity
     * @param email
     *      Member email
     */
    public void register(BookDTO bookDTO, Image image, String email);

    /**
     * Select book by id.
     * @param id
     *      Book id
     */
    public Book select(Long id);

    /**
     * Convert BookDTO to Book entity.
     * @param bookDTO
     *      BookDTO
     * @return
     *      Book entity
     */
    default Book dtoToEntity(BookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .content(bookDTO.getContent())
                .datePublish(bookDTO.getDatePublish())
                .type(bookDTO.getType())
                .build();
    }

    /**
     * Convert Book entity to BookDTO.
     * @param book
     *      Book entity
     * @return
     *      BookDTO
     */
    default BookDTO entityToDto(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .content(book.getContent())
                .datePublish(book.getDatePublish())
                .type(book.getType())
                .build();
    }
}
