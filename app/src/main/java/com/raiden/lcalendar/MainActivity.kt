//package com.raiden.lcalendar
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.raiden.lcalendar.config.OnSelectDateListener
//import com.raiden.lcalendar.ui.theme.CalendarTheme
//import com.raiden.lcalendar.util.CalendarUtils
//import com.raiden.lcalendar.view.CalendarDialog
//import com.raiden.lcalendar.view.CalendarDialogComposable
//import com.raiden.lcalendar.view.CustomCalendar
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                CalendarDialogUsageExample()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CalendarExampleApp() {
//    val context = LocalContext.current
//
//    // 状态管理
//    var selectedDateText by remember { mutableStateOf("") }
//    var currentMonthText by remember { mutableStateOf("") }
//    var selectedExample by remember { mutableStateOf(0) }
//    var showDateDetails by remember { mutableStateOf(false) }
//    var lastSelectedCalendar by remember { mutableStateOf<Calendar?>(null) }
//
//    // 日期格式化器
//    val dateFormatter = remember {
//        SimpleDateFormat("yyyy-MM-dd EEEE", Locale.getDefault())
//    }
//
//    // 示例数据
//    val exampleData = prepareExampleData()
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = CalendarTheme.TechColors.BackgroundGray
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp, vertical = 30.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // 应用标题
//            TitleCard()
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // 示例选择器
//            ExampleSelector(
//                selectedExample = selectedExample,
//                onExampleSelected = { selectedExample = it }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 根据选择显示不同示例
//            when (selectedExample) {
//                0 -> BasicCalendarExample(
//                    onDateSelected = { calendar ->
//                        selectedDateText = context.getString(
//                            R.string.selected_date,
//                            dateFormatter.format(calendar.time)
//                        )
//                        lastSelectedCalendar = calendar
//                        showDateDetails = true
//                    },
//                    onMonthChanged = { calendar ->
//                        currentMonthText = context.getString(
//                            R.string.current_month_display,
//                            CalendarUtils.getMonthName(calendar),
//                            calendar.get(Calendar.YEAR)
//                        )
//                    }
//                )
//
//                1 -> AdvancedCalendarExample(
//                    exampleData = exampleData,
//                    onDateSelected = { calendar ->
//                        selectedDateText = context.getString(
//                            R.string.advanced_selected,
//                            dateFormatter.format(calendar.time)
//                        )
//                        lastSelectedCalendar = calendar
//                        showDateDetails = true
//                    }
//                )
//
//                2 -> CustomTimestampExample(
//                    onDateSelected = { calendar ->
//                        selectedDateText = context.getString(
//                            R.string.timestamp_selected,
//                            dateFormatter.format(calendar.time)
//                        )
//                        lastSelectedCalendar = calendar
//                        showDateDetails = true
//                    }
//                )
//
//                3 -> DateRangeExample(
//                    onDateSelected = { calendar ->
//                        selectedDateText = context.getString(
//                            R.string.range_selected,
//                            dateFormatter.format(calendar.time)
//                        )
//                        lastSelectedCalendar = calendar
//                        showDateDetails = true
//                    }
//                )
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
////            // 显示选择结果
////            if (selectedDateText.isNotEmpty()) {
////                ResultCard(
////                    selectedDateText = selectedDateText,
////                    currentMonthText = currentMonthText,
////                    lastSelectedCalendar = lastSelectedCalendar,
////                    showDateDetails = showDateDetails,
////                    onDismissDetails = { showDateDetails = false }
////                )
////            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            // 底部说明
//            Text(
//                text = context.getString(R.string.bottom_instructions),
//                color = CalendarTheme.TechColors.DisabledGray,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun TitleCard() {
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        ),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = context.getString(R.string.main_title),
//                color = CalendarTheme.TechColors.TechYellow,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//        }
//    }
//}
//
//@Composable
//fun ExampleSelector(
//    selectedExample: Int,
//    onExampleSelected: (Int) -> Unit
//) {
//    val context = LocalContext.current
//    val examples = listOf(
//        context.getString(R.string.basic_calendar),
//        context.getString(R.string.advanced_features),
//        context.getString(R.string.custom_timestamp),
//        context.getString(R.string.date_range_limit)
//    )
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.select_example),
//                color = CalendarTheme.TechColors.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                examples.forEachIndexed { index, example ->
//                    FilterChip(
//                        onClick = { onExampleSelected(index) },
//                        label = {
//                            Text(
//                                text = example,
//                                fontSize = 12.sp
//                            )
//                        },
//                        selected = selectedExample == index,
//                        colors = FilterChipDefaults.filterChipColors(
//                            selectedContainerColor = CalendarTheme.TechColors.TechYellow,
//                            selectedLabelColor = CalendarTheme.TechColors.Black,
//                            containerColor = CalendarTheme.TechColors.BorderGray,
//                            labelColor = CalendarTheme.TechColors.White
//                        ),
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BasicCalendarExample(
//    onDateSelected: (Calendar) -> Unit,
//    onMonthChanged: @Composable (Calendar) -> Unit
//) {
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.basic_calendar),
//                color = CalendarTheme.TechColors.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = context.getString(R.string.basic_calendar_desc),
//                color = CalendarTheme.TechColors.DisabledGray,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            CustomCalendar(
//                onDateSelected = onDateSelected,
//                onMonthChanged = {
//
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun AdvancedCalendarExample(
//    exampleData: ExampleData,
//    onDateSelected: (Calendar) -> Unit
//) {
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.advanced_features),
//                color = CalendarTheme.TechColors.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = context.getString(R.string.advanced_calendar_desc),
//                color = CalendarTheme.TechColors.DisabledGray,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            CustomCalendar(
//                enabledDates = exampleData.enabledDates,
//                disabledDates = exampleData.disabledDates,
//                minDate = exampleData.minDate,
//                maxDate = exampleData.maxDate,
//                enabledColor = Color(0xFF00FF88),
//                disabledColor = Color(0xFF444444),
//                onDateSelected = onDateSelected
//            )
//        }
//    }
//}
//
//@Composable
//fun CustomTimestampExample(
//    onDateSelected: (Calendar) -> Unit
//) {
//    val context = LocalContext.current
//    val customTimestamp = remember {
//        Calendar.getInstance().apply {
//            set(2024, Calendar.JANUARY, 1, 0, 0, 0)
//        }.timeInMillis
//    }
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.custom_timestamp),
//                color = CalendarTheme.TechColors.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = context.getString(R.string.custom_timestamp_desc),
//                color = CalendarTheme.TechColors.TechYellow,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            CustomCalendar(
//                initialTimestamp = customTimestamp,
//                onDateSelected = onDateSelected
//            )
//        }
//    }
//}
//
//@Composable
//fun DateRangeExample(
//    onDateSelected: (Calendar) -> Unit
//) {
//    val context = LocalContext.current
//    val minDate = Calendar.getInstance().apply {
//        add(Calendar.DAY_OF_MONTH, -30)
//    }
//    val maxDate = Calendar.getInstance().apply {
//        add(Calendar.DAY_OF_MONTH, 30)
//    }
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.Black
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.date_range_limit),
//                color = CalendarTheme.TechColors.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = context.getString(R.string.date_range_desc),
//                color = CalendarTheme.TechColors.TechYellow,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            CustomCalendar(
//                minDate = minDate,
//                maxDate = maxDate,
//                enabledColor = CalendarTheme.TechColors.TechYellow,
//                onDateSelected = onDateSelected
//            )
//        }
//    }
//}
//
//@Composable
//fun ResultCard(
//    selectedDateText: String,
//    currentMonthText: String,
//    lastSelectedCalendar: Calendar?,
//    showDateDetails: Boolean,
//    onDismissDetails: () -> Unit
//) {
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = CalendarTheme.TechColors.TechYellow
//        )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = context.getString(R.string.selection_result),
//                color = CalendarTheme.TechColors.Black,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            if (selectedDateText.isNotEmpty()) {
//                Text(
//                    text = selectedDateText,
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 14.sp
//                )
//            }
//
//            if (currentMonthText.isNotEmpty()) {
//                Text(
//                    text = currentMonthText,
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 14.sp
//                )
//            }
//
//            // 详细信息
//            if (showDateDetails && lastSelectedCalendar != null) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Divider(color = CalendarTheme.TechColors.Black.copy(alpha = 0.3f))
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = context.getString(R.string.detail_info),
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = context.getString(
//                        R.string.timestamp_info,
//                        lastSelectedCalendar.timeInMillis
//                    ),
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 11.sp
//                )
//
//                Text(
//                    text = context.getString(
//                        R.string.weekday_info,
//                        CalendarUtils.getFullWeekDayName(lastSelectedCalendar)
//                    ),
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 11.sp
//                )
//
//                Text(
//                    text = context.getString(
//                        R.string.is_today_info,
//                        context.getString(
//                            if (CalendarUtils.isToday(lastSelectedCalendar))
//                                R.string.yes
//                            else
//                                R.string.no
//                        )
//                    ),
//                    color = CalendarTheme.TechColors.Black,
//                    fontSize = 11.sp
//                )
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    TextButton(
//                        onClick = onDismissDetails,
//                        colors = ButtonDefaults.textButtonColors(
//                            contentColor = CalendarTheme.TechColors.Black
//                        )
//                    ) {
//                        Text(
//                            text = context.getString(R.string.dismiss_details),
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//// 数据类
//data class ExampleData(
//    val enabledDates: Set<String>,
//    val disabledDates: Set<String>,
//    val minDate: Calendar,
//    val maxDate: Calendar
//)
//
//// 示例数据准备函数
//@Composable
//fun prepareExampleData(): ExampleData {
//    return remember {
//        val calendar = Calendar.getInstance()
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        // 启用的日期列表（本月1-15号）
//        val enabledDates = mutableSetOf<String>()
//        for (day in 1..15) {
//            calendar.set(Calendar.DAY_OF_MONTH, day)
//            enabledDates.add(dateFormat.format(calendar.time))
//        }
//
//        // 禁用的日期列表（3号和13号）
//        val disabledDates = mutableSetOf<String>()
//        calendar.set(Calendar.DAY_OF_MONTH, 3)
//        disabledDates.add(dateFormat.format(calendar.time))
//        calendar.set(Calendar.DAY_OF_MONTH, 13)
//        disabledDates.add(dateFormat.format(calendar.time))
//
//        // 最小和最大日期
//        val minDate = Calendar.getInstance().apply {
//            add(Calendar.MONTH, -1)
//        }
//
//        val maxDate = Calendar.getInstance().apply {
//            add(Calendar.MONTH, 2)
//        }
//
//        ExampleData(enabledDates, disabledDates, minDate, maxDate)
//    }
//}
//
///**
// * 日历弹窗使用示例
// */
//@Composable
//fun CalendarDialogUsageExample() {
//    var selectedDateText by remember { mutableStateOf("未选择日期") }
//
//    // 各种弹窗的显示状态
//    var showBasicDialog by remember { mutableStateOf(false) }
//    var showAdvancedDialog by remember { mutableStateOf(false) }
//    var showComposableDialog by remember { mutableStateOf(false) }
//    var mSelectedDate by remember { mutableStateOf<Calendar?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//
//        Text(
//            text = "日历弹窗示例",
//            style = MaterialTheme.typography.headlineMedium,
//            color = CalendarTheme.TechColors.White
//        )
//
//        // 显示选中的日期
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            colors = CardDefaults.cardColors(
//                containerColor = CalendarTheme.TechColors.TechYellow
//            )
//        ) {
//            Text(
//                text = "选中的日期: $selectedDateText",
//                modifier = Modifier.padding(16.dp),
//                color = CalendarTheme.TechColors.Black
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 基础使用示例
//        Button(
//            onClick = { showBasicDialog = true },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = CalendarTheme.TechColors.TechYellow
//            )
//        ) {
//            Text("显示基础日历弹窗", color = CalendarTheme.TechColors.Black)
//        }
//
//        // 高级配置示例
//        Button(
//            onClick = { showAdvancedDialog = true },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = CalendarTheme.TechColors.BorderGray
//            )
//        ) {
//            Text("显示高级配置弹窗", color = CalendarTheme.TechColors.White)
//        }
//
//        // 可组合组件示例
//        Button(
//            onClick = { showComposableDialog = true },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = CalendarTheme.TechColors.DisabledGray
//            )
//        ) {
//            Text("显示可组合弹窗", color = CalendarTheme.TechColors.White)
//        }
//
//        // 基础弹窗
//        if (showBasicDialog) {
//            BasicDialogExample(
//                onDateSelected = { selectedDate ->
//                    val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
//                    selectedDateText = formatter.format(selectedDate.time)
//                    showBasicDialog = false
//                    mSelectedDate = selectedDate
//                },
//                onDismiss = { showBasicDialog = false },
//                defaultDate = mSelectedDate
//            )
//        }
//
//        // 高级配置弹窗
//        if (showAdvancedDialog) {
//            AdvancedDialogExample(
//                onDateSelected = { selectedDate ->
//                    val formatter = SimpleDateFormat("yyyy-MM-dd EEEE", Locale.getDefault())
//                    selectedDateText = formatter.format(selectedDate.time)
//                    showAdvancedDialog = false
//
//                },
//                onDismiss = { showAdvancedDialog = false },
//            )
//        }
//
//        // 可组合组件弹窗
//        if (showComposableDialog) {
//            val config = CalendarDialog.builder()
//                .setTitle("选择日期")
//                .setCancelButtonText("取消")
//                .setOkButtonText("确定")
//                .setEnabledColor(Color(0xFF00FF88))
//                .build()
//
//            CalendarDialogComposable(
//                showDialog = showComposableDialog,
//                config = config,
//                onDismiss = { showComposableDialog = false },
//                onDateSelected = { selectedDate ->
//                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//                    selectedDateText = formatter.format(selectedDate.time)
//                    showComposableDialog = false
//                }
//            )
//        }
//    }
//}
//
///**
// * 基础日历弹窗示例
// */
//@Composable
//fun BasicDialogExample(
//    onDateSelected: (Calendar) -> Unit,
//    onDismiss: () -> Unit,
//    defaultDate: Calendar? = null
//) {
//    val builder = CalendarDialog.builder()
//        .setOnSelectDateListener(object : OnSelectDateListener {
//            override fun onSelect(selectedDate: Calendar) {
//                onDateSelected(selectedDate)
//            }
//        })
//        .setOnDismissListener {
//            onDismiss()
//        }
//
//    // 仅当 defaultDate 不为 null 时调用 setInitialDate
//    defaultDate?.let {
//        builder.setInitialDate(it)
//    }
//
//    builder.show()
//}
//
///**
// * 高级配置弹窗示例
// */
//@Composable
//fun AdvancedDialogExample(
//    onDateSelected: (Calendar) -> Unit,
//    onDismiss: () -> Unit
//) {
//    // 准备示例数据
//    val enabledDates = remember {
//        val dates = mutableSetOf<String>()
//        val calendar = Calendar.getInstance()
//        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        // 只启用本月的1-15号
//        for (day in 1..15) {
//            calendar.set(Calendar.DAY_OF_MONTH, day)
//            dates.add(formatter.format(calendar.time))
//        }
//        dates
//    }
//
//    val disabledDates = remember {
//        val dates = mutableSetOf<String>()
//        val calendar = Calendar.getInstance()
//        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        // 禁用3号和13号
//        calendar.set(Calendar.DAY_OF_MONTH, 3)
//        dates.add(formatter.format(calendar.time))
//        calendar.set(Calendar.DAY_OF_MONTH, 13)
//        dates.add(formatter.format(calendar.time))
//        dates
//    }
//
//    CalendarDialog.builder()
//        .setTitle("高级日历选择")
//        .setCancelButtonText("取消")
//        .setOkButtonText("确认")
//        .setEnabledDates(enabledDates)
//        .setDisabledDates(disabledDates)
//        .setEnabledColor(Color(0xFF00FF88))
//        .setDisabledColor(Color(0xFF666666))
//        .setMinDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -30) })
//        .setMaxDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 30) })
//        .setDismissOnOutsideClick(false)
//        .setOnSelectDateListener(object : OnSelectDateListener {
//            override fun onSelect(selectedDate: Calendar) {
//                onDateSelected(selectedDate)
//            }
//        })
//        .setOnDismissListener {
//            onDismiss()
//        }
//        .show()
//}
//
///**
// * 实际应用场景示例
// */
//@Composable
//fun RealWorldExamples() {
//    var showBookingDialog by remember { mutableStateOf(false) }
//    var showBirthdayDialog by remember { mutableStateOf(false) }
//    var showTaskDialog by remember { mutableStateOf(false) }
//    var resultText by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text(
//            text = "实际应用场景",
//            style = MaterialTheme.typography.headlineSmall,
//            color = CalendarTheme.TechColors.White
//        )
//
//        // 预约系统
//        Button(
//            onClick = { showBookingDialog = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("预约系统 - 选择预约日期")
//        }
//
//        // 生日选择
//        Button(
//            onClick = { showBirthdayDialog = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("个人资料 - 选择生日")
//        }
//
//        // 任务截止日期
//        Button(
//            onClick = { showTaskDialog = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("任务管理 - 设置截止日期")
//        }
//
//        if (resultText.isNotEmpty()) {
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                colors = CardDefaults.cardColors(
//                    containerColor = CalendarTheme.TechColors.TechYellow
//                )
//            ) {
//                Text(
//                    text = resultText,
//                    modifier = Modifier.padding(16.dp),
//                    color = CalendarTheme.TechColors.Black
//                )
//            }
//        }
//
//        // 预约系统弹窗
//        if (showBookingDialog) {
//            BookingSystemDialog(
//                onDateSelected = { date ->
//                    val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
//                    resultText = "预约日期: ${formatter.format(date.time)}"
//                    showBookingDialog = false
//                },
//                onDismiss = { showBookingDialog = false }
//            )
//        }
//
//        // 生日选择弹窗
//        if (showBirthdayDialog) {
//            BirthdayPickerDialog(
//                onDateSelected = { date ->
//                    val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
//                    resultText = "生日: ${formatter.format(date.time)}"
//                    showBirthdayDialog = false
//                },
//                onDismiss = { showBirthdayDialog = false }
//            )
//        }
//
//        // 任务截止日期弹窗
//        if (showTaskDialog) {
//            TaskDeadlineDialog(
//                onDateSelected = { date ->
//                    val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
//                    resultText = "任务截止日期: ${formatter.format(date.time)}"
//                    showTaskDialog = false
//                },
//                onDismiss = { showTaskDialog = false }
//            )
//        }
//    }
//}
//
///**
// * 预约系统弹窗
// */
//@Composable
//fun BookingSystemDialog(
//    onDateSelected: (Calendar) -> Unit,
//    onDismiss: () -> Unit
//) {
//    // 获取可预约的日期（示例：未来30天内的工作日）
//    val availableDates = remember {
//        val dates = mutableSetOf<String>()
//        val calendar = Calendar.getInstance()
//        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        for (i in 1..30) {
//            calendar.timeInMillis = System.currentTimeMillis()
//            calendar.add(Calendar.DAY_OF_MONTH, i)
//
//            // 只有工作日可以预约
//            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//            if (dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY) {
//                dates.add(formatter.format(calendar.time))
//            }
//        }
//        dates
//    }
//
//    CalendarDialog.builder()
//        .setTitle("选择预约日期")
//        .setEnabledDates(availableDates)
//        .setMinDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) })
//        .setMaxDate(Calendar.getInstance().apply { add(Calendar.MONTH, 3) })
//        .setEnabledColor(Color(0xFF4CAF50))
//        .setCancelButtonText("取消")
//        .setOkButtonText("确认预约")
//        .setOnSelectDateListener(object : OnSelectDateListener {
//            override fun onSelect(selectedDate: Calendar) {
//                onDateSelected(selectedDate)
//            }
//        })
//        .setOnDismissListener { onDismiss() }
//        .show()
//}
//
///**
// * 生日选择弹窗
// */
//@Composable
//fun BirthdayPickerDialog(
//    onDateSelected: (Calendar) -> Unit,
//    onDismiss: () -> Unit
//) {
//    CalendarDialog.builder()
//        .setTitle("选择生日")
//        .setMinDate(Calendar.getInstance().apply { add(Calendar.YEAR, -100) })
//        .setMaxDate(Calendar.getInstance())
//        .setInitialDate(Calendar.getInstance().apply { add(Calendar.YEAR, -25) })
//        .setEnabledColor(Color(0xFF2196F3))
//        .setCancelButtonText("取消")
//        .setOkButtonText("确定")
//        .setOnSelectDateListener(object : OnSelectDateListener {
//            override fun onSelect(selectedDate: Calendar) {
//                onDateSelected(selectedDate)
//            }
//        })
//        .setOnDismissListener { onDismiss() }
//        .show()
//}
//
///**
// * 任务截止日期弹窗
// */
//@Composable
//fun TaskDeadlineDialog(
//    onDateSelected: (Calendar) -> Unit,
//    onDismiss: () -> Unit
//) {
//    CalendarDialog.builder()
//        .setTitle("设置任务截止日期")
//        .setMinDate(Calendar.getInstance())
//        .setEnabledColor(Color(0xFFF44336))
//        .setCancelButtonText("取消")
//        .setOkButtonText("设置截止日期")
//        .setOnSelectDateListener(object : OnSelectDateListener {
//            override fun onSelect(selectedDate: Calendar) {
//                onDateSelected(selectedDate)
//            }
//        })
//        .setOnDismissListener { onDismiss() }
//        .show()
//}
//
// TraditionalAndroidUsageExample.kt

package com.raiden.lcalendar
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.raiden.lcalendar.config.OnMonthChangedListener
import com.raiden.lcalendar.view.CalendarDialogCompat
import com.raiden.lcalendar.view.CalendarPickerHelper
import com.raiden.lcalendar.config.OnSelectDateListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * 传统Activity中使用日历弹窗的示例
 */
class MainActivity : FragmentActivity() {

    private lateinit var btnBasicCalendar: Button
    private lateinit var btnAdvancedCalendar: Button
    private lateinit var btnBookingCalendar: Button
    private lateinit var btnBirthdayCalendar: Button
    private lateinit var tvSelectedDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 如果您有布局文件，可以使用setContentView(R.layout.activity_traditional)
        // 这里为了演示，我们用代码创建布局
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        // 创建线性布局
        val linearLayout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }

        // 标题
        val tvTitle = TextView(this).apply {
            text = "传统Android开发日历弹窗示例"
            textSize = 20f
            setTextColor(Color.BLACK)
            setPadding(0, 0, 0, 32)
        }
        linearLayout.addView(tvTitle)

        // 显示选中日期的TextView
        tvSelectedDate = TextView(this).apply {
            text = "未选择日期"
            textSize = 16f
            setTextColor(Color.BLUE)
            setPadding(0, 0, 0, 32)
        }
        linearLayout.addView(tvSelectedDate)

        // 基础日历按钮
        btnBasicCalendar = Button(this).apply {
            text = "显示基础日历"
        }
        linearLayout.addView(btnBasicCalendar)

        // 高级配置日历按钮
        btnAdvancedCalendar = Button(this).apply {
            text = "显示高级配置日历"
        }
        linearLayout.addView(btnAdvancedCalendar)

        // 预约日历按钮
        btnBookingCalendar = Button(this).apply {
            text = "预约系统日历"
        }
        linearLayout.addView(btnBookingCalendar)

        // 生日选择按钮
        btnBirthdayCalendar = Button(this).apply {
            text = "生日选择器"
        }
        linearLayout.addView(btnBirthdayCalendar)

        setContentView(linearLayout)
    }

    private fun setupClickListeners() {
        // 基础日历
        btnBasicCalendar.setOnClickListener {
            showBasicCalendar()
        }

        // 高级配置日历
        btnAdvancedCalendar.setOnClickListener {
            showAdvancedCalendar()
        }

        // 预约日历
        btnBookingCalendar.setOnClickListener {
            showBookingCalendar()
        }

        // 生日选择
        btnBirthdayCalendar.setOnClickListener {
            showBirthdayCalendar()
        }
    }

    /**
     * 显示基础日历
     */
    private fun showBasicCalendar() {
        CalendarPickerHelper.showDatePicker(
            activity = this,
            title = "选择日期",
            onDateSelected = { selectedDate ->
                val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                val dateString = formatter.format(selectedDate.time)
                tvSelectedDate.text = "选中日期: $dateString"

                Toast.makeText(this, "选择了: $dateString", Toast.LENGTH_SHORT).show()
            },
            onCancel = {
                Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show()
            }
        )
    }

    /**
     * 显示高级配置日历
     */
    private fun showAdvancedCalendar() {
        // 准备示例数据
        val enabledDates = generateEnabledDates().toMutableSet()
        var disabledDates = generateDisabledDates()

        CalendarDialogCompat.builder(this)
            .setTitle("高级配置日历")
            .setEnabledDates(enabledDates)
            .setDisabledDates(disabledDates)
            .setEnabledColor(Color.parseColor("#00FF88"))
            .setDisabledColor(Color.parseColor("#666666"))
            .setMinDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -30) })
            .setMaxDate(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 30) })
            .setCancelButtonText("取消")
            .setOkButtonText("确认")
            .setDismissOnOutsideClick(false)
            .setOnSelectDateListener(object : OnSelectDateListener {
                override fun onSelect(selectedDate: Calendar) {
                    val formatter = SimpleDateFormat("yyyy-MM-dd EEEE", Locale.getDefault())
                    val dateString = formatter.format(selectedDate.time)
                    tvSelectedDate.text = "高级日历选中: $dateString"

                    // 处理业务逻辑
                    handleAdvancedDateSelection(selectedDate)
                }
            })
            .setOnDismissListener {
                Toast.makeText(this, "高级日历被关闭", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    /**
     * 显示预约日历
     */
    private fun showBookingCalendar() {
        val availableDates = generateAvailableBookingDates()

        CalendarPickerHelper.showBookingDatePicker(
            activity = this,
            availableDates = availableDates,
            onDateSelected = { selectedDate ->
                val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                val dateString = formatter.format(selectedDate.time)
                tvSelectedDate.text = "预约日期: $dateString"

                // 处理预约逻辑
                handleBookingRequest(selectedDate)
            }
        )
    }

    /**
     * 显示生日选择器
     */
    private fun showBirthdayCalendar() {
        CalendarPickerHelper.showBirthdayPicker(
            activity = this,
            onDateSelected = { selectedDate ->
                val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                val dateString = formatter.format(selectedDate.time)
                tvSelectedDate.text = "生日: $dateString"

                // 保存生日信息
                saveBirthday(selectedDate)
            }
        )
    }

    // 辅助方法
    private fun generateEnabledDates(): Set<String> {
        val dates = mutableSetOf<String>()
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // 只启用本月的1-15号
        for (day in 1..15) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            dates.add(formatter.format(calendar.time))
        }
        return dates
    }


    private fun generateDisabledDates(): Set<String> {
        val dates = mutableSetOf<String>()
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // 禁用3号和13号
        calendar.set(Calendar.DAY_OF_MONTH, 3)
        dates.add(formatter.format(calendar.time))
        calendar.set(Calendar.DAY_OF_MONTH, 13)
        dates.add(formatter.format(calendar.time))
        return dates
    }

    private fun generateAvailableBookingDates(): Set<String> {
        val dates = mutableSetOf<String>()
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // 生成未来30天的工作日作为可预约日期
        for (i in 1..30) {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.DAY_OF_MONTH, i)

            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY) {
                dates.add(formatter.format(calendar.time))
            }
        }
        return dates
    }

    private fun handleAdvancedDateSelection(selectedDate: Calendar) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateString = formatter.format(selectedDate.time)

        // 可以在这里执行数据库操作、网络请求等
        // 例如：保存到SharedPreferences
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .edit()
            .putString("advanced_selected_date", dateString)
            .apply()

        Toast.makeText(this, "高级日历日期已保存", Toast.LENGTH_SHORT).show()
    }

    private fun handleBookingRequest(selectedDate: Calendar) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = formatter.format(selectedDate.time)

        // 处理预约请求
        // 例如：调用API
        Toast.makeText(this, "预约请求已发送: $dateString", Toast.LENGTH_LONG).show()

        // 这里可以调用实际的预约API
        // bookingApi.createBooking(dateString)
    }

    private fun saveBirthday(selectedDate: Calendar) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = formatter.format(selectedDate.time)

        // 保存生日到本地存储
        getSharedPreferences("user_profile", MODE_PRIVATE)
            .edit()
            .putString("birthday", dateString)
            .apply()

        Toast.makeText(this, "生日已保存", Toast.LENGTH_SHORT).show()
    }
}

