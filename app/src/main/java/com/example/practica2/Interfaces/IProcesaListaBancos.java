package com.example.practica2.Interfaces;

import android.widget.TextView;

import com.example.practica2.WebServices.Asynchtask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IProcesaListaBancos implements Asynchtask {
     TextView txtresp;
    public IProcesaListaBancos(TextView txtresp) {
        this.txtresp = txtresp;
    }

    @Override
    public void processFinish(String result) throws JSONException {
        //Parseo Lista Bancos
        String listaBancos="";
        JSONArray JSONlista =  new JSONArray(result);
        for(int i = 1; i< JSONlista.length(); i++){
            JSONObject banco=  JSONlista.getJSONObject(i);
            listaBancos  += banco.getString("code")
                    + " - " + banco.getString("name") +"\n" ;
        }
        txtresp.setText("Lista Bancos: \n" + listaBancos);
    }
}
