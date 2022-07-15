package com.example.skindemo.manager

import android.util.AttributeSet
import android.view.View
import com.example.skindemo.utils.SkinThemeUtils
import java.util.ArrayList

class SkinAttribute {

    companion object {
        private val mAttributes = arrayOf("background", "src", "textColor", "drawableLeft", "drawableTop", "drawableRight", "drawableBottom")
    }


    public fun look(view: View, attrs: AttributeSet) {

        val mSkinPars: MutableList<Pair<String, Int>> = mutableListOf();


        val attributeCount = attrs.attributeCount
        for (index in 0 until attributeCount) {
            val attributeName = attrs.getAttributeName(index)
            if (mAttributes.contains(attributeName)) {
                val attributeValue = attrs.getAttributeValue(index)

                // 比如color 以#开头表示写死的颜色 不可用于换肤
                if (attributeValue.startsWith("#")) {
                    continue
                }
                var resId: Int

                if (attributeValue.startsWith("?")) {
                    val attrId = attributeValue.substring(1).toInt()
                    resId = SkinThemeUtils.getResId(view.context, intArrayOf(attrId)).get(0)
                } else {
                    // 正常以 @ 开头
                    resId = Integer.parseInt(attributeValue.substring(1));
                }

                val pair: Pair<String, Int>  = Pair(attributeName, resId)
                mSkinPars.add(pair)
            }
        }
    }
}