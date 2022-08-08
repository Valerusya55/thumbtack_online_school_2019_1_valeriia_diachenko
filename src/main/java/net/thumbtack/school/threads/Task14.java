package net.thumbtack.school.threads;

import java.io.*;

public class Task14 {
    public static class Message {
        private String emailAddress;
        private String sender;
        private String subject;
        private String body;

        public Message(String emailAddress, String sender, String subject, String body) {
            this.emailAddress = emailAddress;
            this.sender = sender;
            this.subject = subject;
            this.body = body;
        }
    }

    public static class Transport {
        public static void send(Message message) throws IOException {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt",true))) {
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        bufferedWriter.write("Получатель: " + line + " " + "Отправитель: "
                                + message.emailAddress + " " + message.sender + " " + message.subject + " " + message.body);
                        bufferedWriter.newLine();
                        line = bufferedReader.readLine();
                    }
                }
            }
        }
    }

    static class NewThread extends Thread {
        private Message message;

        public NewThread(Message message) {
            this.message = message;
        }

        public void run() {
            try {
                Transport.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Message message = new Message("qww2er", "dftaer", "werwet", "wrtwrt");
        Message message1 = new Message("11qww2er", "11dftaer", "11erwet", "11wrtwrt");
        NewThread t1 = new NewThread(message);
        NewThread t2 = new NewThread(message1);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
