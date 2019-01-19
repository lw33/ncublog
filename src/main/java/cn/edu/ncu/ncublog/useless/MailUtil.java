package cn.edu.ncu.ncublog.useless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author lw
 * @Date 2018-09-03 20:53:35
 **/
//@Component
public class MailUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

   /* //@Autowired
    JavaMailSender mailSender;

    //@Autowired
    //FreeMarkerTemplateUtils templateEngine;

    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) {

        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            e.printStackTrace();
            //throw new ServiceException(e.getMessage());
        }
    }*/

}
