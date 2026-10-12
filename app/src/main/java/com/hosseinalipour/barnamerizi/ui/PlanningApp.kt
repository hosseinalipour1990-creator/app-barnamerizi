package com.hosseinalipour.barnamerizi.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.TrackChanges
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hosseinalipour.barnamerizi.model.Goal
import com.hosseinalipour.barnamerizi.model.Habit
import com.hosseinalipour.barnamerizi.model.PlannerTask
import com.hosseinalipour.barnamerizi.model.SampleData
import com.hosseinalipour.barnamerizi.model.TaskCategory
import com.hosseinalipour.barnamerizi.model.TaskPriority
import com.hosseinalipour.barnamerizi.model.completionRatio
import com.hosseinalipour.barnamerizi.ui.theme.Amber
import com.hosseinalipour.barnamerizi.ui.theme.DeepSurface
import com.hosseinalipour.barnamerizi.ui.theme.ElevatedSurface
import com.hosseinalipour.barnamerizi.ui.theme.Ink
import com.hosseinalipour.barnamerizi.ui.theme.Mint
import com.hosseinalipour.barnamerizi.ui.theme.MintContainer
import com.hosseinalipour.barnamerizi.ui.theme.Rose
import com.hosseinalipour.barnamerizi.ui.theme.Sky
import com.hosseinalipour.barnamerizi.ui.theme.SoftBorder
import com.hosseinalipour.barnamerizi.ui.theme.TextPrimary
import com.hosseinalipour.barnamerizi.ui.theme.TextSecondary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private enum class AppDestination(val title: String, val icon: ImageVector) {
    TODAY("امروز", Icons.Rounded.Home),
    TASKS("کارها", Icons.Rounded.Checklist),
    HABITS("عادت‌ها", Icons.Rounded.TrackChanges),
    GOALS("هدف‌ها", Icons.Rounded.Flag)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanningApp() {
    CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides LayoutDirection.Rtl) {
        var currentDestination by rememberSaveable { mutableStateOf(AppDestination.TODAY) }
        var showAddTask by rememberSaveable { mutableStateOf(false) }
        val tasks = remember { mutableStateListOf<PlannerTask>().apply { addAll(SampleData.tasks) } }
        val habits = remember { mutableStateListOf<Habit>().apply { addAll(SampleData.habits) } }

        Scaffold(
            containerColor = Ink,
            bottomBar = {
                AppNavigationBar(
                    selected = currentDestination,
                    onSelected = { currentDestination = it }
                )
            },
            floatingActionButton = {
                if (currentDestination == AppDestination.TODAY || currentDestination == AppDestination.TASKS) {
                    FloatingActionButton(
                        onClick = { showAddTask = true },
                        containerColor = Mint,
                        contentColor = Ink,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = "افزودن کار")
                    }
                }
            }
        ) { innerPadding ->
            Crossfade(
                targetState = currentDestination,
                modifier = Modifier.padding(innerPadding),
                label = "main-navigation"
            ) { destination ->
                when (destination) {
                    AppDestination.TODAY -> DashboardScreen(tasks, ::toggleTask)
                    AppDestination.TASKS -> TasksScreen(tasks, ::toggleTask)
                    AppDestination.HABITS -> HabitsScreen(habits) { habitId, day ->
                        val index = habits.indexOfFirst { it.id == habitId }
                        if (index >= 0) {
                            val habit = habits[index]
                            val days = habit.completedDays.toMutableSet().apply {
                                if (!add(day)) remove(day)
                            }
                            habits[index] = habit.copy(completedDays = days)
                        }
                    }
                    AppDestination.GOALS -> GoalsScreen(SampleData.goals)
                }
            }
        }

        if (showAddTask) {
            AddTaskSheet(
                onDismiss = { showAddTask = false },
                onAdd = { title, category ->
                    tasks.add(
                        0,
                        PlannerTask(
                            id = (tasks.maxOfOrNull { it.id } ?: 0L) + 1L,
                            title = title,
                            time = "بدون ساعت",
                            category = category
                        )
                    )
                    showAddTask = false
                }
            )
        }
    }
}

private fun toggleTask(tasks: MutableList<PlannerTask>, id: Long) {
    val index = tasks.indexOfFirst { it.id == id }
    if (index >= 0) tasks[index] = tasks[index].copy(isDone = !tasks[index].isDone)
}

