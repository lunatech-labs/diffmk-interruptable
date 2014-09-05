package com.lunatech.utils;

import java.util.concurrent.*;

public class TimeLimitedComputation {
    /**
     * Try to execute a callable until timer runs out. The Callable will need to check for
     * Thread.currentThread().isInterrupted() and throw InterruptedException.
     * @param seconds How many seconds to run
     * @param callable What to do
     * @param <T> Return type of the computation
     * @return Result of the callable
     * @throws TimeoutException
     * @throws ExecutionException
     */
    public static <T> T execute(int seconds, Callable<T> callable) throws TimeoutException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<T> fut = pool.submit(callable);
        try {
            return fut.get(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new TimeoutException("Interrupted");
        } finally {
            pool.shutdownNow();
        }
    }

    public static void main(String[] args) {
        Callable<String> foo = new Callable<String>() {
            public String call() throws Exception {
                String c = null;
                for (long i = 0; i < 1000000L; i++) {
                    if (Thread.currentThread().isInterrupted())
                        throw new InterruptedException("I got interrupted");
                    for (long j = 0; j < 100000; j++) {
                        long b = i*j;
                        c = Long.toString(b);
                    }
                }
                return "foo"+c;
            }
        };
        try {
            System.out.println(TimeLimitedComputation.execute(5, foo));
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
