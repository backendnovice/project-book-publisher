/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-16
 * @desc : Maps book-related pages and processes requests.
 *
 * changelog :
 * 2023-07-16 - backendnovice@gmail.com - Add book read method
 */

package backendnovice.projectbookpublisher.book.controller;

import backendnovice.projectbookpublisher.book.domain.Book;
import backendnovice.projectbookpublisher.book.dto.BookDTO;
import backendnovice.projectbookpublisher.book.service.BookService;
import backendnovice.projectbookpublisher.image.domain.Image;
import backendnovice.projectbookpublisher.image.dto.ImageDTO;
import backendnovice.projectbookpublisher.image.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookViewController {
    private final BookService bookService;
    private final ImageService imageService;

    public BookViewController(BookService bookService, ImageService imageService) {
        this.bookService = bookService;
        this.imageService = imageService;
    }

    @Value("${image.upload.directory}")
    private String directory;

    /**
     * Mapping book registration page.
     * @return
     *      Book registration URI
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "books/register";
    }

    /**
     * Mapping book list page.
     * @return
     *      Book list URI
     */
    @GetMapping("/list")
    public String getListPage() {
        return "books/list";
    }

    /**
     * Mapping book read page with id.
     * @param id
     *      Book id
     * @param model
     *      Model object
     * @return
     *      Book read URI
     */
    @GetMapping("/read/{id}")
    public String getReadPage(@PathVariable Long id, Model model) {
        Book book = bookService.select(id);
        String filename = loadImageFile(book);
        BookDTO bookDTO = bookService.entityToDto(book);
        bookDTO.setContent(bookDTO.getContent().replaceAll("<br>", "\r\n"));
        model.addAttribute("book", bookDTO);
        model.addAttribute("image", filename);

        return "books/read";
    }

    /**
     * Handle book register service.
     * @param bookDTO
     *      BookDTO
     * @param imageFile
     *      Book image file
     * @param principal
     *      Logged in member
     * @return
     *      Book list URI
     */
    @PostMapping("/register")
    public String registerProcess(BookDTO bookDTO, @RequestParam("image") MultipartFile imageFile, Principal principal) {
        ImageDTO imageDTO = ImageDTO.builder()
                .uuid(saveImageFile(imageFile))
                .extension(imageFile.getContentType())
                .path(directory)
                .build();

        Image image = imageService.saveImage(imageDTO);
        bookService.register(bookDTO, image, principal.getName());

        return "books/list";
    }

    /**
     * Save image file to the path.
     * @param image
     *      Image file
     * @return
     *      generated uuid
     */
    private String saveImageFile(MultipartFile image) {
        try {
            String uuid = UUID.randomUUID().toString();
            String path = directory + uuid + ".png";
            File file = new File(path);
            image.transferTo(file);
            return uuid;
        }catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image.");
        }
    }

    /**
     * Load image inform by book entity.
     * @param book
     *      Book entity
     * @return
     *      File name
     */
    private String loadImageFile(Book book) {
        Image image = book.getImage();

        return image.getUuid() + image.getExtenstion().replace("image/", ".");
    }
}
