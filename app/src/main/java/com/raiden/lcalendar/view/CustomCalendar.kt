package com.raiden.lcalendar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiden.lcalendar.ui.theme.CalendarTheme
import com.raiden.lcalendar.util.CalendarUtils
import java.util.*

/**
 * 自定义日历组件
 *
 * @param modifier 修饰符
 * @param initialTimestamp 初始时间戳，默认为当前时间
 * @param onDateSelected 日期选择回调
 * @param onMonthChanged 月份切换回调
 * @param enabledDates 启用的日期列表，为空则所有日期启用
 * @param disabledDates 禁用的日期列表
 * @param minDate 最小可选日期
 * @param maxDate 最大可选日期
 * @param enabledColor 启用日期颜色
 * @param disabledColor 禁用日期颜色
 */
@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    initialTimestamp: Long = System.currentTimeMillis(),
    onDateSelected: (Calendar) -> Unit = {},
    onMonthChanged: (Calendar) -> Unit = {},
    enabledDates: Set<String>? = null, // 格式: "yyyy-MM-dd"
    disabledDates: Set<String> = emptySet(),
    minDate: Calendar? = null,
    maxDate: Calendar? = null,
    enabledColor: Color = CalendarTheme.TechColors.White,
    disabledColor: Color = CalendarTheme.TechColors.DisabledGray
) {
    var currentCalendar by remember {
        mutableStateOf(Calendar.getInstance().apply { timeInMillis = initialTimestamp })
    }

    var selectedCalendar by remember {
        mutableStateOf(Calendar.getInstance().apply { timeInMillis = initialTimestamp })
    }

    // 当日期改变时触发回调
    LaunchedEffect(selectedCalendar.timeInMillis) {
        onDateSelected(selectedCalendar.clone() as Calendar)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(
                CalendarTheme.TechColors.Black,
                RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = CalendarTheme.TechColors.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 月份导航栏
            CalendarHeader(
                currentCalendar = currentCalendar,
                onPreviousMonth = {
                    currentCalendar = CalendarUtils.getPreviousMonth(currentCalendar)
                    onMonthChanged(currentCalendar.clone() as Calendar)
                },
                onNextMonth = {
                    currentCalendar = CalendarUtils.getNextMonth(currentCalendar)
                    onMonthChanged(currentCalendar.clone() as Calendar)
                },
                onTodayClick = {
                    val today = Calendar.getInstance()
                    currentCalendar = today.clone() as Calendar
                    selectedCalendar = today.clone() as Calendar
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 星期标题
            WeekDayHeader()

            Spacer(modifier = Modifier.height(8.dp))

            // 日历网格
            CalendarGrid(
                currentCalendar = currentCalendar,
                selectedCalendar = selectedCalendar,
                onDateClick = { date ->
                    if (CalendarUtils.isDateSelectable(
                            date, enabledDates, disabledDates, minDate, maxDate
                        )) {
                        selectedCalendar = date
                    }
                },
                enabledDates = enabledDates,
                disabledDates = disabledDates,
                minDate = minDate,
                maxDate = maxDate,
                enabledColor = enabledColor,
                disabledColor = disabledColor
            )
        }
    }
}