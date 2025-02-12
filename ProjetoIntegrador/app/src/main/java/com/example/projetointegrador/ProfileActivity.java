package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import adapter.CarAdapter;
import adapter.CartaoAdapter;
import model.Cartao;
import model.Usuario;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceCartao;
import services.ServiceUsuario;
import services.ServiceVeiculo;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayAdapter<Cartao> adapter;
    List<Cartao> cartaoList = new ArrayList<>();
    private TextView tvName;
    private TextView tvEmail;
    private ListView rvCards;
    private Button btnAddCard;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        rvCards = findViewById(R.id.rvCards);
        btnAddCard = findViewById(R.id.btnAddCard);
        btnAddCard.setOnClickListener(this);

        Intent intent = getIntent();
        userId = intent.getLongExtra("user_id", 0);

        adapter = new CartaoAdapter(this, cartaoList);
        rvCards.setAdapter(adapter);

        listarPerfil(userId);
        listarCartao(userId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarCartao(userId);
    }

    public void listarCartao(Long userId){
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();
        ServiceCartao cartaoService = retrofit.create(ServiceCartao.class);

        cartaoService.getCartaoByUsuarioId(userId).enqueue(new Callback<List<Cartao>>() {
            @Override
            public void onResponse(Call<List<Cartao>> call, Response<List<Cartao>> response) {
                if(response.isSuccessful()){
                    cartaoList.clear();
                    cartaoList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Erro ao carregar a lista de cartões", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Cartao>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void listarPerfil(Long userId){
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();
        ServiceUsuario usuarioService = retrofit.create(ServiceUsuario.class);

        usuarioService.getUsuarioById(userId).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Usuario usuario = response.body();

                    tvName.setText(usuario.getNome());
                    tvEmail.setText(usuario.getEmail());
                } else{
                    tvName.setText("Erro ao carregar nome");
                    tvEmail.setText("Erro ao carregar email");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                tvName.setText("Falha na conexão");
                tvEmail.setText("Falha na conexão");
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v == btnAddCard){
            Intent telaCadastroCartao = new Intent(ProfileActivity.this, AddCartaoActivity.class);
            telaCadastroCartao.putExtra("user_id", userId);
            startActivity(telaCadastroCartao);
        }
    }
}