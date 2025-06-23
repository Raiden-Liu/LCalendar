package com.raiden.lcalendar.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.raiden.lcalendar.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日历工具类
 */
/**
 * 日历工具类
 */
object CalendarUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    /**
     * 获取上个月
     */
    fun getPreviousMonth(calendar: Calendar): Calendar {
        val newCalendar = calendar.clone() as Calendar
        newCalendar.add(Calendar.MONTH, -1)
        return newCalendar
    }

    /**
     * 获取下个月
     */
    fun getNextMonth(calendar: Calendar): Calendar {
        val newCalendar = calendar.clone() as Calendar
        newCalendar.add(Calendar.MONTH, 1)
        return newCalendar
    }

    /**
     * 获取月份的第一天
     */
    fun getFirstDayOfMonth(calendar: Calendar): Calendar {
        val newCalendar = calendar.clone() as Calendar
        newCalendar.set(Calendar.DAY_OF_MONTH, 1)
        return newCalendar
    }

    /**
     * 获取月份显示需要的天数（包含前后月份的天数）
     */
    fun getCalendarDays(calendar: Calendar): List<CalendarDay> {
        val days = mutableListOf<CalendarDay>()
        val firstDay = getFirstDayOfMonth(calendar)

        // 获取本月第一天是星期几（0=Sunday, 1=Monday, etc.）
        val firstDayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK) - 1

        // 添加上个月的天数
        val prevMonth = getPreviousMonth(calendar)
        val prevMonthLastDay = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in firstDayOfWeek - 1 downTo 0) {
            val day = prevMonthLastDay - i
            val dayCalendar = prevMonth.clone() as Calendar
            dayCalendar.set(Calendar.DAY_OF_MONTH, day)
            days.add(CalendarDay(dayCalendar, isCurrentMonth = false))
        }

        // 添加本月的天数
        val currentMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..currentMonthDays) {
            val dayCalendar = calendar.clone() as Calendar
            dayCalendar.set(Calendar.DAY_OF_MONTH, day)
            days.add(CalendarDay(dayCalendar, isCurrentMonth = true))
        }

        // 添加下个月的天数以填满42个格子（6行x7列）
        val nextMonth = getNextMonth(calendar)
        val remainingDays = 42 - days.size
        for (day in 1..remainingDays) {
            val dayCalendar = nextMonth.clone() as Calendar
            dayCalendar.set(Calendar.DAY_OF_MONTH, day)
            days.add(CalendarDay(dayCalendar, isCurrentMonth = false))
        }

        return days
    }

    /**
     * 检查日期是否可选择
     */
    fun isDateSelectable(
        date: Calendar,
        enabledDates: Set<String>?,
        disabledDates: Set<String>,
        minDate: Calendar?,
        maxDate: Calendar?
    ): Boolean {
        val dateString = formatDate(date)

        // 检查是否在禁用列表中
        if (disabledDates.contains(dateString)) return false

        // 检查最小最大日期限制
        minDate?.let { if (date.before(it)) return false }
        maxDate?.let { if (date.after(it)) return false }

        // 如果有启用列表，检查是否在启用列表中
        enabledDates?.let { return it.contains(dateString) }

        return true
    }

    /**
     * 格式化日期为字符串
     */
    fun formatDate(calendar: Calendar): String {
        return dateFormat.format(calendar.time)
    }

    /**
     * 检查是否是今天
     */
    fun isToday(calendar: Calendar): Boolean {
        val today = Calendar.getInstance()
        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 检查两个日期是否是同一天
     */
    fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取月份名称（国际化）
     */
    @Composable
    fun getMonthName(calendar: Calendar): String {
        val context = LocalContext.current
        val monthResIds = arrayOf(
            R.string.january, R.string.february,
            R.string.march, R.string.april,
            R.string.may, R.string.june,
            R.string.july, R.string.august,
            R.string.september, R.string.october,
            R.string.november, R.string.december
        )
        return context.getString(monthResIds[calendar.get(Calendar.MONTH)])
    }

    /**
     * 获取星期名称（国际化）
     */
    @Composable
    fun getWeekDayNames(): List<String> {
        val context = LocalContext.current
        return listOf(
            context.getString(R.string.sunday_short),
            context.getString(R.string.monday_short),
            context.getString(R.string.tuesday_short),
            context.getString(R.string.wednesday_short),
            context.getString(R.string.thursday_short),
            context.getString(R.string.friday_short),
            context.getString(R.string.saturday_short)
        )
    }

    /**
     * 获取星期名称（完整版本，国际化）
     */
    @Composable
    fun getFullWeekDayName(calendar: Calendar): String {
        val context = LocalContext.current
        val weekDayResIds = arrayOf(
            R.string.sunday, R.string.monday,
            R.string.tuesday, R.string.wednesday,
            R.string.thursday, R.string.friday,
            R.string.saturday
        )
        // Calendar.DAY_OF_WEEK: 1=Sunday, 2=Monday, etc.
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        return context.getString(weekDayResIds[dayOfWeek])
    }
}

/**
 * 日历日期数据类
 */
data class CalendarDay(
    val calendar: Calendar,
    val isCurrentMonth: Boolean
)// CalendarUtils.kt
