package com.example.navhigh.ui.birthday

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.graphics.drawable.toDrawable
import com.example.navhigh.R
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.DateOfBirthTextField
import com.example.navhigh.ui.theme.AppDimensions
import com.example.navhigh.ui.theme.AppTypography
import com.example.navhigh.ui.theme.BirthdayBgBlack
import com.example.navhigh.ui.theme.BirthdayCyanAccent
import com.example.navhigh.ui.theme.BirthdayDialogBg
import com.example.navhigh.ui.theme.BirthdayFieldBorder
import com.example.navhigh.ui.theme.BirthdayTextGrey
import com.example.navhigh.ui.theme.BirthdayTextWhite
import com.example.navhigh.ui.theme.CircularRepeatCount
import com.example.navhigh.ui.theme.DatePickerMaxYear
import com.example.navhigh.ui.theme.DatePickerMinYear
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import com.example.navhigh.ui.theme.FullWeight
import com.example.navhigh.ui.theme.WheelAlphaDistanceFactor
import com.example.navhigh.ui.theme.WheelAlphaMax
import com.example.navhigh.ui.theme.WheelAlphaMin
import com.example.navhigh.ui.theme.WheelCenterThreshold
import com.example.navhigh.ui.theme.WheelDayWeight
import com.example.navhigh.ui.theme.WheelDistanceMaxRows
import com.example.navhigh.ui.theme.WheelDistanceMinRows
import com.example.navhigh.ui.theme.WheelMonthWeight
import com.example.navhigh.ui.theme.WheelYearWeight
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.abs
import android.graphics.Color as AndroidColor

private fun calculateAge(year: Int, month: Int, day: Int): Int {
    val today = Calendar.getInstance()
    val birth = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    var age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)

    val birthdayNotYetOccurredThisYear =
        today.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH))

    if (birthdayNotYetOccurredThisYear) age -= 1

    return age.coerceAtLeast(0)
}

@Composable
fun BirthdayScreen(
    onBack: () -> Unit = {},
    onNext: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> },
    onLogin: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    val monthNames = stringArrayResource(R.array.month_names)

    val today = remember { Calendar.getInstance() }
    var birthdayYear by remember { mutableIntStateOf(today.get(Calendar.YEAR)) }
    var birthdayMonth by remember { mutableIntStateOf(today.get(Calendar.MONTH)) }
    var birthdayDay by remember { mutableIntStateOf(today.get(Calendar.DAY_OF_MONTH)) }

    var showPicker by remember { mutableStateOf(true) }
    var showInfoSheet by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val contentWidth: Dp =
        if (isTablet) AppDimensions.PasswordTabletContentWidth else Dp.Unspecified

    val formattedBirthday = remember(birthdayYear, birthdayMonth, birthdayDay) {
        "$birthdayDay ${monthNames[birthdayMonth]} $birthdayYear"
    }

    val age = remember(birthdayYear, birthdayMonth, birthdayDay) {
        calculateAge(birthdayYear, birthdayMonth, birthdayDay)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BirthdayBgBlack)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .then(
                    if (isTablet) {
                        Modifier.width(contentWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .padding(horizontal = AppDimensions.ScreenPadding)
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(AppDimensions.TopSpace))

            BackArrow(onClick = { onBack() })

            Spacer(modifier = Modifier.height(AppDimensions.EmailBackArrowSpacing))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(
                            text = stringResource(R.string.birthday_title_part1),
                            color = BirthdayTextWhite
                        ),
                        TitlePart(
                            text = stringResource(R.string.birthday_title_part2),
                            color = ForgotPasswordBlue
                        ),
                        TitlePart(
                            text = stringResource(R.string.birthday_title_part3),
                            color = BirthdayTextWhite
                        )
                    )
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailDescriptionSpacing))

            val whyLinkText = stringResource(R.string.birthday_why_link)
            val descriptionText = buildAnnotatedString {
                append(stringResource(R.string.birthday_description))
                withLink(
                    LinkAnnotation.Clickable(
                        tag = "WHY",
                        styles = TextLinkStyles(style = SpanStyle(color = BirthdayCyanAccent))
                    ) {
                        showInfoSheet = true
                    }
                ) {
                    append(whyLinkText)
                }
            }

            Text(
                text = descriptionText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = BirthdayTextGrey,
                    fontSize = AppTypography.BirthdayDescriptionTextSize,
                    lineHeight = AppTypography.EmailDescriptionLineHeight
                )
            )

            Spacer(modifier = Modifier.height(AppDimensions.EmailTextFieldSpacing))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                DateOfBirthTextField(
                    value = formattedBirthday,
                    onValueChange = {},
                    age = age
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showPicker = true }
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.EmailButtonSpacing))

            Button(
                text = stringResource(R.string.birthday_next_button),
                onClick = { onNext(birthdayYear, birthdayMonth, birthdayDay) }
            )

            Spacer(modifier = Modifier.weight(FullWeight))
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimensions.EmailScreenHorizontalPadding,
                    vertical = AppDimensions.EmailScreenVerticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlreadyHaveAccount(
                onLogin = { onLogin() },
                onContinue = { onContinue() }
            )
        }
    }

    if (showPicker) {
        DateWheelPickerDialog(
            initialDay = birthdayDay,
            initialMonth = birthdayMonth,
            initialYear = birthdayYear,
            onDismiss = { showPicker = false },
            onConfirm = { day, month, year ->
                birthdayDay = day
                birthdayMonth = month
                birthdayYear = year
                showPicker = false
            }
        )
    }

    if (showInfoSheet) {
        BirthdayInfoBottomSheet(
            onDismiss = { showInfoSheet = false }
        )
    }
}

