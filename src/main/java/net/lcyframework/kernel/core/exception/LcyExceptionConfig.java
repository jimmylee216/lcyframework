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

package net.lcyframework.kernel.core.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <pre>
 * 名称: YtoExceptionConfig
 * 描述: 异常配置
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class LcyExceptionConfig {

    private static final String ERROR_CONFIG_FILE = "/LcyException.json";
    private static Map<Integer, LcyExceptionInfo> V_MAP = new TreeMap<>();

    /**
     * 装载YtoExceptionConfig
     * @return V_MAP
     */
    @Bean
    public Map<Integer, LcyExceptionInfo> execBean() {
        read(ERROR_CONFIG_FILE);
        return V_MAP;
    }

    private synchronized boolean read(final String file) {
        StringBuilder result = new StringBuilder();
        V_MAP = new TreeMap<>();
        try {
            InputStream inputStream = getClass().getResourceAsStream(file);
            if (null == inputStream) {
                return false;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
            List<LcyExceptionInfo> items = json2Reference(result.toString(), new TypeReference<List<LcyExceptionInfo>>() { });
            items.stream().forEach(n -> V_MAP.put(n.getErrorCode(), n));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private static <T> T json2Reference(final String json, final TypeReference<T> reference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, reference);
    }

    /**
     * 获取错误实体
     * @param code 错误代码
     * @return LcyExceptionInfo 实体
     */
    public static LcyExceptionInfo getError(final Integer code) {
        if (V_MAP.containsKey(code)) {
            return V_MAP.get(code);
        }
        return new LcyExceptionInfo(code);
    }
}
