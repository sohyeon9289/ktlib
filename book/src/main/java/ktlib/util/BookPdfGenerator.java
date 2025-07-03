package ktlib.util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import ktlib.domain.BookList;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public class BookPdfGenerator {

    public static byte[] generate(BookList book) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        document.setMargins(30, 30, 30, 30);

        try {
            // ✅ 커버 이미지
            if (book.getCoverUrl() != null && !book.getCoverUrl().isEmpty()) {
                Image cover = new Image(ImageDataFactory.create(new URL(book.getCoverUrl())));
                cover.setAutoScale(true);
                cover.setMarginBottom(20);
                document.add(cover);
            }

            // ✅ 제목
            document.add(new Paragraph(safe(book.getTitle()))
                .setFontSize(20)
                .setBold()
                .setMarginBottom(10));

            // ✅ 작가
            document.add(new Paragraph("by " + safe(book.getAuthorNickname()))
                .setFontSize(12)
                .setItalic()
                .setMarginBottom(20));

            // ✅ 요약
            document.add(new LineSeparator(new SolidLine(0.5f)));
            document.add(new Paragraph("📚 Genre: " + safe(book.getGenre())).setFontSize(12));
            document.add(new Paragraph("📝 Summary:\n" + safe(book.getSummary()))
                .setFontSize(12)
                .setMarginBottom(20));

            // ✅ 본문
            document.add(new LineSeparator(new SolidLine(0.5f)));
            document.add(new Paragraph("📖 Content:").setFontSize(14).setBold().setMarginTop(10));
            document.add(new Paragraph(safe(book.getContent()))
                .setFontSize(12));

            document.close();
            byte[] pdfData = baos.toByteArray();
            System.out.println("📄 PDF 바이트 길이: " + pdfData.length);
            return pdfData;
        } catch (Exception e) {
            System.err.println("❌ PDF 생성 실패: " + e.getMessage());
            throw e;
        }
    }

    private static String safe(String input) {
        return input != null ? input : "(N/A)";
    }
}
