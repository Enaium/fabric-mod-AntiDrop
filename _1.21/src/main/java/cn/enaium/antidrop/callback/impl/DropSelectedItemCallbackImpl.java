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
import cn.enaium.antidrop.callback.DropSelectedItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;

/**
 * @author Enaium
 */
public class DropSelectedItemCallbackImpl implements DropSelectedItemCallback {
    @Override
    public ActionResult interact(ItemStack mainHandStack) {
        if (Config.getModel().item.contains(Registries.ITEM.getId(mainHandStack.getItem()).toString())) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}
