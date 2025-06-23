package com.raiden.lcalendar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiden.lcalendar.ui.theme.CalendarTheme
import com.raiden.lcalendar.util.CalendarDay
import com.raiden.lcalendar.util.CalendarUtils
import java.util.*

/**
 * 日历网格组件
 */
@Composable
fun CalendarGrid(
    currentCalendar: Calendar,
    selectedCalendar: Calendar,
    onDateClick: (Calendar) -> Unit,
    enabledDates: Set<String>?,
    disabledDates: Set<String>,
    minDate: Calendar?,
    maxDate: Calendar?,
    enabledColor: Color,
    disabledColor: Color,
    modifier: Modifier = Modifier
) {
    val calendarDays = remember(currentCalendar) {
        CalendarUtils.getCalendarDays(currentCalendar)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(calendarDays) { index, calendarDay ->
            CalendarDayItem(
                calendarDay = calendarDay,
                isSelected = CalendarUtils.isSameDay(calendarDay.calendar, selectedCalendar),
                isToday = CalendarUtils.isToday(calendarDay.calendar),
                isSelectable = CalendarUtils.isDateSelectable(
                    calendarDay.calendar,
                    enabledDates,
                    disabledDates,
                    minDate,
                    maxDate
                ),
                enabledColor = enabledColor,
                disabledColor = disabledColor,
                onClick = { onDateClick(calendarDay.calendar) }
            )
        }
    }
}

/**
 * 单个日期项组件
 */
@Composable
private fun CalendarDayItem(
    calendarDay: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    isSelectable: Boolean,
    enabledColor: Color,
    disabledColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected && isToday -> CalendarTheme.TechColors.White
        isSelected -> CalendarTheme.TechColors.TechYellow
        isToday -> CalendarTheme.TechColors.BorderGray
        else -> Color.Transparent
    }

    val textColor = when {
        !isSelectable -> disabledColor
        !calendarDay.isCurrentMonth -> CalendarTheme.TechColors.DisabledGray
        isSelected && isToday -> CalendarTheme.TechColors.Black
        isSelected -> CalendarTheme.TechColors.Black
        isToday -> CalendarTheme.TechColors.TechYellow
        else -> enabledColor
    }

    val borderColor = when {
        isToday && !isSelected -> CalendarTheme.TechColors.TechYellow
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = if (borderColor != Color.Transparent) 1.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .let { baseModifier ->
                if (isSelectable && calendarDay.isCurrentMonth) {
                    baseModifier.clickable { onClick() }
                } else {
                    baseModifier
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = calendarDay.calendar.get(Calendar.DAY_OF_MONTH).toString(),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}