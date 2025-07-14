package com.raiden.lcalendar.view

import android.app.Activity
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.raiden.lcalendar.config.CalendarDialogConfig
import com.raiden.lcalendar.config.OnSelectDateListener
import java.util.*
import androidx.core.graphics.toColorInt
import com.raiden.lcalendar.config.OnMonthChangedListener

/**
 * 传统Android开发的日历弹窗兼容类 - 简化版本
 */
class CalendarDialogCompat private constructor(
    private val context: Context,
    private val config: CalendarDialogConfig
) {

    private var composeView: ComposeView? = null
    private var isShowing = false

    /**
     * 显示弹窗
     */
    fun show() {
        if (isShowing) return

        isShowing = true

        val dialog = android.app.Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
        val composeView = ComposeView(context).apply {
            setContent {
                CalendarDialogContent(
                    config = config,
                    onDismiss = {
                        dialog.dismiss()
                        isShowing = false
                        config.onDismissListener?.invoke()
                    },
                    onConfirm = { selectedDate ->
                        config.onSelectDateListener?.onSelect(selectedDate)
                        dialog.dismiss()
                        isShowing = false
                    }
                )
            }

            // 如果需要使用生命周期相关的特性（例如 rememberSaveable）
            if (context is androidx.lifecycle.LifecycleOwner) {
                setViewTreeLifecycleOwner(context as LifecycleOwner?)
                setViewTreeViewModelStoreOwner(
                    context as androidx.lifecycle.ViewModelStoreOwner)
                setViewTreeSavedStateRegistryOwner(context as androidx.savedstate
                    .SavedStateRegistryOwner)
            }
        }

        dialog.setContentView(composeView)
        dialog.setCancelable(config.dismissOnBackPress)
        dialog.setCanceledOnTouchOutside(config.dismissOnOutsideClick)
        dialog.show()
    }

    /**
     * 关闭弹窗
     */
    fun dismiss() {
        if (!isShowing) return

        isShowing = false
        composeView = null
    }

    /**
     * 检查弹窗是否正在显示
     */
    fun isShowing(): Boolean = isShowing

    /**
     * Builder类 - 传统Android开发专用
     */
    class Builder(private val context: Context) {
        private var config = CalendarDialogConfig()

        fun setInitialTimestamp(timestamp: Long): Builder {
            config = config.copy(initialTimestamp = timestamp)
            return this
        }

        fun setInitialDate(calendar: Calendar): Builder {
            config = config.copy(initialTimestamp = calendar.timeInMillis)
            return this
        }



        fun setEnabledDates(dates: Set<String>): Builder {
            config = config.copy(enabledDates = dates)
            return this
        }

        fun setDisabledDates(dates: Set<String>): Builder {
            config = config.copy(disabledDates = dates)
            return this
        }

        fun setMinDate(calendar: Calendar): Builder {
            config = config.copy(minDate = calendar)
            return this
        }

        fun setMaxDate(calendar: Calendar): Builder {
            config = config.copy(maxDate = calendar)
            return this
        }

        fun setEnabledColor(colorInt: Int): Builder {
            config = config.copy(enabledColor = Color(colorInt))
            return this
        }

        fun setDisabledColor(colorInt: Int): Builder {
            config = config.copy(disabledColor = Color(colorInt))
            return this
        }

        fun setTitle(title: String): Builder {
            config = config.copy(title = title)
            return this
        }

        fun setCancelButtonText(text: String): Builder {
            config = config.copy(cancelButtonText = text)
            return this
        }

        fun setOkButtonText(text: String): Builder {
            config = config.copy(okButtonText = text)
            return this
        }

        fun setDismissOnOutsideClick(dismiss: Boolean): Builder {
            config = config.copy(dismissOnOutsideClick = dismiss)
            return this
        }

        fun setDismissOnBackPress(dismiss: Boolean): Builder {
            config = config.copy(dismissOnBackPress = dismiss)
            return this
        }

        fun setOnSelectDateListener(listener: OnSelectDateListener): Builder {
            config = config.copy(onSelectDateListener = listener)
            return this
        }

        fun setOnMonthChangedListener(listener: OnMonthChangedListener): Builder {
            config = config.copy(onMonthChangedListener = listener)
            return this
        }

        fun setOnDismissListener(listener: () -> Unit): Builder {
            config = config.copy(onDismissListener = listener)
            return this
        }

        fun build(): CalendarDialogCompat {
            return CalendarDialogCompat(context, config)
        }

        fun show(): CalendarDialogCompat {
            val dialog = build()
            dialog.show()
            return dialog
        }
    }

    companion object {
        /**
         * 创建Builder - Activity使用
         */
        fun builder(activity: Activity): Builder {
            return Builder(activity)
        }

        /**
         * 创建Builder - Fragment使用
         */
        fun builder(fragment: Fragment): Builder {
            return Builder(fragment.requireActivity())
        }
    }
}

