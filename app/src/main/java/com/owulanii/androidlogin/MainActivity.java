package com.owulanii.androidlogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import prefs.UserInfo;
import prefs.UserSession;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private TextView tvUsername, tvEmail;
    private UserInfo userInfo;
    private UserSession userSession;
String va;
    String json;

    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;


    String username;
    String email;

    InputStream is=null;

    String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfo        = new UserInfo(this);
        userSession     = new UserSession(this);
        logout          = (Button)findViewById(R.id.logout);
        tvUsername      = (TextView)findViewById(R.id.key_username);
        tvEmail         = (TextView)findViewById(R.id.key_email);

        if(!userSession.isUserLoggedin()){
            startActivity(new Intent(this, Login.class));
            finish();
        }

         username = userInfo.getKeyUsername();
         email    = userInfo.getKeyEmail();

        tvUsername.setText(username);
        tvEmail.setText(email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSession.setLoggedin(false);
                userInfo.clearUserInfo();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });

        //signup(username,email);


       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      //  StrictMode.setThreadPolicy(policy);

/*
        try {
            consulta(username,email);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

     //   new consulta().execute(username,email);


    }

    public void getJSON(View view){
        new backd().execute();
    }

    class backd extends AsyncTask<Void,Void,String>
    {
        String json_url;



        @Override
        protected void onPreExecute() {
           // super.onPreExecute();

            json_url = "http://arteypixel.com/transportemetro/getpass.php?username="+"demo"+"&email="+"erlisakino@gmail.com";

        }

        @Override
        protected String doInBackground(Void... voids) {


                try {
                    URL url = null;
                    url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder stringBuilder = new StringBuilder();

                    while ((JSON_STRING = bufferedReader.readLine())!=null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return  stringBuilder.toString().trim();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return  null;

        }




        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            TextView textview = (TextView) findViewById(R.id.datos);
            textview.setText(result);
         }
    }

 /*
       class consulta extends AsyncTask<String  , String, Long> {


        @Override
        protected Long doInBackground(String... strings) {

            try {

                String link = "http://arteypixel.com/transportemetro/getpass.php?username="+"demo"+"& emailer="+"erlisakino@gmail.com";

                 URL url = new URL(link);

                HttpURLConnection urlConnection = null;
                BufferedReader bufferedReader = null;

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();
                is= urlConnection.getInputStream();



                //bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                //json = bufferedReader.readLine();
                Log.d(TAG, " json: " + json);
                //urlConnection.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
            }


            //  Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();

            //  ver2(json);
         //   Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();


            //   }
            //  });

            Log.d(TAG, " json: " + json);

            return Long.valueOf(json);
        }


        @Override
        protected void onPostExecute(Long result) {
         //   showDialog("rtjtrj" + result + "rthrh");

            Toast.makeText(getApplicationContext(),  4, Toast.LENGTH_SHORT).show();


        }
    }

    */
    public void consulta(String username, String email) throws IOException {

//                    URL url = new URL("http://arteypixel.com/envio_notificaciones/register.php");

            String link = "http://arteypixel.com/transportemetro/getpass.php?username="+username+"& emailer="+email;

            URL url = new URL(link);
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            json = bufferedReader.readLine();
            //  Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
            Log.d(TAG, " json: " + json);
          //  ver2(json);
           Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();

            urlConnection.disconnect();

        //   }
        //  });

        Log.d(TAG, " json: " + json);


    }



    private String signup(final String username, final String password) throws IOException, URISyntaxException {


    //    protected String signup(String... arg0) {

//    protected Object doInBackground(String... arg0) {
     //   if(byGetOrPost == 0){


                String username1 = username;
                String password1= password;
                String link = "http://arteypixel.com/transportemetro/getpass.php?username="+username+"& password="+password;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

       // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();

        //   } else{
        /*
            try{
                String username1 = username;
                String password1 = password;

                String link="http://myphpmysqlweb.hostei.com/loginpost.php";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
           }
           */
      //  }
    }

}
