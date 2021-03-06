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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 描述：BaseComShardingRequest.
 * 用户、分库分表基类
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseComShardingRequest<SKEY extends Serializable & Comparable<SKEY>, UKEY extends Serializable & Comparable<UKEY>>
        extends BaseUserRequest<UKEY> {

    private static final long serialVersionUID = -9199086521387431997L;

    /**
     * 分表基准值
     */
    private SKEY sId;

    /**
     * 读库
     */
    private String read;

    /**
     * 写库
     */
    private String write;

    /**
     * 分表表名
     */
    private String tableName;

}
