package com.raiden.lcalendar.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.raiden.lcalendar.config.CalendarDialogConfig
import com.raiden.lcalendar.config.OnMonthChangedListener
import com.raiden.lcalendar.config.OnSelectDateListener
import java.util.*

/**
 * 日历弹窗构建器
 */
class CalendarDialogBuilder {
    private var config = CalendarDialogConfig()

    /**
     * 设置初始时间戳
     */
    fun setInitialTimestamp(timestamp: Long): CalendarDialogBuilder {
        config = config.copy(initialTimestamp = timestamp)
        return this
    }

    /**
     * 设置初始日期
     */
    fun setInitialDate(calendar: Calendar): CalendarDialogBuilder {
        config = config.copy(initialTimestamp = calendar.timeInMillis)
        return this
    }

    /**
     * 设置启用的日期列表
     */
    fun setEnabledDates(dates: Set<String>): CalendarDialogBuilder {
        config = config.copy(enabledDates = dates)
        return this
    }

    /**
     * 设置禁用的日期列表
     */
    fun setDisabledDates(dates: Set<String>): CalendarDialogBuilder {
        config = config.copy(disabledDates = dates)
        return this
    }

    /**
     * 设置最小可选日期
     */
    fun setMinDate(calendar: Calendar): CalendarDialogBuilder {
        config = config.copy(minDate = calendar)
        return this
    }

    /**
     * 设置最大可选日期
     */
    fun setMaxDate(calendar: Calendar): CalendarDialogBuilder {
        config = config.copy(maxDate = calendar)
        return this
    }

    /**
     * 设置启用日期的颜色
     */
    fun setEnabledColor(color: Color): CalendarDialogBuilder {
        config = config.copy(enabledColor = color)
        return this
    }

    /**
     * 设置禁用日期的颜色
     */
    fun setDisabledColor(color: Color): CalendarDialogBuilder {
        config = config.copy(disabledColor = color)
        return this
    }

    /**
     * 设置弹窗标题
     */
    fun setTitle(title: String): CalendarDialogBuilder {
        config = config.copy(title = title)
        return this
    }

    /**
     * 设置取消按钮文本
     */
    fun setCancelButtonText(text: String): CalendarDialogBuilder {
        config = config.copy(cancelButtonText = text)
        return this
    }

    /**
     * 设置确定按钮文本
     */
    fun setOkButtonText(text: String): CalendarDialogBuilder {
        config = config.copy(okButtonText = text)
        return this
    }

    /**
     * 设置点击外部是否关闭弹窗
     */
    fun setDismissOnOutsideClick(dismiss: Boolean): CalendarDialogBuilder {
        config = config.copy(dismissOnOutsideClick = dismiss)
        return this
    }

    /**
     * 设置按返回键是否关闭弹窗
     */
    fun setDismissOnBackPress(dismiss: Boolean): CalendarDialogBuilder {
        config = config.copy(dismissOnBackPress = dismiss)
        return this
    }

    /**
     * 设置日期选择监听器
     */
    fun setOnSelectDateListener(listener: OnSelectDateListener): CalendarDialogBuilder {
        config = config.copy(onSelectDateListener = listener)
        return this
    }
    /**
     * 设置月份切换监听器
     */
    fun setOnMonthChangedListener(listener: OnMonthChangedListener): CalendarDialogBuilder {
        config = config.copy(onMonthChangedListener = listener)
        return this
    }

    /**
     * 设置弹窗关闭监听器
     */
    fun setOnDismissListener(listener: () -> Unit): CalendarDialogBuilder {
        config = config.copy(onDismissListener = listener)
        return this
    }

    /**
     * 创建并显示弹窗
     */
    @Composable
    fun show(): CalendarDialogController {
        val showDialog = remember { mutableStateOf(true) }
        val controller = remember { CalendarDialogController(showDialog) }

        if (showDialog.value) {
            CalendarDialogContent(
                config = config,
                onDismiss = {
                    showDialog.value = false
                    config.onDismissListener?.invoke()
                },
                onConfirm = { selectedDate ->
                    config.onSelectDateListener?.onSelect(selectedDate)
                    showDialog.value = false
                }
            )
        }

        return controller
    }

    /**
     * 构建配置但不显示弹窗
     */
    fun build(): CalendarDialogConfig {
        return config
    }
}

/**
 * 日历弹窗控制器
 */
class CalendarDialogController(
    private val showDialog: androidx.compose.runtime.MutableState<Boolean>
) {
    /**
     * 显示弹窗
     */
    fun show() {
        showDialog.value = true
    }

    /**
     * 隐藏弹窗
     */
    fun dismiss() {
        showDialog.value = false
    }

    /**
     * 弹窗是否正在显示
     */
    val isShowing: Boolean
        get() = showDialog.value
}

/**
 * 创建日历弹窗构建器的便捷方法
 */
object CalendarDialog {
    /**
     * 创建构建器
     */
    fun builder(): CalendarDialogBuilder {
        return CalendarDialogBuilder()
    }
}