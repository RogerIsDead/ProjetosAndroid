package com.example.projetointegrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import model.Usuario;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ServiceUsuario;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText edtName;
    TextInputEditText edtEmail;
    TextInputEditText edtSenha;
    TextInputEditText edtConfirmaSenha;
    Button btnRegistrar;
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

    }

    public void registrarUsuario(Usuario usuario) {
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();

        ServiceUsuario usuarioService = retrofit.create(ServiceUsuario.class);

        usuarioService.postUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    try {
                        if(response.code() == 409){
                            Toast.makeText(RegisterActivity.this, "Email já cadastrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v == btnRegistrar){
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String confirmaSenha = edtConfirmaSenha.getText().toString();


            if (name.isEmpty()) {
                edtName.setError("Nome é obrigatório");
                edtName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                edtEmail.setError("E-mail é obrigatório");
                edtEmail.requestFocus();
                return;
            }
            if (senha.isEmpty()) {
                edtSenha.setError("Senha é obrigatória");
                edtSenha.requestFocus();
                return;
            }
            if (confirmaSenha.isEmpty() || !confirmaSenha.equals(senha)){
                edtConfirmaSenha.setError("As senhas devem ser iguais");
                edtConfirmaSenha.requestFocus();
                return;
            }

            Usuario user = new Usuario(name, email, senha, false);

            registrarUsuario(user);
        }
        if (v == btnVoltar){
            finish();
        }
    }
}