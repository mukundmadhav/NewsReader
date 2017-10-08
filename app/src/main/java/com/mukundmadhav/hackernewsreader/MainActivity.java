package com.mukundmadhav.hackernewsreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    //https://hacker-news.firebaseio.com/v0/topstories.json

    private class GetTitles extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String listOfIds = "";

            try{
                URL url =new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                int data = isr.read();
                while(data!=-1){
                    char ch = (char) data;
                    listOfIds += ch;
                    data = isr.read();
                }
                return  listOfIds;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return  listOfIds;

        }


    }

    private class GetIds extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String listOfIds = "";

            try{
                URL url =new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                int data = isr.read();
                while(data!=-1){
                    char ch = (char) data;
                    listOfIds += ch;
                    data = isr.read();
                }
                return  listOfIds;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return  listOfIds;

        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        GetIds getIds= new GetIds();
        GetTitles[] getTitles = new GetTitles[10];
        for(int i=0; i<10; ++i) {
            getTitles[i] = new GetTitles();
        }
        String[] urlsAddresses = new String[15];

        try {

            String s = getIds.execute("https://hacker-news.firebaseio.com/v0/topstories.json").get();
            String tits;
            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<15; ++i) {
                Log.i("Values:",jsonArray.getString(i));
                tits = getTitles[i].execute("https://hacker-news.firebaseio.com/v0/item/"+jsonArray.getString(i)+".json").get();
                JSONObject jsonObject = new JSONObject(tits);
                arrayList.add(jsonObject.getString("title"));
                urlsAddresses[i] = jsonObject.getString("url");
                arrayAdapter.notifyDataSetChanged();
                Log.i("Return:",jsonObject.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String[] urlsAddresse = urlsAddresses;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Url:",urlsAddresse[i]);
                Intent intent = new Intent(MainActivity.this, displayURLs.class);
                intent.putExtra("url",urlsAddresse[i]);
                startActivity(intent);
            }
        });

    }
}
