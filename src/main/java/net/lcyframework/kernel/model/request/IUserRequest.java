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

package net.lcyframework.kernel.model.request;

import java.io.Serializable;

/**
 * <pre>
 * 名称: IUserRequest
 * 描述: ${DESCRIPTION}
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public interface IUserRequest<T extends Comparable<T>> extends Serializable {

    /**
     * 获取用户id
     * @return T
     */
    T getUserId();

    /**
     * 设置用户id
     * @param userId 用户id
     */
    void setUserId(T userId);

    /**
     * 获取用户名
     * @return userName
     */
    String getUserName();

    /**
     * 设置用户名
     * @param userName 用户名
     */
    void setUserName(String userName);
}
