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

package cn.enaium.antidrop.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;

/**
 * @author Enaium
 */
public interface ScreenMouseClickCallback {
    Event<ScreenMouseClickCallback> EVENT = EventFactory.createArrayBacked(ScreenMouseClickCallback.class, listeners -> (slot, cursorStack, slotActionType) -> {
        for (ScreenMouseClickCallback listener : listeners) {
            ActionResult result = listener.interact(slot, cursorStack, slotActionType);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult interact(@Nullable Slot slot, ItemStack cursorStack, SlotActionType slotActionType);
}
