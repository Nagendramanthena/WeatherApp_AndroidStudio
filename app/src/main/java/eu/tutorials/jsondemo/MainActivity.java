package eu.tutorials.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView edit;


    public void Weather(View view){
        TextView resview = findViewById(R.id.textView5);
        String text = edit.getText().toString();
        downloader d = new downloader();
        String main = "";
        String descrition = "";
        String message = "";
        double curr ;
        double feelslike;double tmax;double tmin;
        try{
            String y =  d.execute("https://api.openweathermap.org/data/2.5/weather?q="+text+"&appid=a59949a0706f0c7e2db0de86e1de08cd").get();
            Log.i("Info",y);
            JSONObject jsb = new JSONObject(y);
            String w = jsb.getString("weather");
            JSONArray arr = new JSONArray(w);
            String m = jsb.getString("main");
            String a[] = m.split(",");

            curr= Double.parseDouble(a[0].split(":")[1])-273.00;



            if(!String.valueOf(curr).equals("")){
                String k = String.format("%.2f", curr);
                message += k+" °C"+"\r\n";
            }




            for(int i=0;i<arr.length();i++){
                JSONObject jsonpartt = arr.getJSONObject(i);
                main = jsonpartt.getString("main");
                descrition = jsonpartt.getString("description");
                Log.i("des",descrition);

                if(!main.equals("") && !descrition.equals("")){
                    message +=main+" : "+descrition+"\r\n";
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }


        resview.setText(message);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.editText);


    }


    public class downloader extends AsyncTask<String,Void,String>{


        String res = "";
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection connection;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();
                InputStreamReader reader =  new InputStreamReader(in);
                 int data = reader.read();
                 Log.i("data",Integer.toString(data));

                 while(data!=-1){
                     char c = (char) data;
                     res +=c;
                     data = reader.read();
                 }
                 Log.i("res",res);
                 return res;
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }
    }
}