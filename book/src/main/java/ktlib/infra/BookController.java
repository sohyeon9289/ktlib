package ktlib.infra;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.azure.storage.blob.sas.*;

import ktlib.domain.BookList;
import ktlib.infra.BookListRepository;
import ktlib.util.BookPdfGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class BookController {
    @Autowired
    private BookListRepository bookListRepository;

    // @GetMapping("/bookLists")
    // public ResponseEntity<?> getAllBooks() {
    //     return ResponseEntity.ok(bookListRepository.findAll());
    // }

    private final AzureBlobUploader uploader;

    // ✅ 생성자에서 환경변수를 안전하게 받아 uploader 초기화
    public BookController(@Value("${AZURE_STORAGE_CONNECTION_STRING}") String connectionString) {
        this.uploader = new AzureBlobUploader(connectionString, "book-pdf");
    }

    @GetMapping("/books/{bookId}/pdf")
    public ResponseEntity<String> generateAndUploadPdf(@PathVariable Long bookId) {
        try {
            BookList book = bookListRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

            byte[] pdfData = BookPdfGenerator.generate(book);
            String sasUrl = uploader.uploadPdf("book-" + bookId + ".pdf", pdfData);

            return ResponseEntity.ok(sasUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("PDF 생성 실패: " + e.getMessage());
        }
    }
}
