/*
 * Copyright 2022 Enaium
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
package cn.enaium.antidrop.command

import cn.enaium.antidrop.Config.model
import cn.enaium.antidrop.ROOT
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.Identifier
import net.minecraft.world.item.ItemStackTemplate
import kotlin.jvm.optionals.getOrNull

/**
 * @author Enaium
 */
fun list(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
    dispatcher.register(
        ROOT.then(
            ClientCommands.literal("list").executes { context ->
                var previous: MutableComponent? = null
                for (item in model.item) {
                    BuiltInRegistries.ITEM.get(Identifier.parse(item)).getOrNull()?.value()?.defaultInstance?.also {
                        val itemText = Component.literal(item).withStyle { style ->
                            style.withColor(ChatFormatting.AQUA)
                                .withHoverEvent(HoverEvent.ShowItem(ItemStackTemplate.fromNonEmptyStack(it)))
                        }
                        if (previous == null) {
                            previous = itemText
                        } else {
                            previous.append(
                                Component.literal(", ")
                                    .withStyle { style -> style.withColor(ChatFormatting.RED) }
                            )
                            previous.append(itemText)
                        }
                    }
                }

                val finalPrevious = previous
                if (finalPrevious != null) {
                    context.getSource().sendFeedback(finalPrevious)
                }
                Command.SINGLE_SUCCESS
            }
        )
    )
}