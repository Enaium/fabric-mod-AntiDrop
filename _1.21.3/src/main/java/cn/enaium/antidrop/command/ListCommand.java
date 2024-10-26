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
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static cn.enaium.antidrop.AntiDrop.ROOT;

/**
 * @author Enaium
 */
public class ListCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("list").executes(context -> {

            MutableText previous = null;
            for (String item : Config.getModel().item) {
                final MutableText itemText = Text.literal(item).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(Registries.ITEM.get(Identifier.of(item)).getDefaultStack()))));
                if (previous == null) {
                    previous = itemText;
                } else {
                    previous.append(Text.literal(", ").styled(style -> style.withColor(Formatting.RED)));
                    previous.append(itemText);
                }
            }

            MutableText finalPrevious = previous;
            if (finalPrevious != null) {
                context.getSource().sendFeedback(() -> finalPrevious, false);
            }
            return Command.SINGLE_SUCCESS;
        })));
    }
}