/**
 * 传统Fragment中使用日历弹窗的示例
 */
class TraditionalFragment : Fragment() {

    private lateinit var btnSelectDate: Button
    private lateinit var tvSelectedDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 创建布局
        val linearLayout = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
        }

        // 标题
        val tvTitle = TextView(requireContext()).apply {
            text = "Fragment中的日历弹窗"
            textSize = 18f
            setTextColor(Color.BLACK)
            setPadding(0, 0, 0, 32)
        }
        linearLayout.addView(tvTitle)

        // 显示选中日期的TextView
        tvSelectedDate = TextView(requireContext()).apply {
            text = "未选择日期"
            textSize = 16f
            setTextColor(Color.BLUE)
            setPadding(0, 0, 0, 32)
        }
        linearLayout.addView(tvSelectedDate)

        // 选择日期按钮
        btnSelectDate = Button(requireContext()).apply {
            text = "选择日期"
        }
        linearLayout.addView(btnSelectDate)

        return linearLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSelectDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        CalendarPickerHelper.showDatePicker(
            fragment = this,
            title = "Fragment日历选择",
            onDateSelected = { selectedDate ->
                val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                val dateString = formatter.format(selectedDate.time)
                tvSelectedDate.text = "Fragment选中: $dateString"

                Toast.makeText(requireContext(), "Fragment选择了: $dateString", Toast.LENGTH_SHORT).show()
            },
            onCancel = {
                Toast.makeText(requireContext(), "Fragment取消选择", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

/**
 * 在传统ListView/RecyclerView的Adapter中使用
 */
class MyListAdapter : android.widget.BaseAdapter() {
    // 简化的Adapter示例

    fun showDatePickerFromAdapter(context: Activity, position: Int) {
        CalendarPickerHelper.showDatePicker(
            activity = context,
            title = "选择第${position + 1}项的日期",
            onDateSelected = { selectedDate ->
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateString = formatter.format(selectedDate.time)

                // 更新数据
                // dataList[position].date = dateString
                // notifyDataSetChanged()

                Toast.makeText(context, "第${position + 1}项日期: $dateString", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // 其他必需的Adapter方法...
    override fun getCount(): Int = 0
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? = null
}

/**
 * 在自定义View中使用的示例
 */
class CustomDatePickerView(context: android.content.Context) : android.widget.LinearLayout(context) {

    private val btnSelectDate: Button
    private val tvSelectedDate: TextView

    init {
        orientation = VERTICAL
        setPadding(32, 32, 32, 32)

        tvSelectedDate = TextView(context).apply {
            text = "请选择日期"
            textSize = 16f
            setTextColor(Color.BLACK)
        }
        addView(tvSelectedDate)

        btnSelectDate = Button(context).apply {
            text = "选择日期"
            setOnClickListener {
                showDatePicker()
            }
        }
        addView(btnSelectDate)
    }

    private fun showDatePicker() {
        if (context is Activity) {
            CalendarPickerHelper.showDatePicker(
                activity = context as Activity,
                title = "自定义View日历",
                onDateSelected = { selectedDate ->
                    val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                    val dateString = formatter.format(selectedDate.time)
                    tvSelectedDate.text = "选中: $dateString"
                }
            )
        }
    }
}