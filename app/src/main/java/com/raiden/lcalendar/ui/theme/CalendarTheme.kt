package com.raiden.lcalendar.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * 日历主题配置
 */
object CalendarTheme {
    /**
     * 科技风格配色方案
     */
    object TechColors {
        val Black = Color(0xFF000000)
        val White = Color(0xFFFFFFFF)
        val TechYellow = Color(0xFFFFD500)
        val DisabledGray = Color(0xFF666666)
        val BackgroundGray = Color(0xFF1A1A1A)
        val BorderGray = Color(0xFF333333)
        val HoverGray = Color(0xFF2A2A2A)
    }

    /**
     * 获取选中状态的颜色
     */
    fun getSelectedColor(isToday: Boolean): Color {
        return if (isToday) TechColors.TechYellow else TechColors.White
    }

    /**
     * 获取选中状态的背景色
     */
    fun getSelectedBackgroundColor(isToday: Boolean): Color {
        return if (isToday) TechColors.White else TechColors.TechYellow
    }
}