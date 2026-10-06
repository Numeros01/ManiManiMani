package com.example.manimanimani.ui.components.cards

fun createDatePattern(
    doShowYear: Boolean = false,
    doShowMonth: Boolean = true,
    doShowDay: Boolean = false,
    doShowHour: Boolean = true,
    doShowMinute: Boolean = true,
    doShowSecond: Boolean = false,
    monthDisplay: Int = 2, // 1 - number, 2 - short, 3 - full name
    isHourFirst: Boolean = false
) : String {
    var pattern = ""
    val month_separator = if(monthDisplay == 2) ' ' else '-'

    if(isHourFirst) {
        pattern += createHourPattern(
            doShowHour = doShowHour,
            doShowMinute = doShowMinute,
            doShowSecond = doShowSecond
        ) + ' '
    }

    if(doShowDay)
        pattern += "EEE "

    pattern += "dd"

    if(doShowMonth) {
        when(monthDisplay) {
            1 -> pattern += "-MM"
            2 -> pattern += " MMM"
            3 -> pattern += "-MMMMM"
        }
    }

    if(doShowYear)
        pattern += month_separator + "yyyy"

    if(!isHourFirst) {
        pattern += ' ' + createHourPattern(
            doShowHour = doShowHour,
            doShowMinute = doShowMinute,
            doShowSecond = doShowSecond
        )
    }

    return pattern
}

fun createHourPattern(
    doShowHour: Boolean = true,
    doShowMinute: Boolean = true,
    doShowSecond: Boolean = false
) : String {
    var pattern = ""

    if(doShowHour) {
        if(doShowMinute) {
            pattern += "HH:mm"

            if (doShowSecond)
                pattern += ":ss"
        } else {
            pattern += "hh a"
        }
    }

    return pattern
}