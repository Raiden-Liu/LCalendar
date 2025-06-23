package com.raiden.lcalendar.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raiden.lcalendar.ui.theme.CalendarTheme
import com.raiden.lcalendar.util.CalendarUtils
import com.raiden.lcalendar.R
import java.util.*

/**
 * 日历头部组件
 */
@Composable
fun CalendarHeader(
    currentCalendar: Calendar,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onTodayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 月份和年份显示
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${CalendarUtils.getMonthName(currentCalendar)} ${currentCalendar.get(Calendar.YEAR)}",
                color = CalendarTheme.TechColors.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // 控制按钮组
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 回到今天按钮
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CalendarTheme.TechColors.TechYellow)
                    .clickable { onTodayClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = context.getString(R.string
                        .go_to_today),
                    tint = CalendarTheme.TechColors.Black,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 上个月按钮
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CalendarTheme.TechColors.BorderGray)
                    .clickable { onPreviousMonth() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = context.getString(R.string.previous_month),
                    tint = CalendarTheme.TechColors.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // 下个月按钮
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CalendarTheme.TechColors.BorderGray)
                    .clickable { onNextMonth() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = context.getString(R.string.next_month),
                    tint = CalendarTheme.TechColors.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * 星期标题组件
 */
@Composable
fun WeekDayHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CalendarUtils.getWeekDayNames().forEach { weekDay ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = weekDay,
                    color = CalendarTheme.TechColors.TechYellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}