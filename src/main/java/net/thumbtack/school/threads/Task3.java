package net.thumbtack.school.threads;

public class Task3 {
    public static void main(String[] args) {

        MyThread threadOne = new MyThread("One");
        MyThread threadTwo = new MyThread("Two");
        MyThread threadThree = new MyThread("Three");

        threadOne.start();
        threadTwo.start();
        threadThree.start();

        System.out.println("Thread One is alive: " + threadOne.isAlive());
        System.out.println("Thread Two is alive: " + threadTwo.isAlive());
        System.out.println("Thread Three is alive: " + threadThree.isAlive());

        try {
            threadOne.join();
            threadTwo.join();
            threadThree.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        System.out.println("Thread One is alive: " + threadOne.isAlive());
        System.out.println("Thread Two is alive: " + threadTwo.isAlive());
        System.out.println("Thread Three is alive: " + threadThree.isAlive());

        System.out.println("Main thread exiting.");
    }
}
