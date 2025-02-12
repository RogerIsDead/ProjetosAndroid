package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import model.Reserva;
import model.Veiculos;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceReserva;
import services.ServiceVeiculo;

public class UserReserveActivity extends AppCompatActivity {

    Long userId;
    ListView reservaList;
    ArrayAdapter<Reserva> adapter;
    List<Reserva> reservasList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_reserve);

        Intent intent = getIntent();
        userId = intent.getLongExtra("user_id", 0);

        reservaList = findViewById(R.id.reservaList);
        bottomNavigationView = findViewById(R.id.bottomNavigationReserve);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservasList);
        reservaList.setAdapter(adapter);

        listarReservas(userId);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
                    Intent telaPerfil = new Intent(UserReserveActivity.this, MainActivity.class);
                    telaPerfil.putExtra("user_id", userId);
                    startActivity(telaPerfil);
                    return true;
                } else {
                    if (item.getItemId() == R.id.action_profile) {
                        Intent telaPerfil = new Intent(UserReserveActivity.this, ProfileActivity.class);
                        telaPerfil.putExtra("user_id", userId);
                        startActivity(telaPerfil);
                        return true;
                    } else {
                        if (item.getItemId() == R.id.action_reserve) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        });
    }

    public void listarReservas(Long userId) {
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();

        ServiceReserva reservaService = retrofit.create(ServiceReserva.class);

        reservaService.getReservasByIdUsuario(userId).enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (response.isSuccessful()) {
                    reservasList.clear();
                    reservasList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(UserReserveActivity.this, "Erro ao carregar a lista de veiculos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Toast.makeText(UserReserveActivity.this, "Erro ao se comunicar com o servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
