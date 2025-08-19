package org.example.capstone3.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
//Mohammed
@Service
public class OpenHtmlToPdfGenerator implements PdfGenerator{
    @Override
    public byte[] fromHtml(String html) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder b = new PdfRendererBuilder();
            b.toStream(out);
            b.withHtmlContent(html, null);
            b.run();
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("PDF generation failed", e);
        }
    }
}
