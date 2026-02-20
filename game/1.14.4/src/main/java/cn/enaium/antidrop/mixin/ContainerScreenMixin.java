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
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Enaium
 */
@Mixin(ContainerScreen.class)
public abstract class ContainerScreenMixin {
    @Shadow
    @Final
    protected PlayerInventory playerInventory;

    @Inject(at = @At("HEAD"), method = "onMouseClick", cancellable = true)
    public void onMouseClick(Slot slot, int invSlot, int button, SlotActionType slotActionType, CallbackInfo ci) {
        String slotItem = null;

        if (slot != null) {
            slotItem = Registry.ITEM.getId(slot.getStack().getItem()).toString();
        }

        if (!ScreenCallbacks.ScreenMouseClickCallback.Companion.getEVENT().getInvoker().interact(slotItem, Registry.ITEM.getId(playerInventory.getCursorStack().getItem()).toString(), SlotAction.valueOf(slotActionType.name()))) {
            ci.cancel();
        }
    }
}
