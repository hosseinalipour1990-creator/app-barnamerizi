package com.hosseinalipour.barnamerizi.model

enum class TaskCategory(val title: String) {
    WORK("کار"), PERSONAL("شخصی"), STUDY("یادگیری"), HEALTH("سلامتی")
}

enum class TaskPriority {
    HIGH, NORMAL, LOW
}

data class PlannerTask(
    val id: Long,
    val title: String,
    val time: String,
    val category: TaskCategory,
    val priority: TaskPriority = TaskPriority.NORMAL,
    val isDone: Boolean = false
)

data class Habit(
    val id: Long,
    val title: String,
    val subtitle: String,
    val streak: Int,
    val completedDays: Set<Int>,
    val icon: String
)

data class Goal(
    val id: Long,
    val title: String,
    val subtitle: String,
    val progress: Float,
    val deadline: String
)

object SampleData {
    val tasks = listOf(
        PlannerTask(1, "مرور برنامهٔ فصل جدید", "۹:۳۰", TaskCategory.WORK, TaskPriority.HIGH),
        PlannerTask(2, "۳۰ دقیقه مطالعهٔ کتاب", "۱۲:۰۰", TaskCategory.STUDY),
        PlannerTask(3, "پیاده‌روی عصرگاهی", "۱۸:۳۰", TaskCategory.HEALTH),
        PlannerTask(4, "تماس با خانواده", "۲۰:۰۰", TaskCategory.PERSONAL, isDone = true),
        PlannerTask(5, "مرتب‌کردن یادداشت‌های امروز", "۲۱:۳۰", TaskCategory.PERSONAL)
    )

    val habits = listOf(
        Habit(1, "آب کافی", "۸ لیوان در روز", 12, setOf(0, 1, 2, 3, 4, 5), "آب"),
        Habit(2, "مطالعه", "حداقل ۳۰ دقیقه", 7, setOf(0, 1, 2, 4, 5), "کتاب"),
        Habit(3, "ورزش", "تمرین روزانه", 4, setOf(0, 2, 4, 5), "ورزش")
    )

    val goals = listOf(
        Goal(1, "تکمیل دورهٔ طراحی محصول", "۸ از ۱۲ جلسه", 0.67f, "تا ۲۸ مهر"),
        Goal(2, "مطالعهٔ ۱۲ کتاب", "۷ کتاب تمام‌شده", 0.58f, "تا پایان سال"),
        Goal(3, "پس‌انداز سفر", "۱۸ از ۳۰ میلیون", 0.60f, "تا ۱۵ دی")
    )
}

fun completionRatio(tasks: List<PlannerTask>): Float {
    if (tasks.isEmpty()) return 0f
    return tasks.count { it.isDone }.toFloat() / tasks.size
}
