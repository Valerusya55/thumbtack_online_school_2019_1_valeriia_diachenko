package net.thumbtack.school.threads;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task10 {

    static class LockThread extends Thread {
        private String name;
        private Lock lock;
        final List<Integer> list = new ArrayList<>();

        public LockThread(Lock lock, String name) {
            this.lock = lock;
            this.name = name;
        }

        public void run() {

            System.out.println("Starting " + name);

            try {
                if (name.equals("Add")) {
                    for (int i = 0; i < 10000; i++) {
                        synchronized (list) {
                            list.add(new Random().nextInt(1000));
                        }
                    }
                } else {
                    for (int i = 0; i < 10000; i++) {
                        if (list.isEmpty()) {
                            return;
                        }
                        synchronized (list) {
                            list.remove((new Random().nextInt(list.size() - 1)) + 1);
                        }
                    }
                }
                lock.lock();
                Thread.sleep(1000);
            } catch (InterruptedException exc) {
                System.out.println(exc);
            } finally {
                lock.unlock();
                System.out.println("arrayList: " + list);
            }
        }
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        new LockThread(lock, "Add").start();
        new LockThread(lock, "Remove").start();
    }
}
