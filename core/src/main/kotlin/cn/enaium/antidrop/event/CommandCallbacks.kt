/*
 * Copyright 2026 Enaium
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

package cn.enaium.antidrop.event

import cn.enaium.antidrop.common.Action

/**
 * @author Enaium
 */
class CommandCallbacks {
    fun interface ActionCallback {

        companion object {
            val EVENT = Event { listeners: List<ActionCallback> ->
                ActionCallback { action, item ->
                    for (listener in listeners) {
                        listener.execute(action, item)
                    }
                }
            }
        }

        fun execute(action: Action, item: String)
    }
}