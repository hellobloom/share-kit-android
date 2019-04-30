package com.bloom.sharekitexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import the share kit lib
import com.bloom.sharekit.ShareKit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example layout to add the RequestButton
        LinearLayout layout = findViewById(R.id.example_activity);

        // initialize the request data JSON Object and the types array
        JSONObject requestData = new JSONObject();
        JSONArray attestationTypes = new JSONArray();
        try {
            requestData.put("action", ShareKit.attestation);
            requestData.put("token", "0x8f31e48a585fd12ba58e70e03292cac712cbae39bc7eb980ec189aa88e24d043");
            requestData.put("url", "https://receive-kit.bloom.co/api/receive");
            requestData.put("org_logo_url", "https://bloom.co/images/notif/bloom-logo.png");
            requestData.put("org_name", "Bloom");
            requestData.put("org_usage_policy_url", "https://bloom.co/legal/terms");
            requestData.put("org_privacy_policy_url", "https://bloom.co/legal/privacy");

            // add the attestations
            attestationTypes.put("full-name");
            attestationTypes.put("phone");
            attestationTypes.put("email");
            requestData.put("types", attestationTypes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String appCallbackUrl = "https://google.com";

        // initialize the button
        Button requestButton = new ShareKit().RequestButton(this, requestData, appCallbackUrl);

        //add button to the layout
        layout.addView(requestButton);

        // set the button padding if necessary
        layout.setPadding(30,50,10,10);
    }
}
