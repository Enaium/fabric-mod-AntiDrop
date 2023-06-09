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

package cn.enaium.antidrop.command;

import cn.enaium.antidrop.Config;
import cn.enaium.antidrop.enums.Action;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import static cn.enaium.antidrop.AntiDrop.ROOT;

/**
 * @author Enaium
 */
public class ActionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (Action action : Action.values()) {
            dispatcher.register(ROOT.then(CommandManager.literal(action.name()).then(CommandManager.argument("item", ItemStackArgumentType.itemStack()).executes(context -> {
                final Item item = ItemStackArgumentType.getItemStackArgument(context, "item").getItem();
                final String itemName = Registry.ITEM.getId(item).toString();
                if (action == Action.ADD) {
                    Config.getModel().item.add(itemName);
                    context.getSource().sendFeedback(new TranslatableText("command.action.add", new LiteralText(itemName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(item.getDefaultStack()))))), false);
                } else if (action == Action.REMOVE) {
                    Config.getModel().item.remove(itemName);
                    context.getSource().sendFeedback(new TranslatableText("command.action.remove", new LiteralText(itemName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(item.getDefaultStack()))))), false);
                }
                return Command.SINGLE_SUCCESS;
            }))));
        }
    }
}
