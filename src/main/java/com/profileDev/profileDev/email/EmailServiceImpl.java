package com.profileDev.profileDev.email;

import com.profileDev.profileDev.Auditing.ProfileDtoAuditingRepository;
import com.profileDev.profileDev.dto.ProfileDevAuditDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class EmailServiceImpl {

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private ProfileDtoAuditingRepository profileDtoAuditingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Environment env;
    public List<ProfileDevAuditDTO> sendAuditMail(String toMailId, String subject, String filePresent) throws MessagingException, IOException {

        List<ProfileDevAuditDTO> auditList = Arrays.asList(modelMapper.map(profileDtoAuditingRepository.findAll(), ProfileDevAuditDTO[].class));


        if (!Boolean.valueOf(filePresent)) {
            SimpleMailMessage message = new SimpleMailMessage();
            System.out.println("### EmailServiceImpl > sendAuditMail() > if > " + toMailId + "___" + subject);

            message.setFrom("prabhatsuyal20@gmail.com");
            message.setTo(toMailId);
            message.setSubject(subject);
            message.setText(auditList.toString());

            //send simple mail without attachment
/*        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table border=1>");
        auditList.forEach(e -> stringBuilder.append("<tr>").append(e).append("</tr>"));
        stringBuilder.append("</table>");
        message.setText(String.valueOf(stringBuilder));                 // format body as table format
*/
            System.out.println("### .................................EmailServiceImpl > sendAuditMail() > if > before Mail sent. " + toMailId + "___" + subject);
            javaMailSender.send(message);
            System.out.println("### .................................EmailServiceImpl > sendAuditMail() > if > after Mail sent. " + toMailId + "___" + subject);

            return auditList;
        }
        else {
            //send simple mail with attachment
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            System.out.println("### EmailServiceImpl > sendAuditMail() > else >" + toMailId + "___" + subject);

            String filePath = "test3.txt";
            FileOutputStream file = new FileOutputStream(filePath);          // create new file
            file.write(auditList.toString().getBytes());                     // write content in file
            file.close();
            FileSystemResource file1 = new FileSystemResource(filePath);     // creating attachable file                     //env.getProperty("MailReport"));//new File("attach.txt"));//

            helper.setFrom("prabhatsuyal20@gmail.com");
            helper.setTo(toMailId);
            helper.setSubject(subject);
            helper.addAttachment(filePath, file1 );                          // visible file name in attachment
            helper.setText("text for mail body");

            System.out.println("### .................................EmailServiceImpl > sendAuditMail() > else > before Mail sent. " + toMailId + "___" + subject);
            javaMailSender.send(mimeMessage);
            new File(filePath).delete();                                     // delete file after mail sent
            System.out.println("### .................................EmailServiceImpl > sendAuditMail() > else > after Mail sent. " + toMailId + "___" + subject);
            return auditList;
        }
    }
}
