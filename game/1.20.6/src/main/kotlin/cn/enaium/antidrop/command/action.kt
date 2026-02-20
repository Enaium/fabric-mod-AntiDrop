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
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.registry.Registries
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.Text
import net.minecraft.util.Formatting


/**
 * @author Enaium
 */
fun action(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess) {
    for (action in Action.entries) {
        dispatcher.register(
            ROOT.then(
                CommandManager.literal(action.name).then(
                    CommandManager.argument(
                        "item",
                        ItemStackArgumentType.itemStack(registryAccess)
                    ).executes { context ->
                        val item = ItemStackArgumentType.getItemStackArgument(context, "item")
                            .item
                        val itemName = Registries.ITEM.getId(item).toString()
                        if (action == Action.ADD) {
                            model.item.add(itemName)
                            context!!.getSource()!!.sendFeedback({
                                Text.translatable(
                                    "command.action.add",
                                    Text.literal(itemName).styled { style ->
                                        style.withColor(Formatting.AQUA)
                                            .withHoverEvent(
                                                HoverEvent(
                                                    HoverEvent.Action.SHOW_ITEM,
                                                    HoverEvent.ItemStackContent(item.defaultStack)
                                                )
                                            )
                                    }
                                )
                            }, false)
                        } else if (action == Action.REMOVE) {
                            model.item.remove(itemName)
                            context!!.getSource()!!.sendFeedback({
                                Text.translatable(
                                    "command.action.remove",
                                    Text.literal(itemName).styled { style ->
                                        style.withColor(Formatting.AQUA)
                                            .withHoverEvent(
                                                HoverEvent(
                                                    HoverEvent.Action.SHOW_ITEM,
                                                    HoverEvent.ItemStackContent(item.defaultStack)
                                                )
                                            )
                                    }
                                )
                            }, false)
                        }
                        Command.SINGLE_SUCCESS
                    }
                )
            )
        )
    }
}