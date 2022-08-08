package net.thumbtack.school.threads;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task12 {
    public static class MyConcurrentHashMap<K, V> {
        private final Map<K, V> map;
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public MyConcurrentHashMap() {
            map = new HashMap<>();
        }

        public MyConcurrentHashMap(Map<K, V> map) {
            this.map = map;
        }

        public synchronized void put(K key, V value) {
            lock.writeLock().lock();
            try {
                synchronized (map) {
                    map.put(key, value);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public synchronized V get(K key) {
            lock.writeLock().lock();
            try {
                synchronized (map) {
                    return map.get(key);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public synchronized void remove(K key) {
            lock.writeLock().lock();
            try {
                synchronized (map) {
                    map.remove(key);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
