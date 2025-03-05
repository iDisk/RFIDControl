package com.idisk.rfidcontrol

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.charset.Charset
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef


class WriteNFCActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        setContent {
            NFCWriterApp()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            writeToNfc(it, "Test NFC Data")
        }
    }

    private fun writeToNfc(tag: Tag, data: String) {
        val ndef = Ndef.get(tag)
        if (ndef != null) {
            try {
                ndef.connect()
                val mimeRecord = NdefRecord.createMime("text/plain", data.toByteArray(Charset.forName("UTF-8")))
                val ndefMessage = NdefMessage(mimeRecord)
                ndef.writeNdefMessage(ndefMessage)
                ndef.close()
            } catch (e: Exception) {
                Log.e("NFC", "Write error", e)
            }
        }
    }
}

@Composable
fun NFCWriterApp() {
    var text by remember { mutableStateOf("Acerca tu NFC para escribir datos") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { text = "Esperando etiqueta NFC..." }) {
            Text("Escribir en NFC")
        }
    }
}

