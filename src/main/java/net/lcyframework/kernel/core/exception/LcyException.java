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

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;

import java.util.function.Consumer;

/**
 * <pre>
 * 名称: LcyException
 * 描述: 异常实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public class LcyException extends SysException {

    /**** region  通用异常常量定义 ****/
    /**
     * 未知异常
     */
    public static final int NOT_KNOW = -40000;
    /**
     * 参数格式错误
     */
    public static final int INVALID_ARGUMENT = -40001;
    /**
     * 参数缺失
     */
    public static final int MISSING_ARGUMENT = -40002;

    /**
     * 服务器配置错误
     */
    public static final int CONFIGURATION_ERROR = -50001;

    /**
     * 一般服务器错误
     */
    public static final int ERROR_SERVER = -50000;
    /**** endregion  通用异常常量定义 ****/

    private static final long serialVersionUID = 8L;

    /**
     * 构造方法
     * @param status 状态码
     * @param msg    消息
     */
    public LcyException(final Integer status, final String msg) {
        super(status, msg);
    }

    /**
     * 构造方法
     * @param status 状态码
     */
    public LcyException(final Integer status) {
        this(status, null);
    }

    @Override
    public int getCode() {
        return super.getCode();
    }

    /**
     * 统一转换异常消息
     *
     * @param ex 异常
     * @param funException 方法异常
     * @return sysException
     */
    public static SysException exportException(final Exception ex, final Consumer<Exception> funException) {
        SysException exception = null;
        Boolean flag = false;

        if (ex instanceof NumberFormatException || ex instanceof com.alibaba.fastjson.JSONException
                || ex instanceof org.springframework.validation.BindException) {
            exception = new SysException(INVALID_ARGUMENT, "参数错误~~~");
            log.debug("", ex);
        } else if (ex instanceof SysException || ex instanceof AppException || ex instanceof LcyException) {
            if (ex instanceof SysException) {
                SysException sysEx = (SysException) ex;
                exception = new SysException(sysEx.getCode(), sysEx.getMessage());
            } else if (ex instanceof AppException || ex instanceof LcyException) {
                if (ex instanceof LcyException) {
                    LcyException appEx = (LcyException) ex;
                    exception = new SysException(appEx.getCode(),
                            StringUtils.isEmpty(appEx.getMessage())
                                    ? LcyExceptionConfig.getError(appEx.getCode()).getMsg() : appEx.getMessage(),
                            appEx.getReturnObj());
                } else {
                    AppException appEx = (AppException) ex;
                    exception = new SysException(appEx.getCode(), appEx.getMessage(), appEx.getReturnObj());
                }
            }
        } else {
            exception = new SysException(NOT_KNOW, String.format("[系统发生未知错误] - %s", ex.getMessage()));
            log.error("", ex);
            flag = true;
        }

        if (flag && funException != null && !(ex instanceof SysException || ex instanceof AppException)) {
            funException.accept(ex);
        }

        return exception;
    }
}
