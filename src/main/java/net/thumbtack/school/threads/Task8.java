package net.thumbtack.school.threads;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task8 {
    public static class ReadWriteLockDemo {
        static private int i = 0;
        static private ReadWriteLock lock = new ReentrantReadWriteLock();

        public static void main(String[] args) {
            Runnable writeTask = () -> {
                lock.writeLock().lock();
                System.out.println("  Write lock locked by thread " + Thread.currentThread().getId());
                try {
                    i++;
                    System.out.println("  Writer thread " + Thread.currentThread().getId() + " set value " + i);
                } finally {
                    System.out.println("  Write lock unlocked by thread " + Thread.currentThread().getId());
                    lock.writeLock().unlock();
                }
            };

            Runnable readTask = () -> {
                lock.readLock().lock();
                System.out.println("Read lock locked by thread " + Thread.currentThread().getId());
                try {
                    System.out.println("Reader thread " + Thread.currentThread().getId() + " read value " + i);
                } finally {
                    System.out.println("Read lock unlocked by thread " + Thread.currentThread().getId());
                    lock.readLock().unlock();
                }
            };

            for (int i = 0; i < 3; i++) {
                new Thread(readTask).start();
                new Thread(writeTask).start();
                new Thread(readTask).start();
                new Thread(writeTask).start();
                new Thread(readTask).start();
                new Thread(writeTask).start();
            }
        }

    }
}
