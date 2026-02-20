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
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.LiteralText
import net.minecraft.text.MutableText
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * @author Enaium
 */
fun list(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("list").executes { context ->
                var previous: MutableText? = null
                for (item in model.item) {
                    val itemText = LiteralText(item).styled { style ->
                        style.withColor(Formatting.AQUA)
                            .withHoverEvent(
                                HoverEvent(
                                    HoverEvent.Action.SHOW_ITEM,
                                    HoverEvent.ItemStackContent(
                                        Registry.ITEM.get(Identifier(item)).defaultStack
                                    )
                                )
                            )
                    }
                    if (previous == null) {
                        previous = itemText
                    } else {
                        previous.append(
                            LiteralText(", ")
                                .styled { style -> style.withColor(Formatting.RED) }
                        )
                        previous.append(itemText)
                    }
                }

                val finalPrevious = previous
                if (finalPrevious != null) {
                    context.getSource().sendFeedback(finalPrevious, false)
                }
                Command.SINGLE_SUCCESS
            }
        )
    )
}