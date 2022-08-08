package net.thumbtack.school.threads;
class MyThread extends Thread {
    private String name;

    public MyThread(String threadname) {
        name = threadname;
    }
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(name + "Interrupted");
        }
        System.out.println(name + " exiting.");
    }
}
public class Task2 {
    public static void main(String[] args) {
        MyThread threadOne = new MyThread("One");
        threadOne.start();
        System.out.println("Thread One is alive: " + threadOne.isAlive());
        try {
            threadOne.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread One is alive: " + threadOne.isAlive());
        System.out.println("Main thread exiting.");
    }
}

