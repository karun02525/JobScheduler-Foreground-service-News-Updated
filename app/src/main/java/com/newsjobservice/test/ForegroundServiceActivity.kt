package com.newsjobservice.test

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.newsjobservice.ui.theme.NewsJobServiceTheme

class ForegroundServiceActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),200)
        }

        setContent {
            NewsJobServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {

                        Button(onClick = {
                             onStartService()
                        }) {
                            Text(text = "Star service")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            onStopService()
                        }) {
                            Text(text = "Stop service")
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                    }

                }
            }
        }
    }

    private fun onStartService() {
        val prefs= getSharedPreferences("mystore", Context.MODE_PRIVATE)
        val intent= Intent(this,MyService::class.java)
        intent.action = Actions.START.toString()
        if (prefs.getString( "componentName", null) == null) {
            val serviceComponentname = startService(intent)
            val editor = prefs.edit()
            editor.putString ("componentName", serviceComponentname.toString())
            editor.apply()
        }
    }
    private fun onStopService() {
        val prefs= getSharedPreferences("mystore", Context.MODE_PRIVATE)
        val intent= Intent(this,MyService::class.java)
        intent.action = Actions.STOP.toString()
        if (prefs.getString( "componentName", null) != null) {
            startService(intent)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
        }
    }
}