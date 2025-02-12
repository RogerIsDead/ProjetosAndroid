package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import model.Cartao;
import model.Usuario;
import model.Veiculos;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceCartao;
import services.ServiceUsuario;

public class AddCartaoActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText edtName;
    TextInputEditText edtNumeroCartao;
    TextInputEditText edtCodSeguranca;
    TextInputEditText edtDataVencimento;
    Button btnRegistrar;
    Button btnVoltar;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cartao);

        edtName = findViewById(R.id.edtName);
        edtNumeroCartao = findViewById(R.id.edtNumeroCartao);
        edtCodSeguranca = findViewById(R.id.edtCodSeguranca);
        edtDataVencimento = findViewById(R.id.edtDataVencimento);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(this);

        Intent intent = getIntent();
        userId = intent.getLongExtra("user_id", 0);

    }

    public void registrarCartao(Cartao cartao) {
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();

        ServiceCartao cartaoService = retrofit.create(ServiceCartao.class);

        cartaoService.postCartao(cartao).enqueue(new Callback<Cartao>() {
            @Override
            public void onResponse(Call<Cartao> call, Response<Cartao> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddCartaoActivity.this, "Cartão cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    try {
                        if(response.code() == 409){
                            Toast.makeText(AddCartaoActivity.this, "Cartão já cadastrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddCartaoActivity.this, "Erro ao cadastrar cartão", Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }

            }

            @Override
            public void onFailure(Call<Cartao> call, Throwable t) {
                Toast.makeText(AddCartaoActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v == btnRegistrar){
            String name = edtName.getText().toString();
            String nCartao = edtNumeroCartao.getText().toString();
            Integer codSeguranca = Integer.valueOf(edtCodSeguranca.getText().toString());
            String dataVencimento = edtDataVencimento.getText().toString();


            if (name.isEmpty()) {
                edtName.setError("Nome do titular é obrigatório");
                edtName.requestFocus();
                return;
            }
            if (nCartao.isEmpty()) {
                edtNumeroCartao.setError("Número do cartão é obrigatório");
                edtNumeroCartao.requestFocus();
                return;
            }
            if (codSeguranca == null) {
                edtCodSeguranca.setError("Código de segurança é obrigatório");
                edtCodSeguranca.requestFocus();
                return;
            }
            if (dataVencimento.isEmpty()) {
                edtDataVencimento.setError("A data de vencimento é obrigatória");
                edtDataVencimento.requestFocus();
                return;
            }
            Cartao cartao = new Cartao(name, userId, nCartao, codSeguranca, dataVencimento);

            registrarCartao(cartao);
        }
        if (v == btnVoltar){
            finish();
        }
    }
}