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
 * 名称: MutiResponse
 * 描述: MutiResponse响应实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MutiResponse<T extends BaseResponse>  extends BasePageResponse<T>  {

    private static final long serialVersionUID = 8354687010277938091L;

    /**
     * 默认构造方法
     */
    public MutiResponse() { }

    /**
     * 构造方法
     * @param items the items
     */
    public MutiResponse(final List<T> items) {
        this(items, null, null, null, null, null);
    }

    /**
     * 构造方法
     * @param items the items
     * @param pageNo 页数
     * @param limit 条数
     * @param total 总数
     */
    public MutiResponse(final List<T> items, final Integer pageNo, final Integer limit, final Integer total) {
        this(items, pageNo, null, limit, total, null);
    }

    /**
     * 构造方法
     * @param items the items
     * @param start 开始
     * @param limit 条数
     * @param morePage  the morePage
     */
    public MutiResponse(final List<T> items, final Integer start, final Integer limit, final Boolean morePage) {
        this(items, null, start, limit, null, morePage);
    }

    /**
     * 构造方法
     * @param items the items
     * @param pageNo 页数
     * @param start 开始
     * @param limit 条数
     * @param total 总数
     * @param morePage the morePage
     */
    public MutiResponse(final List<T> items, final Integer pageNo, final Integer start, final Integer limit,
            final Integer total, final Boolean morePage) {
        super.setItems(items);
        super.setPageNo(pageNo);
        super.setMorePage(morePage);
        super.setStart(start);
        super.setLimit(limit);
        if (total == null) {
            super.setTotal(items != null ? items.size() : 0L);
        } else {
            super.setTotal(Long.valueOf(total + ""));
        }
    }
}
