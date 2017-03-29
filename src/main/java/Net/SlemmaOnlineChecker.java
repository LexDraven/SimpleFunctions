package Net;


import Simple.URLChecker;

public class SlemmaOnlineChecker {

    public static void main(String[] args) {
        MailSender mailSender = new MailSender("","");
        mailSender.sendEmail("farofwell@gmail.com", "Checker is running","The check is on the line!");
        int code = 200;
        URLChecker checker = new URLChecker();
        while (code==200){
            checker.checkLinks("https://slemma.com");
            code = checker.getStatusCode();
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        //MailSender mailSender = new MailSender("","");
        mailSender.sendEmail("login", "The release is DOWN!","The response from slemma.com is not 200! And the answer code is "+code);
    }
}
