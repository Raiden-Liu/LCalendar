package com.raiden.lcalendar.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.raiden.lcalendar.*
import com.raiden.lcalendar.config.CalendarDialogConfig
import com.raiden.lcalendar.ui.theme.CalendarTheme
import java.util.*

/**
 * 日历弹窗内容组件
 */
@Composable
fun CalendarDialogContent(
    config: CalendarDialogConfig,
    onDismiss: () -> Unit,
    onConfirm: (Calendar) -> Unit
) {
    val context = LocalContext.current
    var selectedCalendar by remember {
        mutableStateOf(Calendar.getInstance().apply {
            timeInMillis = config.initialTimestamp
        })
    }

    // 处理返回键
    if (config.dismissOnBackPress) {
        BackHandler {
            onDismiss()
        }
    }

    // 获取本地化文本
    val title = config.title ?: "LCalendar"
    val cancelText = config.cancelButtonText ?: "Cancel"
    val okText = config.okButtonText ?: "OK"

    Dialog(
        onDismissRequest = if (config.dismissOnOutsideClick) onDismiss else {{}},
        properties = DialogProperties(
            dismissOnBackPress = config.dismissOnBackPress,
            dismissOnClickOutside = config.dismissOnOutsideClick
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CalendarTheme.TechColors.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // 标题
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        color = CalendarTheme.TechColors.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // 日历组件
                CustomCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    initialTimestamp = config.initialTimestamp,
                    onDateSelected = { calendar ->
                        selectedCalendar = calendar
                    },
                    onMonthChanged = { /* 处理月份变化 */ },
                    enabledDates = config.enabledDates,
                    disabledDates = config.disabledDates,
                    minDate = config.minDate,
                    maxDate = config.maxDate,
                    enabledColor = config.enabledColor,
                    disabledColor = config.disabledColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 按钮行
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 取消按钮
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = CalendarTheme.TechColors.DisabledGray
                        )
                    ) {
                        Text(
                            text = cancelText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // 确定按钮
                    Button(
                        onClick = {
                            onConfirm(selectedCalendar)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CalendarTheme.TechColors.TechYellow,
                            contentColor = CalendarTheme.TechColors.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = okText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

/**
 * 可组合的日历弹窗组件 - 用于在其他Composable中直接使用
 */
@Composable
fun CalendarDialogComposable(
    showDialog: Boolean,
    config: CalendarDialogConfig,
    onDismiss: () -> Unit,
    onDateSelected: (Calendar) -> Unit
) {
    if (showDialog) {
        CalendarDialogContent(
            config = config,
            onDismiss = onDismiss,
            onConfirm = onDateSelected
        )
    }
}

/**
 * 记住弹窗状态的便捷方法
 */
@Composable
fun rememberCalendarDialogState(
    initialShow: Boolean = false
): androidx.compose.runtime.MutableState<Boolean> {
    return remember { mutableStateOf(initialShow) }
}