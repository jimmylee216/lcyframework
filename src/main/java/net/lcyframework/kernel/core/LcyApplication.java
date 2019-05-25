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

package net.lcyframework.kernel.core;

import org.springframework.boot.ResourceBanner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;

import net.lcyframework.kernel.core.config.ApplicationEventListener;
import net.lcyframework.kernel.core.config.PropertiesConfig;
import net.lcyframework.kernel.core.config.SpringContextConfig;

/**
 * <pre>
 * 应用启动入口
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public final class LcyApplication {

    private LcyApplication() { }

    /**
     * 默认的启动方法
     * @param startClass 启动类
     * @param args       启动参数
     */
    public static void start(final Class<?> startClass, final String[] args) {
        // 设置log4j2为全局异步处理
        ResourceBanner rb = new ResourceBanner(new ClassPathResource("banner.txt"));
        new SpringApplicationBuilder()
                .listeners(new SpringContextConfig(), new ApplicationEventListener(), new PropertiesConfig())
                .sources(startClass).banner(rb).run(args);
    }
}
