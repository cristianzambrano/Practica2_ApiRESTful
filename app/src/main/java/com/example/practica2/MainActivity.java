package com.example.practica2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practica2.Interfaces.IProcesaListaBancos;
import com.example.practica2.WebServices.Asynchtask;
import com.example.practica2.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    TextView txtUsr, txtClave, txtresp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtUsr =(TextView) findViewById(R.id.txtUsr);
        txtClave  =(TextView) findViewById(R.id.txtPassword);
        txtresp =(TextView) findViewById(R.id.txtRespuesta);

    }
    public void enviarWS(View view){

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService(
                getUrlLogin(),
                datos, MainActivity.this,
                MainActivity.this) ;
        ws.execute("GET");
    }
    public String getUrlLogin(){
        return "https://revistas.uteq.edu.ec/ws/login.php?"+
                "usr="    + txtUsr.getText().toString() +
                "&pass="  + txtClave.getText().toString();
    }
    public void enviarWSWithVolley(View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                getUrlLogin(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtresp.setText("Respuesta "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtresp.setText("Error de Conexiòn!");
                    }
                });

        queue.add(stringRequest);

    }

    public void getListaBancos(View view){
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new
                WebService("https://api-uat.kushkipagos.com/transfer/v1/bankList",
                datos, MainActivity.this,
                new IProcesaListaBancos(txtresp));

        ws.execute("GET","Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        //Parseo de los datos
        txtresp.setText(result);

    }
}