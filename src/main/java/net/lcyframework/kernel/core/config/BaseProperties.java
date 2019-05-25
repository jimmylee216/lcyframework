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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import lombok.extern.slf4j.Slf4j;
import net.lcyframework.kernel.core.consts.SysErrorConsts;
import net.lcyframework.kernel.core.exception.SysException;

/**
 * <pre>
 * 名称: BaseProperties
 * 描述: spring中获取资源文件
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public class BaseProperties extends PropertyPlaceholderConfigurer {

    // 保存k,v配置
    private static Map<String, Object> CTX_PROPERTIES_MAP = new HashMap<String, Object>();

    private static ConfigurableConversionService CONVERSION_SERVICE = new DefaultConversionService();

    // 原子性
    @SuppressWarnings("unused")
    private static AtomicBoolean LOAD_FLOG = new AtomicBoolean(true);

    // StandardServletEnvironment的PropertySources的名字作为key，保证PropertySources的原子性，不被多次加载
    private static Map<String, Object> ATOMIC_MAP = new HashMap<String, Object>();

    /* remove by xlxcc 2018-3-14 0:23:00 start*/
    @SuppressWarnings("unused")
    private static final String APP_PROPERTIES = "applicationConfigurationProperties";
    /* remove by xlxcc 2018-3-14 0:23:00 end */

    private static final String CLASS_PATH_RESOURCE = "class path resource";

    private static Environment ENVIRONMENT;

    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactoryToProcess, final Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        /* remove by xlxcc 2018-3-14 0:23:00 start*/
        /* if (LOAD_FLOG.get()) {
            loadData(props);
        }*/
        /* remove by xlxcc 2018-3-14 0:23:00 end */

        /* add by xlxcc 2018-3-14 0:23:00 start*/
        loadData(props);
        /* add by xlxcc 2018-3-14 0:23:00 end*/
    }

    /**
     * 加载数据
     * @param props 配置
     * @throws BeansException beans异常
     */
    public static void loadData(final Properties props) throws BeansException {
        CTX_PROPERTIES_MAP = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            CTX_PROPERTIES_MAP.put(keyStr, value);
        }
    }

    /**
     * 根据键获取属性值
     * @param key 键
     * @return obj值
     */
    public static Object getProperty(final String key) {
        return CTX_PROPERTIES_MAP.get(key);
    }

    /**
     * 根据键获取属性值
     * @param key 键
     * @return string值
     */
    public static String getString(final String key) {
        return (String) CTX_PROPERTIES_MAP.get(key);
    }

    /**
     * 根据所有属性，返回map
     * @return map
     */
    public static Map<String, Object> getAll() {
        return CTX_PROPERTIES_MAP;
    }

    /**
     * 属性总，是否包含某个键
     * @param key 键
     * @return boolean
     */
    public static boolean containsProperty(final String key) {
        return CTX_PROPERTIES_MAP.containsKey(key);
    }

    /**
     * 设置属性值
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public static boolean setProperty(final String key, final String value) {
        if (key == null || value == null) {
            return false;
        }

        CTX_PROPERTIES_MAP.put(key, value);

        return true;
    }

    /**
     * 根据键获取属性，如果键不存在，返回用户设定的defaultValue
     * @param key 键
     * @param defaultValue 自定义默认值
     * @return string
     */
    public static String getProperty(final String key, final String defaultValue) {
        Object value = CTX_PROPERTIES_MAP.get(key);
        return value == null ? defaultValue : (String) value;
    }

    /**
     * 根据键获取属性, 并且返回目标类型实例
     * @param key 键
     * @param targetType 目标类型
     * @param <T> 返回类型
     * @return T实例
     */
    public static <T> T getProperty(final String key, final Class<T> targetType) {
        Object value = CTX_PROPERTIES_MAP.get(key);
        if (value == null) {
            return null;
        }
        return CONVERSION_SERVICE.convert(value, targetType);
    }

    /**
     * 根据键获取属性, 如果键不存在，设置为用户设定的defaultValue, 并且返回目标类型实例
     * @param key 键
     * @param defaultValue 默认值
     * @param targetType 目标类型
     * @param <T> 返回类型
     * @return T实例
     */
    public static <T> T getProperty(final String key, final Class<T> targetType, final T defaultValue) {
        Object value = CTX_PROPERTIES_MAP.get(key);
        if (value == null) {
            return defaultValue;
        }
        return CONVERSION_SERVICE.convert(value, targetType);
    }

    /* remove by xlxcc 2018-3-14 0:23:00 start*/
    /**
     * 加载配置文件数据
     * @param event 环境变量
     */
    /* public static void loadData(final Environment event) {
        if (LOAD_FLOG.get()) {
            ConfigurableEnvironment environment = (ConfigurableEnvironment) event;

            Iterator<PropertySource<?>> iter = environment.getPropertySources().iterator();
            while (iter.hasNext()) {
                PropertySource<?> ps = iter.next();
                String name = ps.getName();
                if (name != null) {
                    if (APP_PROPERTIES.equals(name)) {
                        EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) ps;
                        Map<String, Object> data = new HashMap<String, Object>();
                        for (String key : eps.getPropertyNames()) {
                            data.put(key, eps.getProperty(key));
                        }
                        CTX_PROPERTIES_MAP = data;
                    } else if (name.startsWith(CLASS_PATH_RESOURCE)) {
                        try {
                            String propertiesName = name.substring(name.indexOf("[") + 1, name.lastIndexOf("]"));
                            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesName);
                            loadData(properties);
                        } catch (final IOException e) {
                            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
                        }
                    }
                }
            }
            LOAD_FLOG.set(false);
        }
    }*/
    /* remove by xlxcc 2018-3-14 0:23:00 end */

    /* add by xlxcc 2018-3-14 0:23:00 start*/
    /**
     * 加载配置文件数据
     * 修正BaseProperties的loadData方法，初步解决apollo配置没load到BaseProperties，导致应用从BaseProperties取不到相关配置的bug
     * @param event 环境变量
     */
    public static void loadData(final Environment event) {
        ENVIRONMENT = event;
        ConfigurableEnvironment environment = (ConfigurableEnvironment) event;

        Iterator<PropertySource<?>> iter = environment.getPropertySources().iterator();
        while (iter.hasNext()) {
            PropertySource<?> ps = iter.next();
            String name = ps.getName();
            if (name != null && ATOMIC_MAP.get(name) == null) {
                try {
                    if (name.startsWith(CLASS_PATH_RESOURCE)) {
                        try {
                            String propertiesName = name.substring(name.indexOf("[") + 1, name.lastIndexOf("]"));
                            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesName);
                            loadData(properties);
                        } catch (final IOException e) {
                            throw new SysException(SysErrorConsts.SYS_ERROR_CODE, e.getMessage(), e);
                        }
                    } else {
                        EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) ps;
                        Map<String, Object> data = new HashMap<String, Object>();
                        for (String key : eps.getPropertyNames()) {
                            data.put(key, eps.getProperty(key));
                        }
                        CTX_PROPERTIES_MAP.putAll(data);
                        ATOMIC_MAP.put(name, false);
                    }
                } catch (final Exception e) {
                    log.error("Try MapPropertySource loadProperties Because PropertiesLoaderUtils load {}' env config Exception : {}",
                            name, e.getMessage());
                    try {
                        MapPropertySource eps = (MapPropertySource) ps;
                        CTX_PROPERTIES_MAP.putAll(eps.getSource());
                        ATOMIC_MAP.put(name, false);
                    } catch (final Exception ex) {
                        log.error("Try StubPropertySource loadProperties Because MapPropertySource load {}' env config Exception : {}",
                                name, ex.getMessage());
                        // StubPropertySource eps = (StubPropertySource) ps;
                        ATOMIC_MAP.put(name, false);
                    }
                }
            }
        }
    }

    public static Environment getEnv() {
        return ENVIRONMENT;
    }
    /* add by xlxcc 2018-3-14 0:23:00 end*/

}
