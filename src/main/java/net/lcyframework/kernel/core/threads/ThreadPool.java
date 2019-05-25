/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lcyframework.kernel.core.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 名称: ThreadPool
 * 描述: 线程池
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public final class ThreadPool {

    private ThreadPool() { }

    /**
     * 创建可伸缩线程数据
     *
     * @param name  线程名称
     * @param cores 初始化线程数
     * @param threads 最大线程数
     * @param queues 线程等待池大小
     * @param alive 有效线程数
     * @return 线程池
     */
    public static ExecutorService newCachedThreadPool(final String name, final int cores, final int threads,
            final int queues, final int alive) {
        return new ThreadPoolExecutor(cores, threads, alive, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    /**
     * 创建可伸缩线程数据,初始化线程数为OS的CPU核数
     *
     * @param name 线程名称
     * @param threads 最大线程数
     * @param queues 线程等待池大小
     * @param alive 有效线程数
     * @return 线程池
     */
    public static ExecutorService newCachedThreadPool(final String name, final int threads, final int queues,
            final int alive) {
        return new ThreadPoolExecutor(Math.min(getSystemProcessors(), alive), threads, alive, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    /**
     * 创建固定线程池
     *
     * @param name 线程名称
     * @param threads 线程数
     * @param queues 线程等待池大小
     * @return 线程池
     */
    public static ExecutorService newFixedThreadPool(final String name, final int threads, final int queues) {
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    /**
     * 创建固定线程池,初始化线程数为OS的CPU核数
     *
     * @param name 线程名称
     * @param queues 线程等待池大小
     * @return 线程池
     */
    public static ExecutorService newFixedThreadPool(final String name, final int queues) {
        int threads = getSystemProcessors();
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    /**
     * 创建只增线程池
     *
     * @param name 线程名称
     * @param cores 初始化线程数
     * @param threads 最大线程数
     * @param queues 线程等待池大小
     * @return 线程池
     */
    public static ExecutorService newLimitedThreadPool(final String name, final int cores, final int threads, final int queues) {
        return new ThreadPoolExecutor(cores, threads, Long.MAX_VALUE, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    /**
     * 创建只增线程池,初始化线程数为OS的CPU核数
     *
     * @param name 线程名称
     * @param threads 最大线程数
     * @param queues 线程等待池大小
     * @return 线程池
     */
    public static ExecutorService newLimitedThreadPool(final  String name, final int threads, final int queues) {
        return new ThreadPoolExecutor(Math.min(getSystemProcessors(), threads), threads, Long.MAX_VALUE,
                TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<Runnable>()
                        : (queues < 0 ? new LinkedBlockingQueue<Runnable>() : new ArrayBlockingQueue<Runnable>(queues)),
                new NamedThreadFactory(name, true), new AbortPolicyWithReport(name));
    }

    private static int getSystemProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

}
