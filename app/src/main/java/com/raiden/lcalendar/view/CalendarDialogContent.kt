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
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration

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

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


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
            dismissOnClickOutside = config.dismissOnOutsideClick,
                    usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = if (isLandscape) {
                Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.7f)
            } else {
                Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
            },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CalendarTheme.TechColors.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Calendar takes left side
                    CustomCalendar(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        initialTimestamp = config.initialTimestamp,
                        onDateSelected = { calendar -> selectedCalendar = calendar },
                        onMonthChanged = { calendar -> config.onMonthChangedListener?.onMonthChanged(calendar) },
                        enabledDates = config.enabledDates,
                        disabledDates = config.disabledDates,
                        minDate = config.minDate,
                        maxDate = config.maxDate,
                        enabledColor = config.enabledColor,
                        disabledColor = config.disabledColor
                    )
                    // Controls on right side
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Title
                        if (title.isNotEmpty()) {
                            Text(
                                text = title,
                                color = CalendarTheme.TechColors.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        // Buttons row at bottom
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                            Button(
                                onClick = { onConfirm(selectedCalendar) },
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
            } else {
                // Portrait layout (original content)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Title
                    if (title.isNotEmpty()) {
                        Text(
                            text = title,
                            color = CalendarTheme.TechColors.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Calendar
                    CustomCalendar(
                        modifier = Modifier.fillMaxWidth(),
                        initialTimestamp = config.initialTimestamp,
                        onDateSelected = { calendar -> selectedCalendar = calendar },
                        onMonthChanged = { calendar -> config.onMonthChangedListener?.onMonthChanged(calendar) },
                        enabledDates = config.enabledDates,
                        disabledDates = config.disabledDates,
                        minDate = config.minDate,
                        maxDate = config.maxDate,
                        enabledColor = config.enabledColor,
                        disabledColor = config.disabledColor
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Buttons row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                        Button(
                            onClick = { onConfirm(selectedCalendar) },
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