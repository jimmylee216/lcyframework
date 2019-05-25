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
 * 名称: TimelineRequest
 * 描述: timeline请求实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TimelineRequest<K extends Serializable & Comparable<K>> extends BasePageRequest {

    private static final long serialVersionUID = 3117490146593603179L;

    private K timeline;
}
