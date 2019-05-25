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
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <pre>
 * 分页响应实体基类
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BasePageResponse<T extends BaseResponse> implements BaseResponse {

    private static final long serialVersionUID = -3516742705251570389L;

    /**
     * 记录总数
     */
    private Long total;

    /**
     * 当前页数据集合
     */
    private List<T> items;

    /**
     * 请求页码
     */
    private Integer pageNo;

    /**
     * 请求页面大小
     */
    private Integer limit;

    /**
     * 是否有下一页
     */
    private Boolean morePage;

    /**
     * 页码数
     */
    private Integer pages;

    /**
     * 请求起始索引
     */
    private Integer start;
    //private String orderby;
}
