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

package cn.enaium.antidrop.mixin;

import cn.enaium.antidrop.common.SlotAction;
import cn.enaium.antidrop.event.ScreenCallbacks;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Enaium
 */
@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> {

    @Shadow
    public abstract T getMenu();

    @Inject(at = @At("HEAD"), method = "slotClicked", cancellable = true)
    public void onMouseClick(Slot slot, int slotId, int buttonNum, ContainerInput containerInput, CallbackInfo ci) {
        String slotItem = null;

        if (slot != null) {
            slotItem = BuiltInRegistries.ITEM.getKey(slot.getItem().getItem()).toString();
        }

        if (!ScreenCallbacks.ScreenMouseClickCallback.Companion.getEVENT().getInvoker().interact(slotItem, BuiltInRegistries.ITEM.getKey(getMenu().getCarried().getItem()).toString(), SlotAction.valueOf(containerInput.name()))) {
            ci.cancel();
        }
    }
}
