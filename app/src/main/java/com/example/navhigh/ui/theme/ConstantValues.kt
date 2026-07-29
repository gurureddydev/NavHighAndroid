package com.example.navhigh.ui.theme


import kotlin.time.Duration.Companion.milliseconds


// Birthday Info Bottom Sheet
const val BirthdaySheetAnimationDurationMs = 280
// Birthday Date Wheel Picker
const val CircularRepeatCount = 2000
const val DatePickerMinYear = 1876
const val DatePickerMaxYear = 2027

// Shared Layout Weights
const val FullWeight = 1f
// Wheel Column Weights
const val WheelDayWeight = 1f
const val WheelMonthWeight = 1f
const val WheelYearWeight = 1.3f

// Wheel Distance/Alpha Calculation
const val WheelDistanceMinRows = 0f
const val WheelDistanceMaxRows = 2f
const val WheelAlphaMax = 1f
const val WheelAlphaMin = 0.22f
const val WheelAlphaDistanceFactor = 0.38f
const val WheelCenterThreshold = 0.5f
// Birthday Info Bottom Sheet - Dim & Drag
const val BirthdaySheetDimAmount = 0.55f
const val BirthdaySheetDragMinOffset = 0f
const val BirthdaySheetSpringBackSteps = 8
const val BirthdaySheetSpringBackDelayMs = 8L


val BirthdaySheetSpringBackDelay = 8.milliseconds

// Email Screen
const val EmailLoadingDelayMs = 5000L
const val EmailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

// ConstantValues.kt
const val FullNameDescriptionAlpha = 0.70f
// ConstantValues.kt
const val UserNameDescriptionAlpha = 0.70f
const val UserNameLoadingDelayMs = 5000L
const val OtpLength = 6