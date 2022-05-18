package com.example.menusandthreads.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.menusandthreads.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentB extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnPintar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentB() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentB.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentB newInstance(String param1, String param2) {
        BlankFragmentB fragment = new BlankFragmentB();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View g = inflater.inflate(R.layout.fragment_blank_b, container, false);
        //Button btnPintar;
        btnPintar = g.findViewById(R.id.btnPintar2);
        btnPintar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new Thread(new Runnable() {
            @Override
            public void run() {
                Pintar objPintar = new Pintar();
                objPintar.execute();
                //objPintar.cancel(true);
                /*for (int i = 0; i <= 10; i++) {
                    //System.out.println(aleatorio());
                    btnPintar.setBackgroundColor(Color.rgb(aleatorio(), aleatorio(), aleatorio()));
                    btnPintar.setText("Pintar: " + i);
                    //if(i == 10) {
                      //  Toast.makeText(getContext(), "Terminé", Toast.LENGTH_LONG).show();
                   // }
                    try {
                        Thread.sleep(1000); //<-- en este contexto hace referencia al hilo recien creado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        }).start();
            }
        });
        return  g;
    }
    public int aleatorio(){
        return (int) (Math.random() * 254 + 1);
    }

    public class Pintar extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= 10; i++) {
                if(isCancelled()){
                    break;
                }else {
                    //System.out.println(aleatorio());
                    btnPintar.setBackgroundColor(Color.rgb(aleatorio(), aleatorio(), aleatorio()));
                    btnPintar.setText("Pintar: " + i);
                    publishProgress();
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
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(getContext(), "Terminé", Toast.LENGTH_LONG).show();
        }
    }
}