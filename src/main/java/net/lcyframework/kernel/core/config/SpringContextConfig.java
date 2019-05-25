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
import net.lcyframework.kernel.core.context.SpringApplicationContext;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * <pre>
 * 名称: SpringContextConfig
 * 描述: spring上下文配置
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public class SpringContextConfig implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {
        SpringApplicationContext context = new SpringApplicationContext();
        context.setApplicationContext(event.getApplicationContext());
        log.info("SpringContext load success!");
    }
}
