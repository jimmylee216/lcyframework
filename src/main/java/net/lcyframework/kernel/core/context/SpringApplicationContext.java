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

package net.lcyframework.kernel.core.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * <pre>
 * 名称: SpringApplicationContext
 * 描述: spring bean 的所有bean存储
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 设置applicationContext
     * @param applicationContext 上下文
     * @throws BeansException bean异常
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.APPLICATION_CONTEXT = applicationContext;
    }

    /**
     * 获取bean实例
     * @param beanName bean名字
     * @return bean实例
     * @throws BeansException bean异常
     */
    public static Object getBean(final String beanName) throws BeansException {
        return APPLICATION_CONTEXT.getBean(beanName);
    }

    /**
     * 获取bean实例
     * @param clazz    类型
     * @param <T>      返回类型
     * @return bean实例
     * @throws BeansException bean异常
     */
    public static <T> T getBean(final Class<T> clazz) throws BeansException {
        return APPLICATION_CONTEXT.getBean(clazz);
    }

    /**
     * 获取bean实例
     * @param beanName bean名字
     * @param clazz    类型
     * @param <T>      返回类型
     * @return bean实例
     * @throws BeansException bean异常
     */
    public static <T> T getBean(final String beanName, final Class<T> clazz) throws BeansException {
        return APPLICATION_CONTEXT.getBean(beanName, clazz);
    }

    /**
     * 获取bean实例
     * @param clazz    类型
     * @param <T>      返回类型
     * @return bean实例
     * @throws BeansException bean异常
     */
    public static <T> Map<String, T> getBeans(final Class<T> clazz) throws BeansException {
        return APPLICATION_CONTEXT.getBeansOfType(clazz);
    }

    /**
     * 注册bean
     * @param beanName bean名字
     * @param abstractBeanDefinition bean定义
     */
    public static void register(final String beanName, final AbstractBeanDefinition abstractBeanDefinition) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) APPLICATION_CONTEXT;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                .getBeanFactory();
        defaultListableBeanFactory.registerBeanDefinition(beanName, abstractBeanDefinition);
    }

    /**
     * 移除bean
     * @param beanId the beanId
     */
    public static void removeBean(final String beanId) {
        if (beanId == null || beanId.isEmpty()) {
            return;
        }
        ConfigurableApplicationContext applicationContexts = (ConfigurableApplicationContext) APPLICATION_CONTEXT;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContexts.getBeanFactory();
        beanFactory.removeBeanDefinition(beanId);
    }

    /**
     * 移除beans
     * @param beanIds the beanIds
     */
    public static void removeBeans(final String... beanIds) {
        if (beanIds == null || beanIds.length == 0) {
            return;
        }
        ConfigurableApplicationContext applicationContexts = (ConfigurableApplicationContext) APPLICATION_CONTEXT;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContexts.getBeanFactory();
        for (String beanId : beanIds) {
            if (beanId != null && !beanId.isEmpty()) {
                if (beanFactory.isBeanNameInUse(beanId)) {
                    beanFactory.removeBeanDefinition(beanId);
                }
            }
        }
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
