package com.example.projetointegrador;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ServiceUsuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    public void fazerLogin(String email, String senha) {
        // Configuração do Retrofit
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();

        ServiceUsuario usuarioService = retrofit.create(ServiceUsuario.class);

        // Preparando os dados de login
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("email", email);
        loginMap.put("senha", senha);

        // Realizando a chamada
        usuarioService.login(loginMap).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    Map<String, Object> responseBody = response.body();
                    String message = (String) responseBody.get("message");
                    Boolean isAdmin = (Boolean) responseBody.get("admin");
                    Double userIdDouble = (Double) responseBody.get("userId");
                    Long userId = userIdDouble != null ? userIdDouble.longValue() : null;
                    Log.e("user" ,userId.toString());

                    if (isAdmin != null && isAdmin) {
                        // Usuário é admin
                        Toast.makeText(LoginActivity.this, "Login de Admin", Toast.LENGTH_SHORT).show();
                        Intent telaPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                        telaPrincipal.putExtra("user_id", userId);
                        startActivity(telaPrincipal);

                    } else {
                        // Usuário comum
                        Toast.makeText(LoginActivity.this, "Login realizado!", Toast.LENGTH_SHORT).show();
                        Intent telaPrincipal = new Intent(LoginActivity.this , MainActivity.class);
                        telaPrincipal.putExtra("user_id", userId);
                        startActivity(telaPrincipal);

                    }
                } else {
                    // Login falhou
                    try {
                        // Tratamento de erro
                        Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                // Erro de conexão
                Log.e("Retrofit Error", t.getMessage(), t);
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if( v == btnLogin){
            String email = etEmail.getText().toString();
            String senha = etPassword.getText().toString();

            Log.i("tag", "Email:" + email + "Senha:" + senha);

            fazerLogin(email, senha);
        }
        if ( v == btnRegister){
            Intent telaRegsitro = new Intent(this , RegisterActivity.class);
            startActivity(telaRegsitro);
        }



    }
}