/**
 * 传统View系统的日历选择器Helper类
 */
object CalendarPickerHelper {

    /**
     * 在Activity中显示日历选择器
     */
    fun showDatePicker(
        activity: Activity,
        title: String = "选择日期",
        selectedDate: Calendar? = null, // 新增：预选中日期
        onDateSelected: (Calendar) -> Unit,
        onCancel: (() -> Unit)? = null
    ) {
        val builder = CalendarDialogCompat.builder(activity)
            .setTitle(title)
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    onDateSelected(selectedDate)
                }
            })
            .setOnDismissListener {
                onCancel?.invoke()
            }

        // 如果有预选中日期，设置它
        selectedDate?.let { builder.setInitialDate(it) }

        builder.show()
    }

    /**
     * 在Fragment中显示日历选择器
     */
    fun showDatePicker(
        fragment: Fragment,
        title: String = "选择日期",
        selectedDate: Calendar? = null, // 新增：预选中日期
        onDateSelected: (Calendar) -> Unit,
        onCancel: (() -> Unit)? = null
    ) {
        val builder = CalendarDialogCompat.builder(fragment)
            .setTitle(title)
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    onDateSelected(selectedDate)
                }
            })
            .setOnDismissListener {
                onCancel?.invoke()
            }

        // 如果有预选中日期，设置它
        selectedDate?.let { builder.setInitialDate(it) }

        builder.show()
    }

    /**
     * 显示预约日期选择器
     */
    fun showBookingDatePicker(
        activity: Activity,
        availableDates: Set<String>,
        selectedDate: Calendar? = null,
        onDateSelected: (Calendar) -> Unit
    ) {
        val builder = CalendarDialogCompat.builder(activity)
            .setTitle("选择预约日期")
            .setEnabledDates(availableDates)
            .setMinDate(Calendar.getInstance())
            .setMaxDate(Calendar.getInstance().apply { add(Calendar.MONTH, 3) })
            .setEnabledColor("#4CAF50".toColorInt())
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    onDateSelected(selectedDate)
                }
            })

        selectedDate?.let { builder.setInitialDate(it) }
        builder.show()
    }

    /**
     * 显示生日选择器
     */
    fun showBirthdayPicker(
        activity: Activity,
        selectedDate: Calendar? = null,
        onDateSelected: (Calendar) -> Unit
    ) {
        val builder = CalendarDialogCompat.builder(activity)
            .setTitle("选择生日")
            .setMinDate(Calendar.getInstance().apply { add(Calendar.YEAR, -100) })
            .setMaxDate(Calendar.getInstance())
            .setEnabledColor("#2196F3".toColorInt())
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    onDateSelected(selectedDate)
                }
            })

        // 如果没有预选中日期，默认选择25年前
        if (selectedDate != null) {
            builder.setInitialDate(selectedDate)
        } else {
            builder.setInitialDate(Calendar.getInstance().apply { add(Calendar.YEAR, -25) })
        }

        builder.show()
    }

    /**
     * 显示任务截止日期选择器
     */
    fun showDeadlinePicker(
        activity: Activity,
        selectedDate: Calendar? = null,
        onDateSelected: (Calendar) -> Unit
    ) {
        val builder = CalendarDialogCompat.builder(activity)
            .setTitle("设置截止日期")
            .setMinDate(Calendar.getInstance())
            .setEnabledColor("#F44336".toColorInt())
            .setOkButtonText("设置截止日期")
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    onDateSelected(selectedDate)
                }
            })

        selectedDate?.let { builder.setInitialDate(it) }
        builder.show()
    }
}