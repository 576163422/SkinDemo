package com.example.skindemo.manager

import android.util.AttributeSet
import android.view.View

class SkinAttribute {

    companion object {
        private val mAttributes = arrayOf("background", "src", "textColor", "drawableLeft", "drawableTop", "drawableRight", "drawableBottom")
    }


    public fun look(view: View, attrs: AttributeSet) {
        val attributeCount = attrs.attributeCount

        for (index in 0 until attributeCount) {
            val attributeName = attrs.getAttributeName(index)
            if (mAttributes.contains(attributeName)) {
                val attributeValue = attrs.getAttributeValue(index)

                // 比如color 以#开头表示写死的颜色 不可用于换肤
                if (attributeValue.startsWith("#")) {
                    continue
                }
                if (attributeValue.startsWith("?")) {

                } else {

                }
            }
        }
    }
}