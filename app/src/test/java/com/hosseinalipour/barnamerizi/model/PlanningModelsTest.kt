package com.hosseinalipour.barnamerizi.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PlanningModelsTest {
    @Test
    fun completionRatio_returnsCompletedShare() {
        val tasks = listOf(
            PlannerTask(1, "اول", "۹:۰۰", TaskCategory.WORK, isDone = true),
            PlannerTask(2, "دوم", "۱۰:۰۰", TaskCategory.PERSONAL),
            PlannerTask(3, "سوم", "۱۱:۰۰", TaskCategory.STUDY, isDone = true),
            PlannerTask(4, "چهارم", "۱۲:۰۰", TaskCategory.HEALTH)
        )

        assertEquals(0.5f, completionRatio(tasks), 0.001f)
    }

    @Test
    fun completionRatio_handlesEmptyList() {
        assertEquals(0f, completionRatio(emptyList()), 0.001f)
    }
}
