package com.example.proyectfinal.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
2  (1-2 dp's)
5  (4-6 dp's)
10 (8-10 dp's)
20 (20-25 dp's)
40 (40-45 dp's)
65 (50-80 dp's)
120
 */

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
    small1 = 2.dp,
    small2 = 5.dp,
    small3 = 10.dp,
    medium1 = 20.dp,
    medium2 = 40.dp,
    medium3 = 65.dp,
    large = 120.dp,
    buttonHeight = 30.dp
)

val CompactMediumDimens = Dimens(
    small1 = 3.dp,
    small2 = 6.dp,
    small3 = 12.dp,
    medium1 = 24.dp,
    medium2 = 45.dp,
    medium3 = 70.dp,
    large = 140.dp
)

val CompactDimens = Dimens(
    small1 = 5.dp,
    small2 = 9.dp,
    small3 = 15.dp,
    medium1 = 28.dp,
    medium2 = 53.dp,
    medium3 = 80.dp,
    large = 160.dp
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