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

package cn.enaium.antidrop

import cn.enaium.antidrop.event.CommandCallbacks
import cn.enaium.antidrop.event.ScreenCallbacks
import cn.enaium.antidrop.event.impl.ActionCallbackImpl
import cn.enaium.antidrop.event.impl.DropSelectedItemCallbackImpl
import cn.enaium.antidrop.event.impl.ScreenMouseClickCallbackImpl

/**
 * @author Enaium
 */
object AntiDrop {
    @JvmStatic
    fun client() {
        ScreenCallbacks.DropSelectedItemCallback.EVENT.register(DropSelectedItemCallbackImpl())
        ScreenCallbacks.ScreenMouseClickCallback.EVENT.register(ScreenMouseClickCallbackImpl())
        CommandCallbacks.ActionCallback.EVENT.register(ActionCallbackImpl())

        Config.load()
        Runtime.getRuntime().addShutdownHook(Thread(Config::save))
    }
}