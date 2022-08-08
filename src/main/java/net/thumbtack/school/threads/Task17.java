package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task17 {
    public enum Event {
        TASK_STARTED,
        TASK_FINISHED,
        PRODUCER_FINISHED
    }

    public static class MultiStageTask {
        private Integer task;
        private List<Task16.Executable> list;

        public MultiStageTask(Integer task, List<Task16.Executable> list) {
            this.task = task;
            this.list = list;
        }
    }

    static class Producer extends Thread {
        private String name;
        private MultiStageTask task;
        private BlockingQueue<MultiStageTask> queue;
        private int count;
        private BlockingQueue<Event> queueCount;

        public Producer(String name, MultiStageTask task, BlockingQueue<MultiStageTask> queue,
                        int count, BlockingQueue<Event> queueCount) {
            this.name = name;
            this.task = task;
            this.queue = queue;
            this.count = count;
            this.queueCount = queueCount;
        }

        public void run() {
            System.out.println(name + " started");
            try {
                for (int i = 0; i < count; i++) {
                    queue.put(task);
                    queueCount.put(Event.TASK_STARTED);
                    Thread.sleep(200);
                }
                queueCount.put(Event.PRODUCER_FINISHED);
                System.out.println(name + " finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private String name;
        private BlockingQueue<MultiStageTask> queue;
        private BlockingQueue<Event> queueCount;

        public Consumer(String name, BlockingQueue<MultiStageTask> queue, BlockingQueue<Event> queueCount) {
            this.name = name;
            this.queue = queue;
            this.queueCount = queueCount;
        }

        public void run() {
            System.out.println(name + " started");
            while (true) {
                try {
                    MultiStageTask task = queue.take();
                    if (task.task == null) {
                        System.out.println(name + " finished");
                        break;
                    }
                    if (!task.list.isEmpty()) {
                        task.list.remove(0).execute();
                    }
                    if (!task.list.isEmpty()) {
                        queue.put(task);
                    } else {
                        queueCount.put(Event.TASK_FINISHED);
                    }
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int queueSize = 100;
        int countProducerFinished = 0;
        int countTaskFinished = 0;
        int countTaskStarted = 0;

        BlockingQueue<MultiStageTask> blockingQueue = new ArrayBlockingQueue<>(queueSize);
        BlockingQueue<Event> queue = new ArrayBlockingQueue<>(queueSize);
        Producer[] producers = new Producer[3];
        Consumer[] consumers = new Consumer[3];
        List<Task16.Executable> list = new ArrayList<>();
        Task16.Executable task1 = new Task16.Task(1);
        Task16.Executable task2 = new Task16.Task(2);
        Task16.Executable task3 = new Task16.Task(3);
        Collections.addAll(list, task1, task2, task3);

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer("Producer " + i, new MultiStageTask(i, list), blockingQueue, 3, queue);
            producers[i].start();
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("Consumer " + i, blockingQueue, queue);
            consumers[i].start();
        }
        try {
            while (producers.length != countProducerFinished || countTaskStarted != countTaskFinished) {
                Event string = queue.take();
                if (string.equals(Event.PRODUCER_FINISHED)) {
                    countProducerFinished++;
                }
                if (string.equals(Event.TASK_STARTED)) {
                    countTaskStarted++;
                }
                if (string.equals(Event.TASK_FINISHED)) {
                    countTaskFinished++;
                }
            }
            for (int i = 0; i < consumers.length; i++) {
                blockingQueue.put(new MultiStageTask(null, null));
            }
            for (Producer producer : producers) {
                producer.join();
            }
            for (Consumer consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
