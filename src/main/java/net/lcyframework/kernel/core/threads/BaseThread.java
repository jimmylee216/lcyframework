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

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 线程基类
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public abstract class BaseThread implements Runnable {

    /** joinTime */
    private static final long JOIN_TIME = 90 * 1000;
    /** 线程 */
    protected final Thread thread;
    /** hasNotified, 默认为false */
    protected volatile boolean hasNotified;
    /** stoped, 默认为false */
    protected volatile boolean stoped;

    /** 线程基类 */
    public BaseThread() {
        this.thread = new Thread(this, this.getServiceName());
    }
    /**
     * serviceName
     * @return serviceName
     */
    public abstract String getServiceName();
    /** 启动线程 */
    public void start() {
        this.thread.start();
    }
    /** 关闭线程 */
    public void shutdown() {
        this.shutdown(false);
    }
    /** 停止线程 */
    public void stop() {
        this.stop(false);
    }
    /** 停止线程 */
    public void makeStop() {
        this.stoped = true;
        log.info("makestop thread {} ", this.getServiceName());
    }
    /**
     * 停止线程
     * @param interrupt 是否中断线程
     */
    public void stop(final boolean interrupt) {
        this.stoped = true;
        log.info("stop thread {} interrupt", this.getServiceName(), interrupt);
        synchronized (this) {
            if (!this.hasNotified) {
                this.hasNotified = true;
                this.notify();
            }
        }

        if (interrupt) {
            this.thread.interrupt();
        }
    }

    /**
     * 关闭任务
     * @param interrupt 中断
     */
    public void shutdown(final boolean interrupt) {
        this.stoped = true;
        log.info("shutdown thread {} interrupt {}", this.getServiceName(), interrupt);
        synchronized (this) {
            if (!this.hasNotified) {
                this.hasNotified = true;
                this.notify();
            }
        }

        try {
            if (interrupt) {
                this.thread.interrupt();
            }

            long beginTime = System.currentTimeMillis();
            if (!this.thread.isDaemon()) {
                this.thread.join(this.getJointime());
            }
            long eclipseTime = System.currentTimeMillis() - beginTime;
            log.info("join thread {} eclipse time(ms) {} {}", this.getServiceName(), eclipseTime, this.getJointime());
        } catch (final InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 唤醒
     */
    public void wakeup() {
        synchronized (this) {
            if (!this.hasNotified) {
                this.hasNotified = true;
                this.notify();
            }
        }
    }

    /**
     * 等待运行
     * @param interval 等待时间
     */
    protected void waitForRunning(final long interval) {
        synchronized (this) {
            if (this.hasNotified) {
                this.hasNotified = false;
                this.onWaitEnd();
                return;
            }

            try {
                this.wait(interval);
            } catch (final InterruptedException e) {
                log.error(e.getMessage(), e);
            } finally {
                this.hasNotified = false;
                this.onWaitEnd();
            }
        }
    }

    /**
     * 等待结束
     */
    protected void onWaitEnd() {
    }

    public boolean isStoped() {
        return stoped;
    }

    public long getJointime() {
        return JOIN_TIME;
    }
}
