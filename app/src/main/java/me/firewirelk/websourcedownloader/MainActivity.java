package me.firewirelk.websourcedownloader;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

                //data=ed1.getText().toString();
                try {
                    FileOutputStream fOut = openFileOutput(String.valueOf(url),MODE_WORLD_READABLE);
                    fOut.write(result.getBytes());
                    fOut.close();
                    Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {

                    e.printStackTrace();
                }


                return result;


            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getBaseContext(),"file does not saved",Toast.LENGTH_SHORT).show();
                return "error";
            }


        }
    }


    public void btnClick(View view){
        DownloadTask dt=new DownloadTask();
        String result=null;
        try {
            result=dt.execute(txtURL.getText().toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("Result",result);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtURL=(TextView)findViewById(R.id.txtURL);
        btnGetresource=(Button)findViewById(R.id.btnGetsource);


    }
}
