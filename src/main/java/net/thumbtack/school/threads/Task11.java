package net.thumbtack.school.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task11 {
    static class PingPong {
        private Lock lock = new ReentrantLock();
        private Condition ping = lock.newCondition();
        private Condition pong = lock.newCondition();
        private boolean flag = false;

        public void ping() throws InterruptedException {
            lock.lock();
            try {
                while (flag) {
                    pong.await();
                }
                System.out.println("ping");
                Thread.sleep(1000);
                flag = true;
                ping.signal();
            } finally {
                lock.unlock();
            }
        }

        public void pong() throws InterruptedException {
            lock.lock();
            try {
                while (!flag) {
                    ping.await();
                }
                System.out.println("pong");
                Thread.sleep(1000);
                flag = false;
                pong.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Producer extends Thread {
        private PingPong pingPong;

        public Producer(PingPong pingPong) {
            this.pingPong = pingPong;
        }

        @Override
        public void run() {
            try {
                for (; ; ) {
                    pingPong.ping();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private PingPong pingPong;

        public Consumer(PingPong pingPong) {
            this.pingPong = pingPong;
        }

        @Override
        public void run() {
            try {
                for (; ; ) {
                    pingPong.pong();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        PingPong pingPong = new PingPong();

        Producer p = new Producer(pingPong);
        Consumer c = new Consumer(pingPong);

        p.start();
        c.start();

        try {
            p.join();
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
