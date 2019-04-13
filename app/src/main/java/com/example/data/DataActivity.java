package com.example.data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    private static final String TAG = DataActivity.class.getSimpleName();
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        DataAsyncTask dataAsyncTask = new DataAsyncTask("https://reqres.in/api/unknown");
        dataAsyncTask.execute();
        ButterKnife.bind(this);
    }

    private class DataAsyncTask extends AsyncTask<String, Void, String> {

        private String urlEndPoint;

        public DataAsyncTask(String urlEndPoint) {
            this.urlEndPoint = urlEndPoint;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(urlEndPoint);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                // urlConnection.setDoInput(false);
                // urlConnection.setDoOutput(false);

                //  urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("GET");


                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString");


                int statusCode = urlConnection.getResponseCode();
                Log.d(TAG, "Status Code: " + statusCode);


                if (statusCode == 200) {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);
                    Log.d(TAG, "Response: " + response);
                    return response;

                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                    // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
                } else {
                    // Status code is not 200
                    // Do something to handle the error
                }

            } catch (Exception e) {
                Log.d(TAG, "Exception: " + e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d(TAG, "Response: " + response);
            ArrayList<DataModel> dataModels = new ArrayList<>();
            try {
                if (response != null) {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.optInt("id");
                        Log.d(TAG, "id " + id);

                        DataModel dataModel = new DataModel();
                        dataModel.setId(id);

                        String name = jsonObject.optString("name");
                        Log.d(TAG, "name " + name);
                        dataModel.setName(name);

                        int year = jsonObject.optInt("year");
                        Log.d(TAG, "year " + year);
                        dataModel.setYear(year);

                        String color = jsonObject.optString("color");
                        Log.d(TAG, "color " + color);
                        dataModel.setColor(color);

                        String pantoneValue = jsonObject.optString("pantone_value");
                        Log.d(TAG, "pantoneValue " + pantoneValue);
                        dataModel.setPantoneValue(pantoneValue);

                        dataModels.add(dataModel);
                    }

                    rvData.setLayoutManager(new LinearLayoutManager(DataActivity.this));
                    DataAdapter adapter = new DataAdapter(DataActivity.this, dataModels);
                    rvData.setAdapter(adapter);

                }
            }catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

        private String convertInputStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }





