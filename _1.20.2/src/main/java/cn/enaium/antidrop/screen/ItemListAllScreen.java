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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class ItemListAllScreen extends Screen {

    private ListWidget<ItemListWidget.Entry> entryListWidget;
    private TextFieldWidget textFieldWidget;
    private ButtonWidget addButton;

    public ItemListAllScreen() {
        super(Text.empty());
    }

    @Override
    public void init() {
        entryListWidget = new ListWidget<>(client, width, height, 50, height - 50, 24);
        textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, Text.empty());
        addButton = ButtonWidget.builder(Text.translatable("button.add"), e -> {
            ItemListWidget.Entry selectedOrNull = entryListWidget.getSelectedOrNull();
            if (selectedOrNull != null) {
                Config.getModel().item.add(selectedOrNull.name);
                Config.save();
                MinecraftClient.getInstance().setScreen(new ItemListScreen());
            }
        }).dimensions(width / 2 - 100, height - 35, 200, 20).build();
        get().forEach(entryListWidget::addEntry);
        textFieldWidget.setChangedListener(s -> {
            entryListWidget.replaceEntries(get());
            entryListWidget.setSelected(null);
        });
        addDrawableChild(entryListWidget);
        addDrawableChild(textFieldWidget);
        addDrawableChild(addButton);
        super.init();
    }

    public List<ItemListWidget.Entry> get() {
        return Registries.ITEM.stream().filter(it -> {
            if (!textFieldWidget.getText().equals("")) {
                return (it.asItem().toString().contains(textFieldWidget.getText()) || Text.translatable(it.asItem().getTranslationKey()).getString().contains(textFieldWidget.getText()));
            } else {
                return true;
            }
        }).map(it -> new ItemListWidget.Entry(Registries.ITEM.getId(it.asItem()).toString())).collect(Collectors.toList());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        addButton.active = entryListWidget.getSelectedOrNull() != null;
        super.render(context, mouseX, mouseY, delta);
    }
}
