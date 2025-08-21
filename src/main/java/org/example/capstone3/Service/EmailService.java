package org.example.capstone3.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Report;
import org.example.capstone3.Repository.DoctorRepository;
import org.example.capstone3.Repository.PatientRepository;
import org.example.capstone3.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//Mohammed

@Service
@RequiredArgsConstructor
public class EmailService {


    @Autowired
    private JavaMailSender mailSender;
    private final ReportRepository reportRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ReportService reportService;


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

    private String baseTemplate(String title, String bodyContent) {
        return """
        <html>
          <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f4f6f9;">
            <div style="max-width:600px; margin:30px auto; background:#ffffff; border-radius:12px; box-shadow:0 4px 12px rgba(0,0,0,0.1); overflow:hidden;">

              <!-- Header -->
              <div style="background:linear-gradient(135deg, #0066cc, #0099ff); padding:20px; text-align:center;">
                <h1 style="margin:0; font-size:24px; color:#ffffff;">HAM GROUP</h1>
                <p style="margin:5px 0 0; color:#e0f0ff; font-size:14px;">%s</p>
              </div>

              <!-- Body -->
              <div style="padding:25px; color:#333333; line-height:1.6;">
                %s
              </div>

              <!-- Footer -->
              <div style="background:#f1f1f1; padding:15px; text-align:center; font-size:12px; color:#777;">
                © 2025 HAM GROUP - All rights reserved
              </div>
            </div>
          </body>
        </html>
        """.formatted(title, bodyContent);
    }




    public void sendWelcomeDoctorEmail(String to, String doctorName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Welcome Dr. " + doctorName);

        // 📌 محتوى مخصص للترحيب بالدكتور
        String bodyContent = """
        <h2 style="color:#0066cc; margin-top:0;">Welcome, Dr. %s!</h2>
        <p>
          We are honored to welcome you to <b>HAM GROUP</b>.  
          Your expertise and dedication will strengthen our mission to provide the best healthcare services.
        </p>
        <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
          🌟 Together, we will continue making a difference in patients’ lives.
        </div>
        <p style="margin-top:30px;">Best regards,</p>
        <p style="font-weight:bold; color:#0066cc;">HAM GROUP Management Team</p>
        """.formatted(doctorName);

        // 📌 استخدام القالب الأساسي مع عنوان مختلف
        String htmlContent = baseTemplate("Welcome to Our Medical Team", bodyContent);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }

    public void sendWelcomePatientEmail(String to, String patientName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Welcome " + patientName);

        // 📌 محتوى الرسالة المخصص للمريض
        String bodyContent = """
        <h2 style="color:#0066cc; margin-top:0;">Welcome, %s!</h2>
        <p>
          We are delighted to welcome you to <b>HAM GROUP</b>.  
          Our medical team is committed to providing you with the highest quality of care 
          and ensuring your health and well-being.
        </p>
        <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
          💙 Your health and comfort are our top priority.
        </div>
        <p style="margin-top:30px;">Warm regards,</p>
        <p style="font-weight:bold; color:#0066cc;">HAM GROUP Support Team</p>
        """.formatted(patientName);

        // 📌 استخدام القالب الأساسي
        String htmlContent = baseTemplate("Welcome to Our Healthcare Family", bodyContent);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }


    public void sendDoctorProfileUpdatedEmail(String to, String doctorName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Profile Update Confirmation");

        // 📌 محتوى الرسالة المخصص
        String bodyContent = """
        <h2 style="color:#0066cc; margin-top:0;">Hello Dr. %s,</h2>
        <p>
          We would like to inform you that your <b>personal profile details</b> 
          have been successfully updated in our system.
        </p>
        <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
          ✅ Your information is now up-to-date and securely saved.
        </div>
        <p>
          If you did not request this change, please contact our support team immediately.
        </p>
        <p style="margin-top:30px;">Best regards,</p>
        <p style="font-weight:bold; color:#0066cc;">HAM GROUP Support Team</p>
        """.formatted(doctorName);

        // 📌 استخدام القالب الأساسي (الهيدر والفوتر الثابتين)
        String htmlContent = baseTemplate("Profile Updated Successfully", bodyContent);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }


    public void sendPatientProfileUpdatedEmail(String to, String patientName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Profile Update Confirmation");

        // 📌 محتوى الرسالة المخصص
        String bodyContent = """
        <h2 style="color:#0066cc; margin-top:0;">Hello %s,</h2>
        <p>
          We would like to inform you that your <b>personal profile details</b> 
          have been successfully updated in our system.
        </p>
        <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
          ✅ Your information is now up-to-date and securely saved.
        </div>
        <p>
          If you did not request this change, please contact our support team immediately.
        </p>
        <p style="margin-top:30px;">Best regards,</p>
        <p style="font-weight:bold; color:#0066cc;">HAM GROUP Support Team</p>
        """.formatted(patientName);

        // 📌 استخدام القالب الأساسي (الهيدر والفوتر الثابتين)
        String htmlContent = baseTemplate("Profile Updated Successfully", bodyContent);

        helper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }


    public void sendPatientReportEmail(String to, String patientName,Integer patientId,Integer doctorId) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("your_email@gmail.com");
        helper.setTo(to);
        helper.setSubject("HAM GROUP - Medical Report for " + patientName);

        // نص الرسالة
        String bodyContent = """
            <h2 style="color:#0066cc; margin-top:0;">Hello %s,</h2>
            <p>
              Please find attached your <b>medical report</b>.  
              If you have any questions, feel free to contact our support team.
            </p>
            <div style="margin:25px 0; padding:15px; background:#e3f2fd; border-left:5px solid #2196f3; border-radius:8px;">
              📑 Report attached in PDF format.
            </div>
            <p style="margin-top:30px;">Best regards,</p>
            <p style="font-weight:bold; color:#0066cc;">HAM GROUP Medical Team</p>
            """.formatted(patientName);

        // نستخدم القالب الأساسي (الهيدر والفوتر)
        String htmlContent = baseTemplate("Medical Report", bodyContent);

        helper.setText(htmlContent, true);

        Report report = reportRepository.findReportByPatient_IdAndDoctor_Id(patientId, doctorId);
        if (report == null) throw new ApiException("report not found!");

        if (report.getPatient() == null || report.getDoctor() == null
                || !report.getPatient().getId().equals(patientId)
                || !report.getDoctor().getId().equals(doctorId)) {
            throw new ApiException("report does not belong to given patient/doctor");
        }

        // نضيف الـ PDF كمرفق
        helper.addAttachment("MedicalReport.pdf", new ByteArrayResource(reportService.generatePdfReport(patientId,doctorId)));

        mailSender.send(mimeMessage);
    }


















}
