package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import adapter.CarAdapter;
import model.Usuario;
import model.Veiculos;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ServiceUsuario;
import services.ServiceVeiculo;

public class MainActivity extends AppCompatActivity {

    RecyclerView lista;
    List<Veiculos> veiculosList;
    BottomNavigationView bottomNavigationView;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getLongExtra("user_id", 0);
        Log.e("userMain", userId.toString());

        lista = findViewById(R.id.carRecyclerView);
        lista.setLayoutManager(new LinearLayoutManager(this)); // Configurar layout vertical
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_home){
                    return true;
                }
                else{
                    if(item.getItemId() == R.id.action_profile)
                    {
                        Intent telaPerfil = new Intent(MainActivity.this, ProfileActivity.class);
                        telaPerfil.putExtra("user_id", userId);
                        startActivity(telaPerfil);
                        return true;
                    }
                    else{
                        if(item.getItemId() == R.id.action_reserve){
                            Intent telaPerfil = new Intent(MainActivity.this, UserReserveActivity.class);
                            telaPerfil.putExtra("user_id", userId);
                            startActivity(telaPerfil);
                            return true;
                        } else return false;
                    }

                }
            }
        });

        listarVeiculos();
    }



    public void listarVeiculos() {
        Retrofit retrofit = RetrofitManager.getRetrofitInstance();

        ServiceVeiculo veiculoService = retrofit.create(ServiceVeiculo.class);

        veiculoService.getVeiculo().enqueue(new Callback<List<Veiculos>>() {
            @Override
            public void onResponse(Call<List<Veiculos>> call, Response<List<Veiculos>> response) {
                if(response.isSuccessful()){
                    veiculosList = response.body();
                    runOnUiThread(() -> {
                        CarAdapter adapter = new CarAdapter(MainActivity.this, veiculosList, userId);
                        lista.setAdapter(adapter);
                    });

                } else { Toast.makeText(MainActivity.this, "Erro ao carregar a lista de veiculos", Toast.LENGTH_SHORT).show(); }

            }
            @Override
            public void onFailure(Call<List<Veiculos>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao se comunicar com o servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}