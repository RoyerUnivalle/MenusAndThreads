package com.example.menusandthreads.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.menusandthreads.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentA extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragmentA newInstance(String param1, String param2) {
        BlankFragmentA fragment = new BlankFragmentA();
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
        View f = inflater.inflate(R.layout.fragment_blank_a, container, false);
        Button btnPintar = f.findViewById(R.id.btnPintar);
        btnPintar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 10; i++) {
                    btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
                    btnPintar.setText("i: "+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return  f;
    }

    public int aleatorio(){
        return (int) (Math.random() * 254 + 1);
    }
}