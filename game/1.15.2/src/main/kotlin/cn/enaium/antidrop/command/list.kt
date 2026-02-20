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
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
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
                var previous: Text? = null
                for (item in model.item) {
                    val itemText: Text = LiteralText(item).styled { style ->
                        style.setColor(Formatting.AQUA).hoverEvent = HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            TranslatableText(Registry.ITEM.get(Identifier(item)).translationKey)
                        )
                    }
                    if (previous == null) {
                        previous = itemText
                    } else {
                        previous.append(LiteralText(", ").styled { style -> style.color = Formatting.RED })
                        previous.append(itemText)
                    }
                }

                if (previous != null) {
                    context.getSource().sendFeedback(previous, false)
                }
                return@executes Command.SINGLE_SUCCESS
            }
        )
    )
}