package net.thumbtack.school.threads;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task13 {
    public static class Formatter {
        public static SimpleDateFormat format(Date date) {
            return new SimpleDateFormat(date.toLocaleString());
        }
    }

    static class ThreadLocalHolder implements Runnable {

        private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            threadLocal.set(Formatter.format(new Date()));
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get().toPattern());
        }
    }

    public static void main(String[] args) {
        ThreadLocalHolder threadLocalHolder = new ThreadLocalHolder();
        Thread t1 = new Thread(threadLocalHolder);
        Thread t2 = new Thread(threadLocalHolder);
        Thread t3 = new Thread(threadLocalHolder);
        Thread t4 = new Thread(threadLocalHolder);
        Thread t5 = new Thread(threadLocalHolder);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

