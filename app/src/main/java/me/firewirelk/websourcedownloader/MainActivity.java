package me.firewirelk.websourcedownloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView txtURL;
    Button btnGetresource;

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL url;
            HttpURLConnection urlConnection=null;

            try{

                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();

                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data != -1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }

                return "success";


            }catch (Exception e){
                e.printStackTrace();
                return "error";
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtURL=(TextView)findViewById(R.id.txtURL);
        btnGetresource=(Button)findViewById(R.id.btnGetsource);

        DownloadTask dt=new DownloadTask();
        String result=null;
        try {
            result=dt.execute(txtURL.getText().toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
