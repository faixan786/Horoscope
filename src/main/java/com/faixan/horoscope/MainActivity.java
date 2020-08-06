package com.faixan.horoscope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    ProgressDialog p;
    MaterialSpinner spinner;
    String sign = "capricorn";
    String apiKey = "70c84b7fb9msh4f70ef70a39b5ffp1a4b91jsn8ac986796396";
    String url = "https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=%s&day=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            getSupportActionBar().hide();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Capricorn", "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>()
        {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item)
            {
                sign = item.toLowerCase();
            }
        });
    }

    public void getHoroscope(View view)
    {
        DownloadData task = new DownloadData();
        String day = view.getTag().toString();
        String urlWithQuery = String.format(url, sign, day);

        try
        {
            task.execute(urlWithQuery);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Getting your horoscope ...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // set headers for the request
                // set host name
                connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com");

                // set the rapid-api key
                connection.setRequestProperty("x-rapidapi-key", apiKey);
                connection.setRequestMethod("POST");

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String temp, response = "";

                // read the response line by line
                while ((temp = reader.readLine()) != null)
                {
                    response += temp;
                }
                return response;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // if not able to retrieve data return null
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            // stop the loading symbol
            p.hide();
            p.dismiss();

            if (result == null)
            {
                Toast.makeText(MainActivity.this, "Cannot fetch your horoscope", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), DisplayResults.class);
                intent.putExtra("horoscope", result);
                intent.putExtra("sign", sign);
                startActivity(intent);
                finish();
            }
        }
    }

   /* @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }*/
}