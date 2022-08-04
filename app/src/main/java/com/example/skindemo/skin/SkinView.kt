package com.example.skindemo.skin

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat

data class SkinView(private val view: View, private val skinPairs: List<Pair<String, Int>>) {

    /**
     * 对一个View中的所有的属性进行修改
     */
    fun applySkin() {
        for (skinPair in skinPairs) {
            val attributeName = skinPair.first
            val resId = skinPair.second

            var left: Drawable? = null
            var top: Drawable? = null
            var right: Drawable? = null
            var bottom: Drawable? = null
            when (attributeName) {
                "background" -> {
                    //背景可能是 @color 也可能是 @drawable
                    val background = SkinResources.getInstance()?.getBackground(resId)
                    if (background is Int) {
                        view.setBackgroundColor(background as Int)
                    } else {
                        ViewCompat.setBackground(view, background as Drawable?)
                    }
                }
                "src" -> {
                    val background = SkinResources.getInstance()?.getBackground(resId)
                    if (background is Int) {
                        (view as ImageView).setImageDrawable(ColorDrawable((background as Int?)!!))
                    } else {
                        (view as ImageView).setImageDrawable(background as Drawable?)
                    }
                }
                "textColor" -> {
                    (view as TextView).setTextColor(SkinResources.getInstance()?.getColorStateList(resId))
                }
                "drawableLeft" -> {
                    left = SkinResources.getInstance()?.getDrawable(resId)
                }
                "drawableTop" -> {
                    top = SkinResources.getInstance()?.getDrawable(resId)
                }
                "drawableRight" -> {
                    right = SkinResources.getInstance()?.getDrawable(resId)
                }
                "drawableBottom" -> {
                    bottom = SkinResources.getInstance()?.getDrawable(resId)
                }
            }
            if (null != left || null != right || null != top || null != bottom) {
                (view as? TextView)?.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
            }
        }
    }
}
