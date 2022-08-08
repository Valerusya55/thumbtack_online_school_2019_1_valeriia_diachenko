package net.thumbtack.school.threads;
import java.util.concurrent.Semaphore;

public class Task7 {
    static class PingPong {

        static Semaphore semCon = new Semaphore(0);
        static Semaphore semProd = new Semaphore(1);

        public void pong() {
            try {
                semCon.acquire();
                System.out.println("pong");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            } finally {
                semProd.release();
            }
        }

        public void ping() {
            try {
                semProd.acquire();
                System.out.println("ping");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException caught");
            } finally {
                semCon.release();

            }
        }
    }

    static class PingThread extends Thread {
        private final PingPong q;

        public PingThread(PingPong q) {
            this.q = q;
        }

        public void run() {
            for(; ; ) {
                q.ping();
            }
        }
    }

    static class PongThread extends Thread {
        private final PingPong q;

        public PongThread(PingPong q) {
            this.q = q;
        }

        public void run() {
            for (;;){
                q.pong();
            }
        }
    }

    public static void main(String[] args) {
        PingPong q = new PingPong();
        new PongThread(q).start();
        new PingThread(q).start();
    }
}
