/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.sunshine.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import static com.example.android.sunshine.utilities.NetworkUtils.buildUrl;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.toString();
    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        Log.i(LOG_TAG, "trying to load");
        loadWeatherData();
    }

    private void loadWeatherData() {
        String location = getUserPrederredLocation();
        new AsyncNetworkRequest().execute(NetworkUtils.buildUrl(location));
    }

    private String getUserPrederredLocation() {
        return "London";
        // todo replace with real one, maybe it should be fetched from some textview
    }

    class AsyncNetworkRequest extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            mWeatherTextView.setText(s);
        }

        @Override
        protected String doInBackground(URL... params) {

            assert params.length == 1;

            try {
                return NetworkUtils.getResponseFromHttpUrl(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.getMessage());
                return "";
            }
        }
    }

}