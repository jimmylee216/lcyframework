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

import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import net.lcyframework.kernel.core.config.condition.SpringContextCondition;
import net.lcyframework.kernel.core.context.SpringApplicationContext;

/**
 * <pre>
 * 名称: BaseProperties
 * 描述: 核心配置文件
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Configuration
public class CoreConfig implements EnvironmentAware {

    @Override
    public void setEnvironment(final Environment event) {
        BaseProperties.loadData(event);
    }

    /**
     * 装载核心配置
     * @param applicationContext spring上下文
     * @return springApplicationContext
     */
    @Bean
    @Conditional(SpringContextCondition.class)
    public SpringApplicationContext springApplicationContext(final ApplicationContext applicationContext) {
        SpringApplicationContext context = new SpringApplicationContext();
        context.setApplicationContext(applicationContext);
        return context;
    }

}
