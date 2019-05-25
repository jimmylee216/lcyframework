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

import java.util.concurrent.locks.Lock;

/**
 * Strategy for maintaining a registry of shared locks.
 * @author Jimmy Li
 * @since 1.0.0
 */
@FunctionalInterface
public interface LockRegistry {

    /**
     * Obtains the locks associated with the parameter object.
     * @param lockKey The object with which the locks is associated.
     * @return The associated locks.
     */
    Lock obtain(Object lockKey);

}
