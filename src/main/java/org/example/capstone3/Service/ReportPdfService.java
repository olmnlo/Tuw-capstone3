// src/main/java/org/example/capstone3/Service/ReportPdfService.java
package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Model.Report;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
@RequiredArgsConstructor
public class ReportPdfService {

    private final PdfGenerator pdfGenerator;

    public byte[] generate(Report r) {
        String desc = HtmlUtils.htmlEscape(
                (r.getDescription() != null && !r.getDescription().isBlank())
                        ? r.getDescription() : "No description."
        );

        String html = """
                <!doctype html>
                <html lang="en">
                <head>
                  <meta charset="utf-8">
                  <style>
                    body{font-family: Arial, Helvetica, sans-serif; font-size:12pt; margin:32px;}
                    h2{margin:0 0 12px 0}
                    .desc{white-space:pre-wrap; line-height:1.5}
                    hr{border:0;border-top:1px solid #ccc;margin:12px 0}
                  </style>
                </head>
                <body>
                  <h2>Report #%s</h2>
                  <hr/>
                  <strong>Description</strong>
                  <div class="desc">%s</div>
                </body>
                </html>
                """.formatted(r.getId(), desc);

        return pdfGenerator.fromHtml(html);
    }
}
