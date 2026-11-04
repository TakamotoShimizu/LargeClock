package com.tak.example.largeclock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.chrono.JapaneseDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ClockViewModel : ViewModel() {

    private val _currentTime = MutableStateFlow(LocalDateTime.now())
    val currentTime: StateFlow<LocalDateTime> = _currentTime

    init {
        viewModelScope.launch {
            while (true) {
                _currentTime.value = LocalDateTime.now()
                delay(1000)
            }
        }
    }

    fun getFormattedDate(dateTime: LocalDateTime): String {
        val japaneseDate = JapaneseDate.from(dateTime)
        val eraFormatter = DateTimeFormatter.ofPattern("GGGGy年", Locale.JAPAN)
        val eraString = japaneseDate.format(eraFormatter)
        
        val westernYear = dateTime.year
        val month = dateTime.monthValue
        val day = dateTime.dayOfMonth
        
        return "${westernYear}年(${eraString})${month}月${day}日"
    }

    fun getHours(dateTime: LocalDateTime): String = String.format(Locale.US, "%02d", dateTime.hour)
    fun getMinutes(dateTime: LocalDateTime): String = String.format(Locale.US, "%02d", dateTime.minute)
    fun getSeconds(dateTime: LocalDateTime): String = String.format(Locale.US, "%02d", dateTime.second)
}
