package net.thumbtack.school.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Task6 {
    public static void main(String[] args) {
        final List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(new Random().nextInt(1000));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                if (list.isEmpty()) {
                    return;
                }
                list.remove((new Random().nextInt(list.size() - 1)) + 1);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("arrayList: " + list);
    }
}
