package com.idisk.rfidcontrol

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
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


class ReadNFCActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        setContent {
            NFCReaderApp("Acerca el NFC para leer datos")
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            val data = readFromNfc(it)
            setContent {
                NFCReaderApp(data ?: "No se pudo leer el NFC")
            }
        }
    }

    private fun readFromNfc(tag: Tag): String? {
        val ndef = Ndef.get(tag)
        return if (ndef != null) {
            try {
                ndef.connect()
                val ndefMessage = ndef.ndefMessage
                ndefMessage?.records?.firstOrNull()?.payload?.toString(Charset.forName("UTF-8"))
            } catch (e: Exception) {
                Log.e("NFC", "Read error", e)
                null
            }
        } else {
            null
        }
    }
}

@Composable
fun NFCReaderApp(text: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
