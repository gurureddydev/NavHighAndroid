package com.example.navhigh.ui.birthday

import android.graphics.drawable.ColorDrawable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.navhigh.common.button.Button
import com.example.navhigh.common.components.AlreadyHaveAccount
import com.example.navhigh.common.components.BackArrow
import com.example.navhigh.common.components.ScreenTitle
import com.example.navhigh.common.components.TitlePart
import com.example.navhigh.common.textfield.DateOfBirthTextField
import com.example.navhigh.ui.theme.ForgotPasswordBlue
import java.util.Calendar

// ---------- Colors ----------
private val BgBlack = Color(0xFF03070C)
private val DialogBg = Color(0xFF020508)
private val CyanAccent = Color(0xFF29C4F0)
private val TextWhite = Color(0xFFF2F5F8)
private val TextGrey = Color(0xFF8C97A3)
private val FieldBorder = Color(0xFF1E3A5F)

private val MONTH_NAMES = arrayOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

private val MONTH_ABBR = arrayOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)


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

    // Subtract 1 if this year's birthday hasn't happened yet.
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
    // Default the picker to TODAY's actual date instead of a fixed
    // placeholder, per request. month is 0-indexed to match Calendar.MONTH.
    val today = remember { Calendar.getInstance() }
    var birthdayYear by remember { mutableIntStateOf(today.get(Calendar.YEAR)) }
    var birthdayMonth by remember { mutableIntStateOf(today.get(Calendar.MONTH)) }
    var birthdayDay by remember { mutableIntStateOf(today.get(Calendar.DAY_OF_MONTH)) }

    // Opens automatically as soon as the screen appears
    var showPicker by remember { mutableStateOf(true) }

    // Controls the "why do we need this" bottom sheet
    var showInfoSheet by remember { mutableStateOf(false) }

    val formattedBirthday = remember(birthdayYear, birthdayMonth, birthdayDay) {
        "$birthdayDay ${MONTH_NAMES[birthdayMonth]} $birthdayYear"
    }

    // Recomputed any time the picked date changes, fed straight into the
    // reusable DateOfBirthTextField's label instead of the old hardcoded "0".
    val age = remember(birthdayYear, birthdayMonth, birthdayDay) {
        calculateAge(birthdayYear, birthdayMonth, birthdayDay)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            BackArrow(onClick = onBack)

            Spacer(modifier = Modifier.height(15.dp))

            ScreenTitle(
                lines = listOf(
                    listOf(
                        TitlePart(text = "What's your ", color = TextWhite),
                        TitlePart(text = "date of birth", color = ForgotPasswordBlue),
                        TitlePart(text = "?", color = TextWhite)
                    )
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            val descriptionText = buildAnnotatedString {
                append("Use your own birthday, even if this account is for a business, a pet or something else. No one will see this unless you choose to share it. ")
                withLink(
                    LinkAnnotation.Clickable(
                        tag = "WHY",
                        styles = TextLinkStyles(style = SpanStyle(color = CyanAccent))
                    ) {
                        showInfoSheet = true
                    }
                ) {
                    append("Why do I need to provide my date of birth?")
                }
            }

            Text(
                text = descriptionText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextGrey,
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Birthday field — reusable DateOfBirthTextField from CommonTextField.kt.
            // No separate background/clip layer here — the OutlinedTextField
            // inside DateOfBirthTextField already draws its own border/shape,
            // so we just overlay a plain transparent clickable layer on top
            // to intercept taps and open the picker.
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                DateOfBirthTextField(
                    value = formattedBirthday,
                    onValueChange = {}, // read-only, value only changes via the picker
                    age = age
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showPicker = true }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Next button (reusable)
            Button(
                text = "Next",
                onClick = { onNext(birthdayYear, birthdayMonth, birthdayDay) }
            )

            // Pushes the account link down to sit centered at the bottom of the screen
            Spacer(modifier = Modifier.weight(1f))

            AlreadyHaveAccount(
                onLogin = onLogin,
                onContinue = onContinue
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

/**
 * Fully custom date picker built in Compose (day / month / year scroll wheels),
 * styled to match the screen exactly. Native android.app.DatePickerDialog is
 * skinned by the phone manufacturer (MIUI, ColorOS, etc.) and largely ignores
 * background/theme overrides — building this ourselves guarantees the same
 * look on every device.
 */
@Composable
private fun DateWheelPickerDialog(
    initialDay: Int,
    initialMonth: Int,
    initialYear: Int,
    onDismiss: () -> Unit,
    onConfirm: (day: Int, month: Int, year: Int) -> Unit
) {
    val days = remember { (1..31).map { it.toString() } }
    val months = remember { MONTH_ABBR.toList() }
    val currentYear = remember { Calendar.getInstance().get(Calendar.YEAR) }
    val years = remember { (currentYear - 100..currentYear + 5).map { it.toString() } }

    var selectedDayIndex by remember { mutableIntStateOf((initialDay - 1).coerceIn(0, days.lastIndex)) }
    var selectedMonthIndex by remember { mutableIntStateOf(initialMonth.coerceIn(0, months.lastIndex)) }
    var selectedYearIndex by remember {
        mutableIntStateOf(years.indexOf(initialYear.toString()).let { if (it >= 0) it else years.lastIndex })
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // The Dialog's underlying Android Window has its own default white
        // background drawable, completely separate from the Surface color
        // below. Stripping it here makes the window itself transparent so
        // only our dark Surface is visible.
        val view = LocalView.current
        SideEffect {
            (view.parent as? DialogWindowProvider)?.window?.setBackgroundDrawable(
                ColorDrawable(android.graphics.Color.TRANSPARENT)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = DialogBg,
                modifier = Modifier.fillMaxWidth(0.94f)
            ) {
                // Tightened padding a bit (24.dp -> 20.dp) to match the
                // shorter wheel height below and keep the card compact.
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Set date",
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WheelColumn(
                            items = days,
                            selectedIndex = selectedDayIndex,
                            onSelectedIndexChange = { selectedDayIndex = it },
                            modifier = Modifier.weight(1f)
                        )
                        WheelColumn(
                            items = months,
                            selectedIndex = selectedMonthIndex,
                            onSelectedIndexChange = { selectedMonthIndex = it },
                            modifier = Modifier.weight(1f)
                        )
                        WheelColumn(
                            items = years,
                            selectedIndex = selectedYearIndex,
                            onSelectedIndexChange = { selectedYearIndex = it },
                            modifier = Modifier.weight(1.3f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "CANCEL",
                            color = CyanAccent,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .clickable { onDismiss() }
                                .padding(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SET",
                            color = CyanAccent,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .clickable {
                                    onConfirm(
                                        selectedDayIndex + 1,
                                        selectedMonthIndex,
                                        years[selectedYearIndex].toInt()
                                    )
                                }
                                .padding(12.dp)
                        )
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
    modifier: Modifier = Modifier
) {
    // Bumped back up a bit from 40.dp / 3 rows so the wheel isn't too
    // cramped, while staying shorter than the earlier 52.dp / 5 rows.
    val itemHeight = 44.dp
    val visibleCount = 4

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    val flingBehavior = rememberSnapFlingBehavior(listState)

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            onSelectedIndexChange(listState.firstVisibleItemIndex.coerceIn(0, items.lastIndex))
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
            itemsIndexed(items) { index, label ->
                val isSelected = index == listState.firstVisibleItemIndex
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) TextWhite else TextGrey,
                        fontSize = if (isSelected) 20.sp else 16.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }

        // Selector lines above and below the centered item, like a native spinner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopCenter)
                .background(FieldBorder)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomCenter)
                .background(FieldBorder)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050B14, widthDp = 393, heightDp = 852)
@Composable
private fun BirthdayScreenPreview() {
    BirthdayScreen()
}