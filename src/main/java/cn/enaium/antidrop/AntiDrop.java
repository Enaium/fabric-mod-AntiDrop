/**
 * Copyright (C) 2022 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.antidrop;

import cn.enaium.antidrop.mixin.IKeyBindingMixin;
import cn.enaium.antidrop.screen.ItemListScreen;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AntiDrop implements ModInitializer {

    public static final EventBus eventBus = new EventBus();

    public static final Logger LOGGER = LoggerFactory.getLogger("AntiDrop");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello AntiDrop world!");
        eventBus.register(new Sub());

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("antidrop").executes(context -> {
                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new ItemListScreen()));
                return Command.SINGLE_SUCCESS;
            }));
        });
        load();
        Runtime.getRuntime().addShutdownHook(new Thread(AntiDrop::save));
    }

    private static final File config = new File(MinecraftClient.getInstance().runDirectory, "AntiDrop.json");
    public static final List<String> list = new ArrayList<>();

    public static void load() {
        if (config.exists()) {
            try {
                list.addAll(new Gson().fromJson(FileUtils.readFileToString(config, StandardCharsets.UTF_8), new TypeToken<List<String>>() {
                }.getType()));
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            FileUtils.write(config, new Gson().toJson(list), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static class Sub {

        private ItemStack itemStack;

        @Subscribe
        private void renderTooltipEvent(RenderTooltipEvent event) {
            itemStack = event.itemStack;
        }

        @Subscribe
        private void key(KeyEvent event) {
            MinecraftClient mc = MinecraftClient.getInstance();

            if (event.key != (((IKeyBindingMixin) mc.options.keyDrop)).getBoundKey().getCode())
                return;

            if (itemStack != null && mc.currentScreen instanceof InventoryScreen) {
                if (AntiDrop.list.contains(
                        Registry.ITEM.getId(
                                itemStack.getItem()
                        ).toString()
                )
                ) {
                    event.cancel = true;
                }
            }

            if (mc.player != null) {
                var slot = mc.player.getInventory().selectedSlot;
                if (slot >= 36) slot -= 36;
                final var stack = mc.player.getInventory().getStack(slot);
                if (!stack.isEmpty()) {
                    if (AntiDrop.list.contains(
                            Registry.ITEM.getId(
                                    stack.getItem()
                            ).toString()
                    )
                    ) {
                        event.cancel = true;
                    }
                }
            }
        }
    }


    public static class KeyEvent {
        private final int key;
        private boolean cancel;

        public KeyEvent(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public boolean isCancel() {
            return cancel;
        }
    }

    public record RenderTooltipEvent(ItemStack itemStack) {
    }
}
