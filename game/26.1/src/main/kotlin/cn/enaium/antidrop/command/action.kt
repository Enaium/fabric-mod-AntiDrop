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
package cn.enaium.antidrop.command

import cn.enaium.antidrop.Config.model
import cn.enaium.antidrop.ROOT
import cn.enaium.antidrop.common.Action
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.world.item.ItemStackTemplate

/**
 * @author Enaium
 */
fun action(dispatcher: CommandDispatcher<FabricClientCommandSource>, buildContext: CommandBuildContext) {
    for (action in Action.entries) {
        dispatcher.register(
            ROOT.then(
                ClientCommands.literal(action.name).then(
                    ClientCommands.argument(
                        "item",
                        ItemArgument.item(buildContext)
                    ).executes { context ->
                        val item = ItemArgument.getItem(context, "item").item
                        val itemName = item.registeredName
                        if (action == Action.ADD) {
                            model.item.add(itemName)
                            context.getSource().sendFeedback(
                                Component.translatable(
                                    "command.action.add",
                                    Component.literal(itemName).withStyle { style ->
                                        style.withColor(ChatFormatting.AQUA)
                                            .withHoverEvent(HoverEvent.ShowItem(ItemStackTemplate.fromNonEmptyStack(item.value().defaultInstance)))
                                    }
                                )
                            )
                        } else if (action == Action.REMOVE) {
                            model.item.remove(itemName)
                            context.getSource().sendFeedback(
                                Component.translatable(
                                    "command.action.remove",
                                    Component.literal(itemName).withStyle { style ->
                                        style.withColor(ChatFormatting.AQUA)
                                            .withHoverEvent(HoverEvent.ShowItem(ItemStackTemplate.fromNonEmptyStack(item.value().defaultInstance)))
                                    }
                                ))
                        }
                        Command.SINGLE_SUCCESS
                    }
                )
            )
        )
    }
}