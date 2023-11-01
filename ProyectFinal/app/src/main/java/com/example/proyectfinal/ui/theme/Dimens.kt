package com.example.proyectfinal.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val extraSmall: Dp = 0.dp,
    val small1: Dp = 0.dp,
    val small2: Dp = 0.dp,
    val small3: Dp = 0.dp,
    val medium1: Dp = 0.dp,
    val medium2: Dp = 0.dp,
    val medium3: Dp = 0.dp,
    val large: Dp = 0.dp,
    val buttonHeight: Dp = 40.dp
)

val CompactSmallDimens = Dimens(
    small1 = 4.dp,
    small2 = 6.dp,
    small3 = 8.dp,
    medium1 = 12.dp,
    medium2 = 15.dp,
    medium3 = 18.dp,
    large = 21.dp,
    buttonHeight = 30.dp
)

val CompactMediumDimens = Dimens(
    small1 = 10.dp,
    small2 = 12.dp,
    small3 = 14.dp,
    medium1 = 21.dp,
    medium2 = 24.dp,
    medium3 = 27.dp,
    large = 65.dp
)

val CompactDimens = Dimens(
    small1 = 14.dp,
    small2 = 16.dp,
    small3 = 18.dp,
    medium1 = 24.dp,
    medium2 = 28.dp,
    medium3 = 34.dp,
    large = 80.dp
)

val MediumDimens = Dimens(
    small1 = 10.dp,
    small2 = 15.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 40.dp,
    large = 110.dp
)

val ExpandedDimens = Dimens (
    small1 = 15.dp,
    small2 = 20.dp,
    small3 = 25.dp,
    medium1 = 35.dp,
    medium2 = 30.dp,
    medium3 = 45.dp,
    large = 130.dp
)