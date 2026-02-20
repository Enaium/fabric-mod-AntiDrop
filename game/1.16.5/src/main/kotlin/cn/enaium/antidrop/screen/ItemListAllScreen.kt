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
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.Registry
import java.util.stream.Collectors


/**
 * @author Enaium
 */
class ItemListAllScreen : Screen(LiteralText("")) {
    private var entryListWidget: ListWidget<ItemListWidget.Entry>? = null
    private var textFieldWidget: TextFieldWidget? = null
    private var addButton: ButtonWidget? = null

    public override fun init() {
        entryListWidget = ListWidget<ItemListWidget.Entry>(client, width, height, 50, height - 50, 24)
        textFieldWidget =
            TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, LiteralText(""))
        addButton = ButtonWidget(
            width / 2 - 100,
            height - 35,
            200,
            20,
            TranslatableText("button.add")
        ) {
            val selectedOrNull = entryListWidget!!.getSelected()
            if (selectedOrNull != null) {
                model.item.add(selectedOrNull.name)
                Config.save()
                MinecraftClient.getInstance().openScreen(ItemListScreen())
            }
        }
        get().forEach { entry -> entryListWidget!!.addEntry(entry) }
        textFieldWidget?.setChangedListener {
            entryListWidget?.replaceEntries(get())
            entryListWidget?.selected = null
        }
        addChild(entryListWidget)
        addChild(textFieldWidget)
        addButton(addButton)
        super.init()
    }

    fun get(): List<ItemListWidget.Entry> {
        return Registry.ITEM.stream().filter {
            if (textFieldWidget?.text != "") {
                return@filter (it.asItem().toString().contains(textFieldWidget!!.text) || TranslatableText(
                    it.asItem().translationKey
                ).string.contains(textFieldWidget!!.text))
            } else {
                return@filter true
            }
        }.map { ItemListWidget.Entry(Registry.ITEM.getId(it.asItem()).toString()) }.collect(Collectors.toList())
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        addButton?.active = entryListWidget?.getSelected() != null
        entryListWidget?.render(matrices, mouseX, mouseY, delta)
        textFieldWidget?.render(matrices, mouseX, mouseY, delta)
        super.render(matrices, mouseX, mouseY, delta)
    }
}
