package com.tecknobit.glider.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SettingsBRoll: ImageVector
    get() {
        if (_SettingsBRoll != null) {
            return _SettingsBRoll!!
        }
        _SettingsBRoll = ImageVector.Builder(
            name = "SettingsBRoll",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(370f, 880f)
                lineToRelative(-16f, -128f)
                quadToRelative(-13f, -5f, -24.5f, -12f)
                reflectiveQuadTo(307f, 725f)
                lineToRelative(-119f, 50f)
                lineTo(78f, 585f)
                lineToRelative(103f, -78f)
                quadToRelative(-1f, -7f, -1f, -13.5f)
                verticalLineToRelative(-27f)
                quadToRelative(0f, -6.5f, 1f, -13.5f)
                lineTo(78f, 375f)
                lineToRelative(110f, -190f)
                lineToRelative(119f, 50f)
                quadToRelative(11f, -8f, 23f, -15f)
                reflectiveQuadToRelative(24f, -12f)
                lineToRelative(16f, -128f)
                horizontalLineToRelative(220f)
                lineToRelative(16f, 128f)
                quadToRelative(13f, 5f, 24.5f, 12f)
                reflectiveQuadToRelative(22.5f, 15f)
                lineToRelative(119f, -50f)
                lineToRelative(110f, 190f)
                lineToRelative(-74f, 56f)
                quadToRelative(-22f, -11f, -45f, -18.5f)
                reflectiveQuadTo(714f, 402f)
                lineToRelative(63f, -48f)
                lineToRelative(-39f, -68f)
                lineToRelative(-99f, 42f)
                quadToRelative(-22f, -23f, -48.5f, -38.5f)
                reflectiveQuadTo(533f, 266f)
                lineToRelative(-13f, -106f)
                horizontalLineToRelative(-79f)
                lineToRelative(-14f, 106f)
                quadToRelative(-31f, 8f, -57.5f, 23.5f)
                reflectiveQuadTo(321f, 327f)
                lineToRelative(-99f, -41f)
                lineToRelative(-39f, 68f)
                lineToRelative(86f, 64f)
                quadToRelative(-5f, 15f, -7f, 30f)
                reflectiveQuadToRelative(-2f, 32f)
                quadToRelative(0f, 16f, 2f, 31f)
                reflectiveQuadToRelative(7f, 30f)
                lineToRelative(-86f, 65f)
                lineToRelative(39f, 68f)
                lineToRelative(99f, -42f)
                quadToRelative(17f, 17f, 36.5f, 30.5f)
                reflectiveQuadTo(400f, 685f)
                quadToRelative(1f, 57f, 23.5f, 107f)
                reflectiveQuadTo(484f, 880f)
                close()
                moveToRelative(41f, -279f)
                quadToRelative(6f, -20f, 14.5f, -38.5f)
                reflectiveQuadTo(445f, 527f)
                quadToRelative(-11f, -8f, -17f, -20.5f)
                reflectiveQuadToRelative(-6f, -26.5f)
                quadToRelative(0f, -25f, 17.5f, -42.5f)
                reflectiveQuadTo(482f, 420f)
                quadToRelative(14f, 0f, 27f, 6.5f)
                reflectiveQuadToRelative(21f, 17.5f)
                quadToRelative(17f, -11f, 35f, -19.5f)
                reflectiveQuadToRelative(38f, -13.5f)
                quadToRelative(-18f, -32f, -50f, -51.5f)
                reflectiveQuadTo(482f, 340f)
                quadToRelative(-59f, 0f, -99.5f, 41f)
                reflectiveQuadTo(342f, 480f)
                quadToRelative(0f, 38f, 18.5f, 70.5f)
                reflectiveQuadTo(411f, 601f)
                moveToRelative(269f, 199f)
                lineToRelative(120f, -120f)
                lineToRelative(-120f, -120f)
                lineToRelative(-28f, 28f)
                lineToRelative(72f, 72f)
                horizontalLineTo(560f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(163f)
                lineToRelative(-71f, 72f)
                close()
                moveToRelative(0f, 80f)
                quadToRelative(-83f, 0f, -141.5f, -58.5f)
                reflectiveQuadTo(480f, 680f)
                reflectiveQuadToRelative(58.5f, -141.5f)
                reflectiveQuadTo(680f, 480f)
                reflectiveQuadToRelative(141.5f, 58.5f)
                reflectiveQuadTo(880f, 680f)
                reflectiveQuadToRelative(-58.5f, 141.5f)
                reflectiveQuadTo(680f, 880f)
            }
        }.build()
        return _SettingsBRoll!!
    }

private var _SettingsBRoll: ImageVector? = null
