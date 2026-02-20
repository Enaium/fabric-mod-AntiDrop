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
package cn.enaium.antidrop.screen

import cn.enaium.antidrop.Config
import cn.enaium.antidrop.Config.model
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text

/**
 * @author Enaium
 */
class ItemListScreen : Screen(Text.empty()) {
    private var entryListWidget: ListWidget<ItemListWidget.Entry>? = null
    private var removeButton: ButtonWidget? = null

    override fun init() {
        entryListWidget = ListWidget(client, width, height - 100, 50, 24)

        model.item.forEach { entryListWidget?.addEntry(ItemListWidget.Entry(it)) }

        val addButton =
            ButtonWidget.builder(Text.translatable("button.add")) {
                MinecraftClient.getInstance().setScreen(
                    ItemListAllScreen()
                )
            }.dimensions(width / 2 - 100, 15, 200, 20).build()
        removeButton =
            ButtonWidget.builder(Text.translatable("button.remove")) {
                if (entryListWidget?.getSelectedOrNull() != null) {
                    model.item.remove(entryListWidget!!.getSelectedOrNull()!!.name)
                    Config.save()
                    entryListWidget?.removeEntry(entryListWidget?.getSelectedOrNull())
                }
            }.dimensions(width / 2 - 100, height - 35, 200, 20).build()
        addDrawableChild(entryListWidget)
        addDrawableChild(addButton)
        addDrawableChild(removeButton)
        super.init()
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        removeButton?.active = entryListWidget?.getSelectedOrNull() != null
        super.render(context, mouseX, mouseY, delta)
    }
}
