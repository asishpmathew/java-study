package com.example.java.memory;

import java.util.*;
import java.util.concurrent.*;

/**
 * MemoryLeakDemo.java
 * Simulates common Java memory leak scenarios:
 *   1. Static collection growing unbounded
 *   2. Listener/callback not removed
 *   3. ThreadLocal not cleaned up
 *   4. Cache with no eviction
 *
 * Run with:
 *   javac MemoryLeakDemo.java
 *   java -Xmx256m -verbose:gc -Xlog:gc* MemoryLeakDemo
 */
public class MemoryLeakDemo {

    // ----------------------------------------------------------------
    // LEAK 1: Static collection — objects added, never removed
    // ----------------------------------------------------------------
    private static final List<byte[]> staticLeakList = new ArrayList<>();

    static void leak1_staticCollection() {
        System.out.println("[LEAK 1] Static collection growing...");
        for (int i = 0; i < 100; i++) {
            staticLeakList.add(new byte[1024 * 100]); // 100 KB each
        }
    }

    // ----------------------------------------------------------------
    // LEAK 2: Listener registered, never unregistered
    // ----------------------------------------------------------------
    interface EventListener {
        void onEvent(String msg);
    }

    static class EventBus {
        private static final List<EventListener> listeners = new ArrayList<>();

        static void register(EventListener l)   { listeners.add(l); }
        // unregister() intentionally missing — simulates the leak
        static void fireEvent(String msg)       { listeners.forEach(l -> l.onEvent(msg)); }
        static int  listenerCount()             { return listeners.size(); }
    }

    static void leak2_listenerLeak() {
        System.out.println("[LEAK 2] Registering listeners without unregistering...");
        for (int i = 0; i < 500; i++) {
            final int id = i;
            // Anonymous class holds a reference to outer scope → never GC'd
            EventBus.register(msg -> System.out.println("  Listener " + id + ": " + msg));
        }
        System.out.println("  Total listeners: " + EventBus.listenerCount());
        EventBus.fireEvent("ping");
    }

    // ----------------------------------------------------------------
    // LEAK 3: ThreadLocal not removed after task finishes
    // ----------------------------------------------------------------
    static final ThreadLocal<List<byte[]>> threadLocalData = new ThreadLocal<>();

    static void leak3_threadLocal() throws InterruptedException {
        System.out.println("[LEAK 3] ThreadLocal not cleaned up in thread pool...");
        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            pool.submit(() -> {
                // Set data but never call threadLocalData.remove()
                List<byte[]> data = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    data.add(new byte[1024 * 50]); // 50 KB each
                }
                threadLocalData.set(data);
                // threadLocalData.remove(); ← FIX: uncomment this to prevent leak
                System.out.println("  Thread " + Thread.currentThread().getName() + " done (leaked ThreadLocal)");
            });
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    // ----------------------------------------------------------------
    // LEAK 4: Unbounded cache (HashMap as cache, no eviction)
    // ----------------------------------------------------------------
    static final Map<String, byte[]> cache = new HashMap<>();

    static void leak4_unboundedCache() {
        System.out.println("[LEAK 4] Filling unbounded cache...");
        for (int i = 0; i < 500; i++) {
            String key = "key-" + UUID.randomUUID();
            cache.put(key, new byte[1024 * 50]); // 50 KB per entry
        }
        System.out.println("  Cache size: " + cache.size() + " entries");
    }

    // ----------------------------------------------------------------
    // MAIN
    // ----------------------------------------------------------------
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Memory Leak Demo Started ===");
        System.out.println("PID: " + ProcessHandle.current().pid());
        System.out.println("Run the thread dump script in another terminal now.\n");

        // Run leaks in a loop so memory builds up gradually
        int round = 1;
        while (true) {
            System.out.println("\n--- Round " + round++ + " ---");
            leak1_staticCollection();
            leak2_listenerLeak();
            leak3_threadLocal();
            leak4_unboundedCache();

            Runtime rt = Runtime.getRuntime();
            long used = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
            long total = rt.totalMemory() / (1024 * 1024);
            long max   = rt.maxMemory()   / (1024 * 1024);
            System.out.printf("  Heap: %d MB used / %d MB total / %d MB max%n", used, total, max);

            Thread.sleep(3000); // pause between rounds
        }
    }
}
