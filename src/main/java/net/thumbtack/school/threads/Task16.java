package net.thumbtack.school.threads;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task16 {
    public static class Task implements Executable {
        private Integer task;

        public Task(Integer task) {
            this.task = task;
        }

        @Override
        public void execute() {
            System.out.println("Task " + task + " execute");
        }
    }
    public interface Executable{
        void execute();
    }

    static class Producer extends Thread {
        private String name;
        private Task task;
        private BlockingQueue<Task> queue;
        private int count;

        public Producer(String name, Task task, BlockingQueue<Task> queue, int count) {
            this.name = name;
            this.task = task;
            this.queue = queue;
            this.count = count;
        }

        public void run() {
            System.out.println(name + " started");
            try {
                for (int i = 0; i < count; i++) {
                    queue.put(task);
                    Thread.sleep(200);
                }
                System.out.println(name + " finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private String name;
        private BlockingQueue<Task> queue;

        public Consumer(String name, BlockingQueue<Task> queue) {
            this.name = name;
            this.queue = queue;
        }

        public void run() {
            System.out.println(name + " started");
            while (true) {
                try {
                    Integer task = queue.take().task;
                    if (task == null) {
                        System.out.println(name + " finished");
                        break;
                    }
                    System.out.println(name + " retrieved: " + task);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int queueSize = 100;
        BlockingQueue<Task> blockingQueue = new ArrayBlockingQueue<>(queueSize);
        Producer[] producers = new Producer[3];
        Consumer[] consumers = new Consumer[3];

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer("Producer " + i, new Task(i), blockingQueue, 3);
            producers[i].start();
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("Consumer " + i, blockingQueue);
            consumers[i].start();
        }
        try {
            for (Producer producer : producers) {
                producer.join();
            }
            for (int i = 0; i < producers.length; i++) {
                blockingQueue.put(new Task(null));
            }
            for (Consumer consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
