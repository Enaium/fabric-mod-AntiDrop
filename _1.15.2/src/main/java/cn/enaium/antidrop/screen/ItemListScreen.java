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

package cn.enaium.antidrop.screen;

import cn.enaium.antidrop.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

/**
 * @author Enaium
 */
public class ItemListScreen extends Screen {

    private ListWidget<ItemListWidget.Entry> entryListWidget;
    private ButtonWidget removeButton;
    private ButtonWidget addButton;


    public ItemListScreen() {
        super(new LiteralText(""));
    }

    @Override
    protected void init() {
        entryListWidget = new ListWidget<>(minecraft, width, height, 50, height - 50, 24);

        Config.getModel().item.forEach(it -> entryListWidget.addEntry(new ItemListWidget.Entry(it)));

        addButton = new ButtonWidget(width / 2 - 100, 15, 200, 20, new TranslatableText("button.add").getString(), (e) -> {
            MinecraftClient.getInstance().openScreen(new ItemListAllScreen());
        });

        removeButton = new ButtonWidget(width / 2 - 100, height - 35, 200, 20, new TranslatableText("button.remove").getString(), (e) -> {
            if (entryListWidget.getSelected() != null) {
                Config.getModel().item.remove(entryListWidget.getSelected().name);
                Config.save();
                entryListWidget.removeEntry(entryListWidget.getSelected());
            }
        });

        addButton(addButton);
        addButton(removeButton);
        super.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        addButton.render(mouseX, mouseY, delta);
        removeButton.render(mouseX, mouseY, delta);
        entryListWidget.render(mouseX, mouseY, delta);
        removeButton.active = entryListWidget.getSelected() != null;
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        entryListWidget.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
