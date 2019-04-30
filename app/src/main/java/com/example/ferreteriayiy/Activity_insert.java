package com.example.ferreteriayiy;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_insert extends AppCompatActivity {

    EditText et_nombre_ai;
    EditText et_apellido_paterno_ai;
    EditText et_apellido_materno_ai;
    EditText et_telefono_ai;
    EditText et_email;

    String nombre_i;
    String apellido_p;
    String apellido_m;
    String telefono_i;
    String email_i;

    private String webservice_url = "https://ferreteriayiy.herokuapp" +
            ".com/api_clientes?user_hash=12345&action=put&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        setContentView(R.layout.activity_insert);
        //inicialización de EditText de la vista
        et_nombre_ai = findViewById(R.id.et_nombre_ai);
        et_apellido_paterno_ai = findViewById(R.id.et_apellido_paterno_ai);
        et_apellido_materno_ai = findViewById(R.id.et_apellido_materno_ai);
        et_telefono_ai = findViewById(R.id.et_telefono_ai);
        et_email = findViewById(R.id.et_email);


    }

    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(webservice_url);

        nombre_i = (et_nombre_ai.getText().toString());
        apellido_p = (et_apellido_paterno_ai.getText().toString());
        apellido_m = (et_apellido_materno_ai.getText().toString());
        telefono_i = (et_telefono_ai.getText().toString());
        email_i = (et_email.getText().toString());

        String nombre= nombre_i.replace(" ","%20");
        String apellido_paterno= apellido_p.replace(" ","%20");
        String apellido_materno= apellido_m.replace(" ","%20");
        String telefono = telefono_i.replace(" ","%20");
        String email = email_i.replace(" ","%20");

        sb.append("nombre="+nombre);
        sb.append("&");
        sb.append("apellido_paterno="+apellido_paterno);
        sb.append("&");
        sb.append("apellido_materno="+apellido_materno);
        sb.append("&");
        sb.append("telefono="+telefono);
        sb.append("&");
        sb.append("email="+email);


        webServicePut(sb.toString());
        Log.e("URL",sb.toString());
    }
    private void webServicePut(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
}
