package net.thumbtack.school.threads;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task15 {
    public static class Data {
        private int[] data;

        public Data(int[] data) {
            this.data = data;
        }

        public int[] get() {
            return data;
        }
    }

    static class Producer extends Thread {
        private String name;
        private Data data;
        private BlockingQueue<Data> queue;
        private int count;

        public Producer(String name, Data data, BlockingQueue<Data> queue, int count) {
            this.name = name;
            this.data = data;
            this.queue = queue;
            this.count = count;
        }

        public void run() {
            System.out.println(name + " started");
            try {
                for (int i = 0; i < count; i++) {
                    queue.put(data);
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
        private BlockingQueue<Data> queue;

        public Consumer(String name, BlockingQueue<Data> queue) {
            this.name = name;
            this.queue = queue;
        }

        public void run() {
            System.out.println(name + " started");
            while (true) {
                try {
                    int[] data = queue.take().get();
                    if (data == null) {
                        System.out.println(name + " finished");
                        break;
                    }
                    System.out.println(name + " retrieved: " + Arrays.toString(data));
                    Thread.sleep(400);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int queueSize = 100;
        BlockingQueue<Data> blockingQueue = new ArrayBlockingQueue<>(queueSize);
        Producer[] producers = new Producer[3];
        Consumer[] consumers = new Consumer[3];

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer("Producer " + i, new Data(new int[]{i, i, i}), blockingQueue, 3);
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
                blockingQueue.put(new Data(null));
            }
            for (Consumer consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
