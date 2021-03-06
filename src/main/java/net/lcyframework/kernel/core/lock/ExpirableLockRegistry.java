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

package net.lcyframework.kernel.core.lock;

/**
 * A {@link LockRegistry} implementing this interface supports the removal of aged locks
 * that are not currently locked.
 * @author Jimmy Li
 * @since 1.0.0
 */
public interface ExpirableLockRegistry extends LockRegistry {

    /**
     * 本地锁超时时间
     * 默认2小时
     */
    long DEFAULT_EXPIRE_UNUSED_OLDER_THEN_TIME = 1000 * 60 * 60 * 2;

    /**
     * 获取默认本地锁超时失效时间
     * @return 本地锁超时失效时间
     */
    default long getExpireUnusedOlderThanTime() {
        return DEFAULT_EXPIRE_UNUSED_OLDER_THEN_TIME;
    }

    /**
     * Remove locks last acquired more than 'age' ago that are not currently locked.
     * @param age the time since the locks was last obtained.
     * @throws IllegalStateException if the registry configuration does not support this feature.
     */
    void expireUnusedOlderThan(long age);

}
