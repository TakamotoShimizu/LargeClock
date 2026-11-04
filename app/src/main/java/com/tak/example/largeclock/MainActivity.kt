package com.tak.example.largeclock

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tak.example.largeclock.ui.theme.LargeClockTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    private val viewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Always keep screen ON
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            LargeClockTheme {
                ClockScreen(viewModel)
            }
        }
    }
}

@Composable
fun ClockScreen(viewModel: ClockViewModel) {
    val currentTime by viewModel.currentTime.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date area (1/6 of screen height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = viewModel.getFormattedDate(currentTime),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Time area (5/6 of screen height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                contentAlignment = Alignment.Center
            ) {
                TimeDisplay(currentTime, viewModel)
            }
        }
    }
}

@Composable
fun TimeDisplay(dateTime: LocalDateTime, viewModel: ClockViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val hours = viewModel.getHours(dateTime)
        val minutes = viewModel.getMinutes(dateTime)
        val seconds = viewModel.getSeconds(dateTime)

        Text(
            text = "$hours:$minutes",
            fontSize = 180.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = seconds,
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.Bottom).padding(bottom = 32.dp)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
fun ClockScreenPreview() {
    LargeClockTheme {
        ClockScreen(ClockViewModel())
    }
}
