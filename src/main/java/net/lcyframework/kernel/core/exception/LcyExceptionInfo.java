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

import lombok.Data;

/**
 * <pre>
 * 名称: ExceptionInfo
 * 描述: 异常实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
public class LcyExceptionInfo {

    /**
     * 错误代码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 错误内容
     */
    private String domain;

    /**
     * 构造方法
     */
    public LcyExceptionInfo() {
    }

    /**
     * 构造方法
     * @param code 错误代码
     */
    public LcyExceptionInfo(final Integer code) {
        this.errorCode = code;
        this.msg = "[未定义]";
    }
}