@Composable
private fun DateWheelPickerDialog(
    initialDay: Int,
    initialMonth: Int,
    initialYear: Int,
    onDismiss: () -> Unit,
    onConfirm: (day: Int, month: Int, year: Int) -> Unit
) {
    val monthAbbr = stringArrayResource(R.array.month_abbr)

    val days = remember { (1..31).map { it.toString() } }
    val months = remember(monthAbbr) { monthAbbr.toList() }
    val years = remember { (DatePickerMinYear..DatePickerMaxYear).map { it.toString() } }

    var selectedDayIndex by remember {
        mutableIntStateOf(
            (initialDay - 1).coerceIn(
                0,
                days.lastIndex
            )
        )
    }
    var selectedMonthIndex by remember {
        mutableIntStateOf(
            initialMonth.coerceIn(
                0,
                months.lastIndex
            )
        )
    }
    var selectedYearIndex by remember {
        mutableIntStateOf(
            years.indexOf(initialYear.toString()).let { if (it >= 0) it else years.lastIndex })
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        val view = LocalView.current
        SideEffect {
            (view.parent as? DialogWindowProvider)?.window?.setBackgroundDrawable(
                AndroidColor.TRANSPARENT.toDrawable()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.DatePickerHorizontalPadding),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = BirthdayDialogBg,
                modifier = Modifier.fillMaxWidth(AppDimensions.DatePickerSurfaceWidthFraction)
            ) {
                Column(modifier = Modifier.padding(AppDimensions.DatePickerContentPadding)) {
                    Text(
                        text = stringResource(R.string.date_picker_title),
                        color = BirthdayTextWhite,
                        fontSize = AppTypography.DatePickerTitleTextSize,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(AppDimensions.DatePickerTitleSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppDimensions.DatePickerWheelSpacing)
                    ) {
                        WheelColumn(
                            items = days,
                            selectedIndex = selectedDayIndex,
                            onSelectedIndexChange = { selectedDayIndex = it },
                            modifier = Modifier.weight(WheelDayWeight),
                            isCircular = true,
                            isNumeric = true,
                            onTypedValue = { typed ->
                                val clamped = typed.coerceIn(1, days.size)
                                selectedDayIndex = clamped - 1
                            }
                        )
                        WheelColumn(
                            items = months,
                            selectedIndex = selectedMonthIndex,
                            onSelectedIndexChange = { selectedMonthIndex = it },
                            modifier = Modifier.weight(WheelMonthWeight),
                            isCircular = true,
                            isNumeric = false
                        )
                        WheelColumn(
                            items = years,
                            selectedIndex = selectedYearIndex,
                            onSelectedIndexChange = { selectedYearIndex = it },
                            modifier = Modifier.weight(WheelYearWeight),
                            isCircular = false,
                            isNumeric = true,
                            onTypedValue = { typed ->
                                val minYear = years.first().toInt()
                                val maxYear = years.last().toInt()
                                val clamped = typed.coerceIn(minYear, maxYear)
                                selectedYearIndex = years.indexOf(clamped.toString())
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimensions.DatePickerActionsSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(R.string.date_picker_cancel),
                            color = BirthdayCyanAccent,
                            fontSize = AppTypography.DatePickerActionTextSize,
                            modifier = Modifier
                                .clickable { onDismiss() }
                                .padding(AppDimensions.DatePickerActionPadding)
                        )
                        Spacer(modifier = Modifier.width(AppDimensions.DatePickerCancelSetSpacing))
                        Text(
                            text = stringResource(R.string.date_picker_set),
                            color = BirthdayCyanAccent,
                            fontSize = AppTypography.DatePickerActionTextSize,
                            modifier = Modifier
                                .clickable {
                                    onConfirm(
                                        selectedDayIndex + 1,
                                        selectedMonthIndex,
                                        years[selectedYearIndex].toInt()
                                    )
                                }
                                .padding(AppDimensions.DatePickerActionPadding)
                        )
                        Spacer(modifier = Modifier.width(AppDimensions.DatePickerActionEndSpacing))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WheelColumn(
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isCircular: Boolean = false,
    isNumeric: Boolean = false,
    onTypedValue: (Int) -> Unit = {}
) {
    val itemHeight = AppDimensions.WheelItemHeight
    val visibleCount = AppDimensions.WheelVisibleCount
    val itemCount = items.size
    val virtualCount = if (isCircular) itemCount * CircularRepeatCount else itemCount
    val initialVirtualIndex = remember {
        if (isCircular) {
            val half = (CircularRepeatCount / 2) * itemCount
            half + selectedIndex
        } else {
            selectedIndex
        }
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialVirtualIndex)
    val flingBehavior = rememberSnapFlingBehavior(listState)
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isEditing by remember { mutableStateOf(false) }
    var editingText by remember { mutableStateOf("") }

    fun actualIndexOf(virtualIndex: Int): Int =
        if (isCircular) ((virtualIndex % itemCount) + itemCount) % itemCount
        else virtualIndex.coerceIn(0, itemCount - 1)

    LaunchedEffect(selectedIndex) {
        if (!listState.isScrollInProgress) {
            val currentVirtual = closestItemIndexToCenter(listState)
            val currentActual = actualIndexOf(currentVirtual)
            if (currentActual != selectedIndex) {
                val targetVirtual = if (isCircular) {
                    currentVirtual + (selectedIndex - currentActual)
                } else {
                    selectedIndex
                }
                listState.animateScrollToItem(targetVirtual)
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { scrolling ->
                if (!scrolling) {
                    val centerVirtual = closestItemIndexToCenter(listState)
                    onSelectedIndexChange(actualIndexOf(centerVirtual))
                }
            }
    }

    Box(
        modifier = modifier.height(itemHeight * visibleCount),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2)),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(count = virtualCount, key = { it }) { virtualIndex ->
                val actualIndex = actualIndexOf(virtualIndex)
                val label = items[actualIndex]

                val itemHeightPx = with(density) { itemHeight.toPx() }
                val layoutInfo = listState.layoutInfo
                val viewportCenter =
                    (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2f
                val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == virtualIndex }
                val itemCenter = itemInfo?.let { it.offset + it.size / 2f } ?: viewportCenter
                val distancePx = abs(itemCenter - viewportCenter)
                val distanceInRows = (distancePx / itemHeightPx).coerceIn(
                    WheelDistanceMinRows,
                    WheelDistanceMaxRows
                )
                val alpha = (WheelAlphaMax - distanceInRows * WheelAlphaDistanceFactor)
                    .coerceIn(WheelAlphaMin, WheelAlphaMax)
                val fontSize =
                    (AppTypography.WheelMaxFontSize - distanceInRows * AppTypography.WheelFontSizeDistanceFactor)
                        .coerceIn(AppTypography.WheelMinFontSize, AppTypography.WheelMaxFontSize)
                val isCenter = distanceInRows < WheelCenterThreshold

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer { this.alpha = alpha }
                        .clickable(enabled = !isEditing) {
                            if (isCenter && isNumeric) {
                                editingText = label
                                isEditing = true
                            } else {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(virtualIndex)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isCenter && isEditing) {
                        BasicTextField(
                            value = editingText,
                            onValueChange = { new ->
                                if (new.length <= 4 && new.all { it.isDigit() }) {
                                    editingText = new
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                color = BirthdayTextWhite,
                                fontSize = AppTypography.WheelEditTextSize,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    editingText.toIntOrNull()?.let(onTypedValue)
                                    isEditing = false
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .width(AppDimensions.WheelEditFieldWidth)
                                .focusRequester(focusRequester)
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused && isEditing) {
                                        editingText.toIntOrNull()?.let(onTypedValue)
                                        isEditing = false
                                    }
                                }
                        )
                        LaunchedEffect(Unit) { focusRequester.requestFocus() }
                    } else {
                        Text(
                            text = label,
                            color = if (isCenter) BirthdayTextWhite else BirthdayTextGrey,
                            fontSize = fontSize.sp,
                            fontWeight = if (isCenter) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Selector lines (top and bottom of the center row) — no offset used.
        Box(
            modifier = Modifier
                .fillMaxWidth(AppDimensions.WheelSelectorLineWidthFraction)
                .height(itemHeight)
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.WheelSelectorLineHeight)
                    .align(Alignment.TopCenter)
                    .background(BirthdayFieldBorder)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.WheelSelectorLineHeight)
                    .align(Alignment.BottomCenter)
                    .background(BirthdayFieldBorder)
            )
        }
    }
}

/** Finds the (virtual) index of the item whose center sits closest to the viewport's center. */
private fun closestItemIndexToCenter(listState: LazyListState): Int {
    val layoutInfo = listState.layoutInfo
    val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2f
    return layoutInfo.visibleItemsInfo.minByOrNull { info ->
        abs((info.offset + info.size / 2f) - viewportCenter)
    }?.index ?: listState.firstVisibleItemIndex
}

@Preview(showBackground = true, widthDp = 393, heightDp = 852, name = "Phone")
@Composable
private fun BirthdayScreenPreview() {
    BirthdayScreen()
}

@Preview(showBackground = true, widthDp = 800, heightDp = 1280, name = "Tablet")
@Composable
private fun BirthdayScreenTabletPreview() {
    BirthdayScreen()
}