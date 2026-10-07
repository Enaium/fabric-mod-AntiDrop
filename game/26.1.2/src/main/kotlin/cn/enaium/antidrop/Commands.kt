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

import cn.enaium.antidrop.config.AntiDropConfig
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.core.registries.Registries
import kotlin.jvm.optionals.getOrNull

/**
 * @author Enaium
 */
object Commands {
    @JvmStatic
    fun client() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { _, buildContext ->
            val map = buildContext.lookup(Registries.ITEM)
                .map { it -> it.listElementIds().map { it.identifier().toString() } }.getOrNull()?.toList()
                ?: emptyList()
            AntiDropConfig.items = AntiDropConfig.items.copy(options = map)
        })
    }
}