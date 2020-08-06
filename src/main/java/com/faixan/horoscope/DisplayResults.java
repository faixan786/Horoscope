package com.faixan.horoscope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayResults extends AppCompatActivity
{
    TextView starSignTextView, dateRangeTextView, currentDateTextView, horoscopeTextView, compatibilityTextView, moodTextView, luckyColorTextView, luckyNumberTextView, luckyTimeTextView;
    ImageView starSignImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        getSupportActionBar().hide();
        String horoscope = getIntent().getStringExtra("horoscope");
        String sign = getIntent().getStringExtra("sign");

        currentDateTextView = (TextView) findViewById(R.id.currentDateTextView);
        starSignTextView = (TextView) findViewById(R.id.starSignTextView);
        compatibilityTextView = (TextView) findViewById(R.id.compatibilityTextView);
        dateRangeTextView = (TextView) findViewById(R.id.dateRangeTextView);
        starSignImageView = (ImageView) findViewById(R.id.starSignImageView);
        horoscopeTextView = (TextView) findViewById(R.id.horoscopeTextView);
        moodTextView = (TextView) findViewById(R.id.moodTextView);
        luckyColorTextView = (TextView) findViewById(R.id.luckyColorTextView);
        luckyNumberTextView = (TextView) findViewById(R.id.luckyNumberTextView);
        luckyTimeTextView = (TextView) findViewById(R.id.luckyTimeTextView);

        horoscopeTextView.setMovementMethod(new ScrollingMovementMethod());
        starSignTextView.setText(sign.toUpperCase());
        starSignImageView.setImageResource(getImageId(sign));

        try
        {
            JSONObject result = new JSONObject(horoscope);
            setResultPage(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setResultPage(JSONObject result)
    {
        try
        {
            String dateRange = result.getString("date_range");
            dateRangeTextView.setText(dateRange);
            String currentDate = result.getString("current_date");
            currentDateTextView.setText(currentDate);
            String description = result.getString("description");
            horoscopeTextView.setText(description);
            String compatibility = result.getString("compatibility");
            compatibilityTextView.setText(compatibility);
            String mood = result.getString("mood");
            moodTextView.setText(mood);
            String luckyColor = result.getString("color");
            luckyColorTextView.setText(luckyColor);
            String luckyNumber = result.getString("lucky_number");
            luckyNumberTextView.setText(luckyNumber);
            String luckyTime = result.getString("lucky_time");
            luckyTimeTextView.setText(luckyTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private int getImageId(String name)
    {
        int id = 0;
        switch (name)
        {
            case "capricorn":
                id = R.drawable.capricorn;
                break;

            case "aquarius":
                id = R.drawable.aquarius;
                break;

            case "pisces":
                id = R.drawable.pisces;
                break;

            case "aries":
                id = R.drawable.aries;
                break;

            case "taurus":
                id = R.drawable.taurus;
                break;

            case "gemini":
                id = R.drawable.gemini;
                break;

            case "cancer":
                id = R.drawable.cancer;
                break;

            case "leo":
                id = R.drawable.leo;
                break;

            case "virgo":
                id = R.drawable.virgo;
                break;

            case "libra":
                id = R.drawable.libra;
                break;

            case "scorpio":
                id = R.drawable.scorpio;
                break;

            case "sagittarius":
                id = R.drawable.sagittarius;
                break;
        }

        return id;
    }
}