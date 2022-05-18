package com.example.menusandthreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.menusandthreads.connection.Connection;
import com.example.menusandthreads.fragments.BlankFragmentA;
import com.example.menusandthreads.fragments.BlankFragmentB;
import com.example.menusandthreads.services.MyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView visualizador;
    // Instantiate the RequestQueue.
    RequestQueue queue;
    Connection objCon;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        visualizador = findViewById(R.id.tvisualizador);
        queue = Volley.newRequestQueue(this);
        objCon = new Connection(this,"uceva",null,1);
        db = objCon.getWritableDatabase();
        if(objCon != null){
            Toast.makeText(this,"Bd created",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.initial_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_bad_thread:
                //Toast.makeText(this,"hola bad",Toast.LENGTH_LONG).show();
                renderFragment(1);
                break;
            case R.id.item_good_thread:
                //Toast.makeText(this,"hola goo",Toast.LENGTH_LONG).show();
                renderFragment(2);
                break;
            case R.id.item1:
                //Toast.makeText(this,"hola item1",Toast.LENGTH_LONG).show();
                iniciarServicio();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void iniciarServicio(){
        Intent servicio = new Intent(this,MyService.class);

        startService(servicio);
    }

    public void renderFragment(int option){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(option==1){// a
            BlankFragmentA fa = new BlankFragmentA();
            //BlankFragmentA fa = BlankFragmentA.newInstance("A","B");
            fragmentTransaction.replace(R.id.fragmentContainer, fa);
            fragmentTransaction.commit();
        }else if (option==2){ //b
            BlankFragmentB fb = new BlankFragmentB();
            //BlankFragmentB fb = BlankFragmentB.newInstance("A","B");
            fragmentTransaction.replace(R.id.fragmentContainer, fb);
            fragmentTransaction.commit();
        }
    }

    public void startVolley(View l){
        // Request a string response from the provided URL.
        String url = "https://run.mocky.io/v3/c3f5e4b5-0c3b-4637-89eb-4f1fdffb7cf4";
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String datos ="";
                            //VolleyLog.v("Response:%n %s",response.getInt("count"));
                            JSONArray jsonArray = response.getJSONArray("estudiantes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject student = jsonArray.getJSONObject(i);
                                datos += "Name: "+student.getString("name")+ " Course: "+student.getString("course_name")+"\n";
                                db.execSQL("insert into students values("+(i+1)+",'"+student.getString("name")+"','"+student.getString("course_name")+"');");
                            }
                            visualizador.setText(datos);
                            //cantidadRegistros=response.getInt("count");
                            //dataAgenda = response.getJSONArray("agenda"); // for foreach
                            //System.out.println("cantidadRegistros1: "+cantidadRegistros);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(req);
    }

    public void iniciarPeticionHttp(View g){
        CosultaHttp objHttp = new CosultaHttp();
        objHttp.execute();
        /*try {
            URL urlApi = new URL("https://run.mocky.io/v3/c3f5e4b5-0c3b-4637-89eb-4f1fdffb7cf4");
            HttpURLConnection urlConnection = (HttpURLConnection) urlApi.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Log.d("","respuesta de api sin formato: "+ in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public class CosultaHttp extends AsyncTask<Void,String,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL urlApi = new URL("https://run.mocky.io/v3/c3f5e4b5-0c3b-4637-89eb-4f1fdffb7cf4");
                HttpURLConnection urlConnection = (HttpURLConnection) urlApi.openConnection();
                InputStream in = urlConnection.getInputStream();
                BufferedReader bR = new BufferedReader(  new InputStreamReader(in));
                String line = "";
                StringBuilder responseStrBuilder = new StringBuilder();
                while((line =  bR.readLine()) != null){

                    responseStrBuilder.append(line);
                }
                in.close();
                try {
                    String datos = "";
                    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("estudiantes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject student = jsonArray.getJSONObject(i);
                        datos += "Name: "+student.getString("name")+ " Course: "+student.getString("course_name")+"\n";
                    }
                    publishProgress(datos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            visualizador.setText(values[0]);
        }
    }
}