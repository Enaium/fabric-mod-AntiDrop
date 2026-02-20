package cn.enaium.antidrop.event

import cn.enaium.antidrop.common.SlotAction

/**
 * @author Enaium
 */
class ScreenCallbacks {
    fun interface DropSelectedItemCallback {

        companion object {
            val EVENT = Event { listeners: List<DropSelectedItemCallback> ->
                DropSelectedItemCallback { item ->
                    var result = false
                    for (listener in listeners) {
                        result = listener.interact(item)
                    }
                    return@DropSelectedItemCallback result
                }
            }
        }


        fun interact(item: String): Boolean
    }

    fun interface ScreenMouseClickCallback {

        companion object {
            val EVENT = Event { listeners: List<ScreenMouseClickCallback> ->
                ScreenMouseClickCallback { slot, cursor, slotActionType ->
                    var result = false
                    for (listener in listeners) {
                        result = listener.interact(slot, cursor, slotActionType)
                    }
                    return@ScreenMouseClickCallback result
                }
            }
        }


        fun interact(slot: String?, cursor: String, slotAction: SlotAction): Boolean
    }
}