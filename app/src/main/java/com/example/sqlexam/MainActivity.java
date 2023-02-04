package com.example.sqlexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.mimecraft.FormEncoding;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText editTextTextPassword, login;
    Button btnsignin1, btnRegistration;


    TextView status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        login = (EditText) findViewById(R.id.login);
        btnsignin1 = (Button) findViewById(R.id.btnsignin1);
        status = (TextView) findViewById(R.id.status);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnsignin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPOST(v);
            }
        });
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistredActivity.class);
                startActivity(intent);
            }
        });
    }

    public void sendPOST(View view) {
        // создаем singleton объект клиента
        String Url = "https://da4e-198-199-101-87.ngrok.io"; // dexgo.ru
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Url).newBuilder();
        urlBuilder.addQueryParameter("token", "dexgo-DDeBNZndzV=I56ofAde1ZLCYVzh-hPakcpEM?1hM/WNsrWNHjXsts7AC8uSSYfLXho1sRsZjOBst4UjMUtjCtKPSq!Deibsx/Aws09pZ7LyZuU-s3FVMB1Bz4EknzBPzR-!HtV6F4ddTqtkDJ2rG!lI5cysP!stPcHWgtN3-t17EscMGuVaXYx?tPCLvADiO3mOVWuUucR-F!qcN5UiqWWtbnO8nvZEBWXDBnU7AA7Pjbs8dAZrBsORSUf");
        urlBuilder.addQueryParameter("login", login.getText().toString());
        urlBuilder.addQueryParameter("password", editTextTextPassword.getText().toString());
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .cacheControl(new CacheControl.Builder().maxStale(30, TimeUnit.DAYS).build())
                .build();
        // выполняем запрос
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // читаем данные в отдельном потоке
                    final String responseData = response.body().string();

                    // выполняем операции по обновлению UI
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            status.setText("OK");
                        }
                    });
                }
            }
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}