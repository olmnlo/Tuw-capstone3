package org.example.capstone3.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//Mohammed

@Service
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;

    public void sendHospitalInstructionEmail(String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Doctor’s Instructions");

        String htmlContent = """
            <html>
              <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f4f6f9;">
                <div style="max-width:600px; margin:30px auto; background:#ffffff; border-radius:12px; box-shadow:0 4px 12px rgba(0,0,0,0.1); overflow:hidden;">
                  
                  <!-- Header -->
                  <div style="background:linear-gradient(135deg, #0066cc, #0099ff); padding:20px; text-align:center;">
                    <h1 style="margin:0; font-size:24px; color:#ffffff;">HAM GROUP</h1>
                    <p style="margin:5px 0 0; color:#e0f0ff; font-size:14px;">Doctor’s Instructions</p>
                  </div>
                  
                  <!-- Body -->
                  <div style="padding:25px; color:#333333; line-height:1.6;">
                    <h2 style="color:#0066cc; margin-top:0;">Doctor’s Recommendation</h2>
                    <p>Dear Patient,</p>
                    <p>
                      Based on your recent consultation, please proceed to the
                      <strong style="color:#d32f2f;">nearest hospital</strong> 
                      as advised by your doctor.
                    </p>
                    
                    <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
                      <p style="margin:0; font-size:14px; color:#0d47a1;">
                        ⚕️ Please keep this message for your medical reference.
                      </p>
                    </div>
                    
                    <p style="margin-top:30px;">Stay safe,</p>
                    <p style="font-weight:bold; color:#0066cc;">HAM GROUP Medical Team</p>
                  </div>
                  
                  <!-- Footer -->
                  <div style="background:#f1f1f1; padding:15px; text-align:center; font-size:12px; color:#777;">
                    © 2025 HAM GROUP - All rights reserved
                  </div>
                </div>
              </body>
            </html>
        """;

        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(mimeMessage);
    }
}
