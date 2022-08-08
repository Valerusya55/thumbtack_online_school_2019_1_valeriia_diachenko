package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task5 {
    public enum Mode {
        ADD,
        REMOVE;
    }

    static class AddRemoveThread extends Thread {
        private List<Integer> list;
        private Mode mode;

        public AddRemoveThread(Mode mode, List<Integer> list) {
            this.mode = mode;
            this.list = list;
        }

        public synchronized void add(List<Integer> list) {
            list.add(new Random().nextInt(1000));
        }

        public synchronized void remove(List<Integer> list) {
            list.remove((new Random().nextInt(list.size() - 1)) + 1);
        }

        public void run() {
            if (mode.equals(Mode.ADD)) {
                for (int i = 0; i < 10000; i++) {
                    add(list);
                }
            } else {
                if (list.isEmpty()) {
                    return;
                }
                for (int i = 0; i < 10000; i++) {
                    remove(list);
                }
            }
            System.out.println(list);
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        AddRemoveThread addThread = new AddRemoveThread(Mode.ADD, list);
        AddRemoveThread removeThread = new AddRemoveThread(Mode.REMOVE, list);

        addThread.start();
        removeThread.start();

        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
