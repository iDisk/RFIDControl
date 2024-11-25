package com.example.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView scannedData;
    private EditText email1, email2;
    private String capturedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scan_button);
        Button sendButton = findViewById(R.id.send_button);
        scannedData = findViewById(R.id.scanned_data);
        email1 = findViewById(R.id.email1);
        email2 = findViewById(R.id.email2);

        scanButton.setOnClickListener(v -> startBarcodeScan());

        sendButton.setOnClickListener(v -> sendEmail());
    }

    private void startBarcodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                capturedData = result.getContents();
                scannedData.setText(capturedData);
            } else {
                Toast.makeText(this, "Scan failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmail() {
        String recipient1 = email1.getText().toString();
        String recipient2 = email2.getText().toString();

        if (capturedData == null || capturedData.isEmpty()) {
            Toast.makeText(this, "No data to send", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recipient1.isEmpty() || recipient2.isEmpty()) {
            Toast.makeText(this, "Please enter both email addresses", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient1, recipient2});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Scanned Barcode Data");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Scanned Data: " + capturedData);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
