package com.bloom.sharekit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ShareKit {

    private JSONObject bloomRequestData = null;
    private String callbackUrl = null;

    public static final String attestation = "request_attestation_data";

    public Button RequestButton(final Activity activity, JSONObject requestData, String appCallbackUrl) {

        // set the share-kit-from query url
        if (requestData.has("url")){
            try {
                String newUrl = requestData.getString("url") + "?share-kit-from=button";
                // update the url
                requestData.put("url", newUrl);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

        // initialize the request data and the callback url
        bloomRequestData = requestData;
        callbackUrl = appCallbackUrl;

        // initialize the button in the current context
        Button bloomButton = new Button(activity);

        // get the base button measurements
        float density =  activity.getResources().getDisplayMetrics().density;

        // calculate the button dimensions based on the density
        int buttonWidth = (int) (331 * density);
        int buttonHeight = (int) (48 * density);

        // set the button layout params
        bloomButton.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth, buttonHeight));
        bloomButton.setBackground(ContextCompat.getDrawable(activity, R.drawable.bloombutton));

        // set the click listener
        bloomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the request in the bloom app if its installed or go to the browser
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getBloomLink())));
            }
        });

        return bloomButton;
    }

    private String getBloomLink() {

        String stringifiedRequestData;
        String base64RequestData = "";

        try {

            stringifiedRequestData = bloomRequestData.toString(2);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                base64RequestData = Base64.encodeToString(stringifiedRequestData.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            } else{
                base64RequestData = Base64.encodeToString(stringifiedRequestData.getBytes("UTF-8"), Base64.DEFAULT);
            }

        }
        catch (JSONException je) {
            je.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return String.format("https://bloom.co/download?request=%s&callback-url=%s", base64RequestData, Uri.encode(callbackUrl));
    }


}
