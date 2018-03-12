package com.example.jamarkushodge.blk;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;



public class home extends AppCompatActivity {

    TextView figureCount;
    Figure[] figures;
    ArrayList <Figure> figArr;

    TextView name_tv;
    TextView bio_tv;
    ImageView figureImg_iv;
    Button  nxtBtn;
    int position = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        name_tv = (TextView) findViewById(R.id.name_tv);
        bio_tv = (TextView) findViewById(R.id.bio_tv);
        figureImg_iv = (ImageView) findViewById(R.id.figureImg_iv);
        nxtBtn = (Button) findViewById(R.id.nxt_btn);



        String data = fileIn();

        try {
            figures = JacksonObjectMapper(data);
        } catch (IOException e) {
            e.printStackTrace();
        }


        figArr = new ArrayList<>(Arrays.asList(figures));



        //Collections.sort(figArr);

        setFields(figArr,position+1);

        nxtBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setFields(figArr,position+1);

                position++;
            }
        });
    }

    private void setFields(ArrayList<Figure> figArr,int pos) {

        String imgUrl = null;
        final String HTP = "https://";
        String figureFaqs = " ";

        try {
            name_tv.setText(figArr.get(pos).getName());


            //for(int i = 0; i < figArr.get(pos).getFactsArr().size(); i++) {
            ArrayList<Fact> facts = figArr.get(pos).getFactsArr();

            for(int i = 0; i<facts.size();i++){
                figureFaqs += facts.get(i).getValue() + "\n";
            }

            bio_tv.setText(figureFaqs);

            imgUrl = figArr.get(pos).getImgURL();
            // substring 2 == Surpasses // in URL
            imgUrl = imgUrl.substring(2,imgUrl.length());
            imgUrl = HTP + imgUrl;

            new DownloadImageTask((ImageView) findViewById(R.id.figureImg_iv))
                    .execute(imgUrl);
        }

        catch (Exception e){
            e.printStackTrace();
        }
}

    private String fileIn() {

        String file = " ";


        AssetManager assetManager = getAssets();

        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("AA100.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        String line = " ";
        StringTokenizer stringTokenizer;

        try {

            int x = 0;

            while ((line = reader.readLine()) != null) {

                file += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public Figure[] JacksonObjectMapper(String jsonString) throws IOException {

        String data = jsonString;
        Figure[] figures = new Figure[500];

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Figure.class,new FigureDeserializer());
        objectMapper.registerModule(module);

        try {
            Figure[] readValue = objectMapper.readValue(data,Figure[].class);


            for(int i = 0; i<readValue.length;i++){

                figures[i] = readValue[i];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return figures;
    }

    public class FigureDeserializer extends StdDeserializer<Figure>{


        public FigureDeserializer(){
            this(null);
        }

        public FigureDeserializer(Class<?> vc){
            super(vc);
        }

            @Override
            public Figure deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = jp.getCodec().readTree(jp);
                ArrayList<Fact> faqs = null;

                String name = node.get("FIELD3").asText();
                String followURL = node.get("FIELD4").asText();
                String bio = node.get("FIELD5").asText();
                String pictureURL = node.get("FIELD6").asText();
                String facs = node.get("FIELD7").asText();

                try {
                    faqs = parseFacts(facs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return new Figure(name,followURL,bio,pictureURL,faqs);
            }

        private ArrayList<Fact> parseFacts(String facs) throws JSONException {


            JSONArray jsonArray = new JSONArray(facs);
            ArrayList<Fact> factArrayList = new ArrayList<>();
            JSONObject jsonObject;

            for(int i = 0; i < jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("facts");


                Fact fact = new Fact();

                fact.setValue(id);
                factArrayList.add(fact);

            }


            return factArrayList;

        }


    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}


