package com.example.skindemo.skin

import android.util.AttributeSet
import android.view.View
import com.example.skindemo.utils.SkinThemeUtils

class SkinAttribute {

    companion object {
        private val mAttributes = arrayOf(
            "background",
            "src",
            "textColor",
            "drawableLeft",
            "drawableTop",
            "drawableRight",
            "drawableBottom"
        )
    }

    private val mSkinViews: MutableList<SkinView> = mutableListOf()

    fun look(view: View, attrs: AttributeSet) {
        //记录属性的集合
        val mSkinPars: MutableList<Pair<String, Int>> = mutableListOf();

        val attributeCount = attrs.attributeCount
        for (index in 0 until attributeCount) {
            //获得属性名  textColor/background
            val attributeName = attrs.getAttributeName(index)
            if (mAttributes.contains(attributeName)) {
                val attributeValue = attrs.getAttributeValue(index)

                // 比如color 以#开头表示写死的颜色 不可用于换肤
                if (attributeValue.startsWith("#")) {
                    continue
                }
                var resId: Int
                if (attributeValue.startsWith("?")) {
                    // 以 ？开头的表示使用 属性
                    val attrId = attributeValue.substring(1).toInt()
                    resId = SkinThemeUtils.getResId(view.context, intArrayOf(attrId)).get(0)
                } else {
                    // 正常以 @ 开头
                    resId = Integer.parseInt(attributeValue.substring(1));
                }
                //属性键值对形式保存，Key：属性名，Value：属性值
                val pair: Pair<String, Int> = Pair(attributeName, resId)
                mSkinPars.add(pair)
            }
        }

        if (mSkinPars.isNotEmpty() || view is SkinViewSupport) {
            //把View 与 View的属性进行绑定
            val skinView = SkinView(view, mSkinPars)
            skinView.applySkin()
            //保存View集合
            mSkinViews.add(skinView)
        }
    }

    /**
     * 对所有的view中的所有的属性进行皮肤修改
     */
    fun applySkin() {
        for (mSkinView in mSkinViews) {
            mSkinView.applySkin()
        }
    }
}