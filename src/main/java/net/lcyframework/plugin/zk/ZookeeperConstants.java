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

/**
 * <pre>
 * 名称: ZookeeperConstants
 * 描述: zookeeper 常量类
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
public final class ZookeeperConstants {

    /** 配置 */
    public static final String CONFIGURATION = "conf";
    /** 锁 */
    public static final String LOCK = "lock";
    /** 服务状态 */
    public static final String NAME_SERVICE = "service-status";
    /** 文件分隔符 */
    public static final String FILE_SEPARATOR = "/";
    /** 文件 */
    public static final String FILE_COLON = ":";
    /** 休眠时间 */
    public static final int SLEEP_TIME_MS = 1000;
    /** 重试次数 */
    public static final int MAX_RETRIES = 3;
    /** 连接超时 */
    public static final int CONNECTION_TIMEOUT_MS = 3000;
    /** 会话超时 */
    public static final int SESSION_TIMEOUT_MS = 5000;

    private ZookeeperConstants() {
    }

}
