package com.ihomer.caresyncapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.api.rest.RestResponse;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amplify();
        setContentView(R.layout.activity_main);
    }

    private void amplify() {
        try {
            // Add these lines to add the `AWSApiPlugin` and `AWSCognitoAuthPlugin`
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify.");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify.", error);
        }

        RestOptions options = RestOptions.builder()
                .addPath("/Database")
                //.addBody("{\"name\":\"Mow the lawn\"}".getBytes())
                .build();

        Amplify.API.get(options,
                response -> reponseSuccesful(response),
                error -> Log.e("MyAmplifyApp", "POST failed.", error)
        );


    }

    private void reponseSuccesful(RestResponse restResponse) {
        Log.i("MyAmplifyApp", "GET succeeded: " + restResponse);
        String s = new String(restResponse.getData().getRawBytes(), StandardCharsets.US_ASCII);
        Log.i("MyAmplifyApp", s);

    }
}