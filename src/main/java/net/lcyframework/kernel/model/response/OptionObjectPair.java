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
package net.lcyframework.kernel.model.response;

import lombok.Data;

/**
 * <pre>
 * 名称: OptionObjectPair
 *
 * </pre>
 *
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
public class OptionObjectPair {
    private String key;
    private String value;
    private String extend;

    /**
     * 空参构造器
     */
    public OptionObjectPair() {
    }

    /**
     * 构造器
     *
     * @param key   key
     * @param value 值
     */
    public OptionObjectPair(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 构造器
     *
     * @param key    key
     * @param value  值
     * @param extend 扩展
     */
    public OptionObjectPair(final String key, final String value, final String extend) {
        this.key = key;
        this.value = value;
        this.extend = extend;
    }
}
