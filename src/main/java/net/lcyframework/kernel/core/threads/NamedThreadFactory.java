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

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 名称: NamedThreadFactory
 * 描述: NamedThreadFactory
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String mPrefix;

    private final boolean mDaemo;

    private final ThreadGroup mGroup;

    /**
     * 构造方法
     */
    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    /**
     * 构造方法
     * @param prefix 前缀
     */
    public NamedThreadFactory(final String prefix) {
        this(prefix, false);
    }

    /**
     * 构造方法
     * @param prefix 前缀
     * @param daemo  是否守护
     */
    public NamedThreadFactory(final String prefix, final boolean daemo) {
        mPrefix = prefix + "-thread-";
        mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    /**
     * 创建新线程
     * @param runnable 任务
     * @return 线程
     */
    @Override
    public Thread newThread(final Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }
}
