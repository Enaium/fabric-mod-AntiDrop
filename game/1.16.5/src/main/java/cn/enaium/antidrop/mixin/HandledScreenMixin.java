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

import cn.enaium.antidrop.Config;
import cn.enaium.antidrop.common.SlotAction;
import cn.enaium.antidrop.event.ScreenCallbacks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
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
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow
    @Final
    protected PlayerInventory playerInventory;

    @Inject(at = @At("HEAD"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", cancellable = true)
    public void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        String slotItem = null;

        if (slot != null) {
            slotItem = Registry.ITEM.getId(slot.getStack().getItem()).toString();
        }

        if (!ScreenCallbacks.ScreenMouseClickCallback.Companion.getEVENT().getInvoker().interact(slotItem, Registry.ITEM.getId(playerInventory.getCursorStack().getItem()).toString(), SlotAction.valueOf(actionType.name()))) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onClose", cancellable = true)
    public void onClose(CallbackInfo ci) {
        if (Config.INSTANCE.getModel().getItem().contains(Registry.ITEM.getId(playerInventory.getCursorStack().getItem()).toString())) {
            ci.cancel();
        }
    }
}
