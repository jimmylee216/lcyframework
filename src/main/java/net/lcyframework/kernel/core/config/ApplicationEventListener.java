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

package net.lcyframework.kernel.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * <pre>
 * 名称: ApplicationEventListener
 * 描述: 应用监听
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class ApplicationEventListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.debug("初始化环境变量完成...");
        } else if (event instanceof ApplicationPreparedEvent) {
            log.debug("初始化完成...");
        } else if (event instanceof ContextRefreshedEvent) {
            log.debug("应用刷新...");
        } else if (event instanceof ApplicationReadyEvent) {
            log.debug("应用已启动完成...");
        } else if (event instanceof ContextStartedEvent) {
            // 应用启动，需要在代码动态添加监听器才可捕获
            log.debug("应用启动...");
        } else if (event instanceof ContextStoppedEvent) {
            log.error("应用停止...");
        } else if (event instanceof ContextClosedEvent) {
            log.error("应用关闭...");
        }
    }

}
