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

package cn.enaium.antidrop.callback.impl;

import cn.enaium.antidrop.Config;
import cn.enaium.antidrop.callback.ScreenMouseClickCallback;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

/**
 * @author Enaium
 */
public class ScreenMouseClickCallbackImpl implements ScreenMouseClickCallback {
    @Override
    public ActionResult interact(@Nullable Slot slot, ItemStack cursorStack, SlotActionType slotActionType) {
        if (slot != null && Config.getModel().item.contains(Registry.ITEM.getId(slot.getStack().getItem()).toString()) && slotActionType == SlotActionType.THROW) {
            return ActionResult.FAIL;
        }
        if (slot == null && Config.getModel().item.contains(Registry.ITEM.getId(cursorStack.getItem()).toString()) && slotActionType == SlotActionType.PICKUP) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}
