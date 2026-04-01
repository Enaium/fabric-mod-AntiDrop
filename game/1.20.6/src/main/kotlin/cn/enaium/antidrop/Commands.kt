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
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.registry.RegistryKeys
import kotlin.jvm.optionals.getOrNull

/**
 * @author Enaium
 */
object Commands {
    @JvmStatic
    fun client() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource>, registryAccess: CommandRegistryAccess ->
            val map =
                registryAccess.getOptionalWrapper(RegistryKeys.BLOCK)
                    .map { wrapper -> wrapper.streamKeys().map { it.value.toString() } }
                    .getOrNull()?.toList() ?: emptyList()
            AntiDropConfig.items = AntiDropConfig.items.copy(options = map)
        })
    }
}