
package com.idisk.rfidcontrol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenu()
        }
    }
}

@Composable
fun MainMenu() {
    val context = LocalContext.current  // ✅ Obtener el contexto aquí

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Selecciona una opción:", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            context.startActivity(Intent(context, WriteNFCActivity::class.java))
        }) {
            Text("Escribir en NFC")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            context.startActivity(Intent(context, ReadNFCActivity::class.java))
        }) {
            Text("Leer desde NFC")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            context.startActivity(Intent(context, QRCodeActivity::class.java))
        }) {
            Text("Generar QR")
        }
    }
}
