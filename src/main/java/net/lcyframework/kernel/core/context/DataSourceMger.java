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

package net.lcyframework.kernel.core.context;

/**
 * <pre>
 * 名称: DataSourceMger
 * 描述: 数据源上下文
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public interface DataSourceMger {

    /**
     * 设置数据源名字
     * @param name 数据源名字
     */
    void setDataSource(String name);

    /**
     * 设置数据源类型
     * @param type 数据源类型
     */
    void setDataType(String type);

    /**
     * 获取数据源名字
     * @return 数据源
     */
    String getDataSource();

    /**
     * 获取数据源类型
     * @return 数据源类型
     */
    String getDataType();

    /**
     * 判断是否包含数据源
     * @param dataSourceId 数据源id
     * @return boolean
     */
    boolean containsDataSource(String dataSourceId);

    /**
     * 清空数据源
     */
    void clearDataSource();
}