@Composable
private fun AppNavigationBar(selected: AppDestination, onSelected: (AppDestination) -> Unit) {
    NavigationBar(
        containerColor = DeepSurface,
        tonalElevation = 0.dp,
        modifier = Modifier.drawBehind {
            drawLine(SoftBorder, Offset(0f, 0f), Offset(size.width, 0f), 1.dp.toPx())
        }
    ) {
        AppDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = selected == destination,
                onClick = { onSelected(destination) },
                icon = { Icon(destination.icon, contentDescription = destination.title) },
                label = { Text(destination.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Mint,
                    selectedTextColor = Mint,
                    indicatorColor = MintContainer,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}

@Composable
private fun DashboardScreen(tasks: MutableList<PlannerTask>, onToggleTask: (MutableList<PlannerTask>, Long) -> Unit) {
    val completed = tasks.count { it.isDone }
    val today = remember { LocalDate.now() }
    val formattedDate = remember(today) {
        today.format(DateTimeFormatter.ofPattern("EEEE، d MMMM", Locale("fa"))).toPersianDigits()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item { DashboardHeader(formattedDate) }
        item { WeekStrip(today) }
        item { ProgressHero(completed, tasks.size, completionRatio(tasks)) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("تمرکز امروز", "۲ ساعت و ۱۰ دقیقه", Icons.Rounded.Timer, Sky, Modifier.weight(1f))
                MetricCard("زنجیرهٔ عادت", "۱۲ روز پیوسته", Icons.Rounded.LocalFireDepartment, Amber, Modifier.weight(1f))
            }
        }
        item { SectionHeader("کارهای امروز", "مشاهده همه") }
        items(tasks.take(4), key = { it.id }) { task ->
            TaskRow(task = task, onToggle = { onToggleTask(tasks, task.id) })
        }
        item { SectionHeader("برنامهٔ زمانی", "تقویم") }
        item { ScheduleCard() }
        item { Spacer(Modifier.height(4.dp)) }
    }
}

@Composable
private fun DashboardHeader(formattedDate: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text("وقت بخیر", color = TextSecondary, style = MaterialTheme.typography.bodyLarge)
            Text("امروز را هدفمند بساز", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Spacer(Modifier.height(4.dp))
            Text(formattedDate, color = Mint, style = MaterialTheme.typography.labelLarge)
        }
        FilledIconButton(
            onClick = {},
            colors = androidx.compose.material3.IconButtonDefaults.filledIconButtonColors(
                containerColor = ElevatedSurface,
                contentColor = TextPrimary
            )
        ) {
            Icon(Icons.Rounded.NotificationsNone, contentDescription = "اعلان‌ها")
        }
    }
}

@Composable
private fun WeekStrip(today: LocalDate) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (-3..3).forEach { offset ->
            val date = today.plusDays(offset.toLong())
            val selected = offset == 0
            Column(
                modifier = Modifier
                    .width(43.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selected) Mint else Color.Transparent)
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    date.format(DateTimeFormatter.ofPattern("EE", Locale("fa"))),
                    color = if (selected) Ink else TextSecondary,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    date.dayOfMonth.toString().toPersianDigits(),
                    color = if (selected) Ink else TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ProgressHero(completed: Int, total: Int, progress: Float) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MintContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressRing(progress)
            Spacer(Modifier.width(20.dp))
            Column(Modifier.weight(1f)) {
                Text("پیشرفت امروز", color = Mint, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(7.dp))
                Text("$completed از $total کار انجام شده".toPersianDigits(), color = TextPrimary, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(5.dp))
                Text("قدم‌های کوچک، نتیجه‌های بزرگ می‌سازند.", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ProgressRing(progress: Float) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(82.dp)) {
        Canvas(Modifier.fillMaxSize()) {
            drawArc(
                color = SoftBorder,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(9.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = Mint,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(9.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text("${(progress * 100).toInt()}٪".toPersianDigits(), color = TextPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MetricCard(title: String, value: String, icon: ImageVector, accent: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = DeepSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftBorder)
    ) {
        Column(Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(accent.copy(alpha = 0.13f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(13.dp))
            Text(value, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
            Text(title, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(title, color = TextPrimary, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
        if (action != null) TextButton(onClick = {}) { Text(action, color = Mint) }
    }
}

@Composable
private fun TaskRow(task: PlannerTask, onToggle: () -> Unit) {
    val background by animateColorAsState(
        if (task.isDone) DeepSurface.copy(alpha = 0.62f) else DeepSurface,
        label = "task-color"
    )
    val categoryColor = when (task.category) {
        TaskCategory.WORK -> Sky
        TaskCategory.PERSONAL -> Rose
        TaskCategory.STUDY -> Amber
        TaskCategory.HEALTH -> Mint
    }
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle),
        colors = CardDefaults.cardColors(containerColor = background),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftBorder),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = Mint,
                    checkmarkColor = Ink,
                    uncheckedColor = TextSecondary
                )
            )
            Column(Modifier.weight(1f).padding(horizontal = 5.dp)) {
                Text(
                    task.title,
                    color = if (task.isDone) TextSecondary else TextPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else null,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(7.dp).clip(CircleShape).background(categoryColor))
                    Spacer(Modifier.width(6.dp))
                    Text(task.category.title, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                    Text("  •  ${task.time}", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                }
            }
            if (task.priority == TaskPriority.HIGH) {
                Text("مهم", color = Rose, style = MaterialTheme.typography.labelMedium)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Rounded.MoreVert, contentDescription = "گزینه‌ها", tint = TextSecondary)
            }
        }
    }
}

@Composable
private fun ScheduleCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = DeepSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftBorder),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ScheduleItem("۱۰:۰۰", "جلسهٔ برنامه‌ریزی هفتگی", "۴۵ دقیقه", Sky)
            HorizontalDivider(color = SoftBorder)
            ScheduleItem("۱۶:۰۰", "زمان تمرکز بدون وقفه", "۹۰ دقیقه", Mint)
        }
    }
}

@Composable
private fun ScheduleItem(time: String, title: String, duration: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(56.dp)) {
            Text(time, color = color, fontWeight = FontWeight.Bold)
            Text(duration, color = TextSecondary, fontSize = 10.sp)
        }
        Box(Modifier.width(3.dp).height(42.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(14.dp))
        Text(title, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun TasksScreen(tasks: MutableList<PlannerTask>, onToggleTask: (MutableList<PlannerTask>, Long) -> Unit) {
    var selectedFilter by rememberSaveable { mutableStateOf("همه") }
    val filteredTasks = when (selectedFilter) {
        "انجام‌شده" -> tasks.filter { it.isDone }
        "در انتظار" -> tasks.filterNot { it.isDone }
        "مهم" -> tasks.filter { it.priority == TaskPriority.HIGH }
        else -> tasks
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { ScreenTitle("کارها", "فهرست کارهای امروز و آینده", Icons.Rounded.Checklist) }
        item {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("همه", "در انتظار", "مهم", "انجام‌شده").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MintContainer,
                            selectedLabelColor = Mint,
                            containerColor = DeepSurface,
                            labelColor = TextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedFilter == filter,
                            borderColor = SoftBorder,
                            selectedBorderColor = Mint.copy(alpha = 0.4f)
                        )
                    )
                }
            }
        }
        item {
            Text("${filteredTasks.size} کار".toPersianDigits(), color = TextSecondary, style = MaterialTheme.typography.labelLarge)
        }
        items(filteredTasks, key = { it.id }) { task ->
            TaskRow(task = task, onToggle = { onToggleTask(tasks, task.id) })
        }
    }
}

