package com.example.menusandthreads.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.example.menusandthreads.fragments.BlankFragmentB;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*for (int i = 0; i < 10; i++) {
            Date date = new Date(); // This object contains the current date value
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Toast.makeText(this,"hola item1: "+formatter.format(date),Toast.LENGTH_SHORT).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    //System.out.println(aleatorio());
                    Date date = new Date(); // This object contains the current date value
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Toast.makeText(getApplicationContext(),"hola item1: "+formatter.format(date),Toast.LENGTH_SHORT).show();
                    //if(i == 10) {
                      //  Toast.makeText(getContext(), "Terminé", Toast.LENGTH_LONG).show();
                   // }
                    try {
                        Thread.sleep(1000); //<-- en este contexto hace referencia al hilo recien creado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
        MostrarFecha ObjMostrarFecha = new MostrarFecha();
        ObjMostrarFecha.execute();
    }

    public class MostrarFecha extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= 10; i++) {
                if(isCancelled()){
                    break;
                }else {
                    //System.out.println(aleatorio());
                    publishProgress();
                    /*if(i == 10) {
                    stopSelf();
                    }*/
                    try {
                        Thread.sleep(1000); //<-- en este contexto hace referencia al hilo recien creado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Date date = new Date(); // This object contains the current date value
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Toast.makeText(getApplicationContext(),"hola item1: "+formatter.format(date),Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(getApplicationContext(), "Terminé", Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }
}