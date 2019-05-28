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

package net.lcyframework.plugin.zk;

import org.apache.curator.framework.CuratorFramework;

/**
 * <pre>
 * 名称: ZookeeperCallback
 * 描述: zk回调接口
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
public interface ZookeeperCallback<T> {

    /**
     * 使用zk
     * @param zkClient zk客户端
     * @return T
     * @throws Exception 异常
     */
    T doInZookeeper(CuratorFramework zkClient) throws Exception;
}
