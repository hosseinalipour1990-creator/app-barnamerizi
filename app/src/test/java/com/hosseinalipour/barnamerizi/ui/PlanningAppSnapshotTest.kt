package com.hosseinalipour.barnamerizi.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.hosseinalipour.barnamerizi.ui.theme.BarnameriziTheme
import org.junit.Rule
import org.junit.Test

class PlanningAppSnapshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false),
        theme = "android:style/Theme.Material.NoActionBar"
    )

    @Test
    fun dashboard() {
        paparazzi.snapshot(name = "dashboard") {
            BarnameriziTheme {
                PlanningApp()
            }
        }
    }
}
