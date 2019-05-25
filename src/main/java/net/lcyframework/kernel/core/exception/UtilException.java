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

import java.text.MessageFormat;

/**
 * <pre>
 * 名称: UtilException
 * 描述: 工具类运行时异常
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class UtilException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    /**
     * 构造方法
     * @param e 栈
     */
    public UtilException(final Throwable e) {
        super(e);
    }

    /**
     * 构造方法
     * @param message 消息
     */
    public UtilException(final String message) {
        super(message);
    }

    /**
     * 构造方法
     * @param messageTemplate 消息模板
     * @param params 参数
     */
    public UtilException(final String messageTemplate, final Object... params) {
        super(MessageFormat.format(messageTemplate, params));
    }

    /**
     * 构造方法
     * @param message 消息
     * @param throwable 栈
     */
    public UtilException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * 构造方法
     * @param messageTemplate 消息模板
     * @param throwable 栈
     * @param params 参数
     */
    public UtilException(final Throwable throwable, final String messageTemplate, final Object... params) {
        super(MessageFormat.format(messageTemplate, params), throwable);
    }

}
