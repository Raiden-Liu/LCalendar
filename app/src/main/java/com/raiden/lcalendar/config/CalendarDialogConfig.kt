package com.raiden.lcalendar.config

import java.util.*




import androidx.compose.ui.graphics.Color
import com.raiden.lcalendar.ui.theme.CalendarTheme

/**
 * 日历弹窗配置类
 */
data class CalendarDialogConfig(
    // 日历组件参数
    val initialTimestamp: Long = System.currentTimeMillis(),
    val enabledDates: Set<String>? = null,
    val disabledDates: Set<String> = emptySet(),
    val minDate: Calendar? = null,
    val maxDate: Calendar? = null,
    val enabledColor: Color = CalendarTheme.TechColors.White,
    val disabledColor: Color = CalendarTheme.TechColors.DisabledGray,

    // 弹窗参数
    val title: String? = null,
    val cancelButtonText: String? = null,
    val okButtonText: String? = null,
    val dismissOnOutsideClick: Boolean = true,
    val dismissOnBackPress: Boolean = true,

    // 回调
    val onSelectDateListener: OnSelectDateListener? = null,
    val onMonthChangedListener: OnMonthChangedListener? = null,
    val onDismissListener: (() -> Unit)? = null
)
/**
 * 日期选择监听器接口
 */
interface OnSelectDateListener {
    /**
     * 当用户选择日期并点击确定按钮时调用
     * @param selectedDate 选中的日期
     */
    fun onSelect(selectedDate: Calendar)
}
/**
 * 月份切换监听器接口
 */
interface OnMonthChangedListener {
    /**
     * 当月份切换时调用
     * @param calendar 切换后的月份日历对象
     */
    fun onMonthChanged(calendar: Calendar)
}