@Composable
private fun ScreenTitle(title: String, subtitle: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(16.dp)).background(MintContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Mint)
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(title, color = TextPrimary, style = MaterialTheme.typography.headlineMedium)
            Text(subtitle, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun HabitsScreen(habits: List<Habit>, onToggleDay: (Long, Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { ScreenTitle("عادت‌ها", "پیوستگی مهم‌تر از بی‌نقص‌بودن است", Icons.Rounded.TrackChanges) }
        item { HabitSummary(habits) }
        item { SectionHeader("این هفته") }
        items(habits, key = { it.id }) { habit ->
            HabitCard(habit, onToggleDay)
        }
    }
}

@Composable
private fun HabitSummary(habits: List<Habit>) {
    val done = habits.count { 5 in it.completedDays }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = ElevatedSurface)
    ) {
        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Amber, modifier = Modifier.size(42.dp))
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text("ریتم خوبت را حفظ کن", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
                Text("امروز $done از ${habits.size} عادت ثبت شده".toPersianDigits(), color = TextSecondary)
            }
        }
    }
}

@Composable
private fun HabitCard(habit: Habit, onToggleDay: (Long, Int) -> Unit) {
    val icon = when (habit.icon) {
        "آب" -> Icons.Rounded.WaterDrop
        "کتاب" -> Icons.Rounded.AutoStories
        "ورزش" -> Icons.Rounded.SelfImprovement
        else -> Icons.Rounded.CheckCircle
    }
    val dayNames = listOf("ش", "ی", "د", "س", "چ", "پ", "ج")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftBorder),
        colors = CardDefaults.cardColors(containerColor = DeepSurface)
    ) {
        Column(Modifier.padding(17.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(MintContainer),
                    contentAlignment = Alignment.Center
                ) { Icon(icon, contentDescription = null, tint = Mint) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(habit.title, color = TextPrimary, style = MaterialTheme.typography.titleMedium)
                    Text(habit.subtitle, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                }
                Icon(Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Amber, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("${habit.streak} روز".toPersianDigits(), color = Amber, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(Modifier.height(18.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                dayNames.forEachIndexed { index, name ->
                    val checked = index in habit.completedDays
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(name, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(7.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(if (checked) Mint else ElevatedSurface)
                                .clickable { onToggleDay(habit.id, index) },
                            contentAlignment = Alignment.Center
                        ) {
                            if (checked) Icon(Icons.Rounded.CheckCircle, null, tint = Ink, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalsScreen(goals: List<Goal>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 104.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { ScreenTitle("هدف‌ها", "تصویر بزرگ را فراموش نکن", Icons.Rounded.Flag) }
        item { GoalFocusCard() }
        item { SectionHeader("هدف‌های فعال", "${goals.size} هدف".toPersianDigits()) }
        items(goals, key = { it.id }) { goal -> GoalCard(goal) }
    }
}

@Composable
private fun GoalFocusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MintContainer),
        shape = RoundedCornerShape(26.dp)
    ) {
        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("تمرکز این ماه", color = Mint, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(4.dp))
                Text("ساختن یک سبک زندگی پایدار", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(6.dp))
                Text("هر هفته یک بازبینی کوتاه انجام بده.", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(Icons.Rounded.DarkMode, contentDescription = null, tint = Mint, modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun GoalCard(goal: Goal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DeepSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, SoftBorder),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(goal.title, color = TextPrimary, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                Text("${(goal.progress * 100).toInt()}٪".toPersianDigits(), color = Mint, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(6.dp))
            Text(goal.subtitle, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(14.dp))
            LinearProgressIndicator(
                progress = { goal.progress },
                modifier = Modifier.fillMaxWidth().height(7.dp).clip(CircleShape),
                color = Mint,
                trackColor = ElevatedSurface,
                strokeCap = StrokeCap.Round
            )
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.CalendarMonth, null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(5.dp))
                Text(goal.deadline, color = TextSecondary, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskSheet(onDismiss: () -> Unit, onAdd: (String, TaskCategory) -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(TaskCategory.PERSONAL) }
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DeepSurface,
        contentColor = TextPrimary,
        dragHandle = null
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 22.dp)
                .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("کار تازه", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = onDismiss) { Icon(Icons.Rounded.Close, contentDescription = "بستن") }
            }
            Spacer(Modifier.height(18.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("عنوان کار") },
                placeholder = { Text("مثلاً مرور برنامهٔ فردا") },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            Spacer(Modifier.height(18.dp))
            Text("دسته‌بندی", color = TextSecondary, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskCategory.entries.forEach { item ->
                    FilterChip(
                        selected = category == item,
                        onClick = { category = item },
                        label = { Text(item.title) }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { if (title.isNotBlank()) onAdd(title.trim(), category) },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Rounded.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("افزودن به برنامه")
            }
        }
    }
}

private fun String.toPersianDigits(): String = buildString(length) {
    this@toPersianDigits.forEach { char ->
        append(if (char in '0'..'9') "۰۱۲۳۴۵۶۷۸۹"[char - '0'] else char)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF090D12, locale = "fa")
@Composable
private fun PlanningAppPreview() {
    com.hosseinalipour.barnamerizi.ui.theme.BarnameriziTheme { PlanningApp() }
}
