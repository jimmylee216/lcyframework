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

package net.lcyframework.kernel.core.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import net.lcyframework.kernel.core.config.BaseProperties;

/**
 * <pre>
 * 任务管理
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public final class TaskManger {

    /**
     * 线程池
     */
    public static final Map<String, ScheduledFuture<?>> TASK_POOL = new ConcurrentHashMap<String, ScheduledFuture<?>>();
    private static final ScheduledExecutorService SCHEDULER = new ScheduledThreadPoolExecutor(
            BaseProperties.getProperty("system.task.threads", Integer.class, 10), Executors.defaultThreadFactory());
    private static volatile TaskManger TASK_MANGER;

    private TaskManger() {
    }

    /**
     * 单例
     * @return taskManager
     */
    public static TaskManger getInstance() {
        if (TASK_MANGER == null) {
            synchronized (TaskManger.class) {
                if (TASK_MANGER == null) {
                    TASK_MANGER = new TaskManger();
                }
            }
        }
        return TASK_MANGER;
    }

    /**
     * 添加任务
     * @param task     任务
     * @param taskName 任务名
     * @param period   周期
     * @param timeUnit 单位
     */
    public void addTask(final ITask task, final String taskName, final long period, final TimeUnit timeUnit) {
        ScheduledFuture<?> sf = SCHEDULER.scheduleAtFixedRate(() -> {
            try {
                task.execute();
            } catch (final Exception ex) {
                log.error("task error:[{}]:{}", taskName, ex.getMessage(), ex);
            }
        }, 0, period, timeUnit);
        TASK_POOL.put(taskName, sf);
    }

    /**
     * 取消任务
     * @param taskNames 任务名
     */
    public void cancelTask(final String... taskNames) {
        for (String taskName : taskNames) {
            ScheduledFuture<?> sf = TASK_POOL.get(taskName);
            if (sf != null) {
                sf.cancel(true);
            }
        }
    }

}
