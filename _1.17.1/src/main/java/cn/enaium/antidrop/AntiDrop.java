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

package cn.enaium.antidrop;

import cn.enaium.antidrop.callback.DropSelectedItemCallback;
import cn.enaium.antidrop.callback.ScreenMouseClickCallback;
import cn.enaium.antidrop.callback.impl.DropSelectedItemCallbackImpl;
import cn.enaium.antidrop.callback.impl.ScreenMouseClickCallbackImpl;
import cn.enaium.antidrop.command.ActionCommand;
import cn.enaium.antidrop.command.ListCommand;
import cn.enaium.antidrop.command.ScreenCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class AntiDrop implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("AntiDrop");
    public static final LiteralArgumentBuilder<ServerCommandSource> ROOT = literal("antidrop");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello AntiDrop world!");

        CommandRegistrationCallback.EVENT.register((dispatcher, environment) -> {
            ScreenCommand.register(dispatcher);
            ActionCommand.register(dispatcher);
            ListCommand.register(dispatcher);
        });

        DropSelectedItemCallback.EVENT.register(new DropSelectedItemCallbackImpl());
        ScreenMouseClickCallback.EVENT.register(new ScreenMouseClickCallbackImpl());

        Config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }
}
