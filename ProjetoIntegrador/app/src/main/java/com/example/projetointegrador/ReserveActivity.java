package com.example.projetointegrador;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import adapter.CartaoSelecionarAdapter;
import model.Cartao;
import model.Reserva;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceCartao;
import services.ServiceReserva;

public class ReserveActivity extends AppCompatActivity implements CartaoSelecionarAdapter.OnCartaoSelectedListener {

    private ListView lvCards;
    private TextView tvCarName, tvTotalValue, tvDays;
    private Button btnConfirmReservation;
    List<Cartao> cartaoList = new ArrayList<>();
    CartaoSelecionarAdapter adapter;
    private String carName;
    private float valorReserva;
    private Long carId, userId;
    private int days;
    private String startDate,endDate;
    private Long selectedCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        lvCards = findViewById(R.id.lvCards);
        tvCarName = findViewById(R.id.tvCarName);
        tvTotalValue = findViewById(R.id.tvTotalValue);
        tvDays = findViewById(R.id.tvDays);
        btnConfirmReservation = findViewById(R.id.btnConfirmReservation);

        // Recuperar dados da intent
        carName = getIntent().getStringExtra("car_name");
        valorReserva = getIntent().getFloatExtra("car_valorReserva", 0f);
        carId = getIntent().getLongExtra("car_id", 0L);
        userId = getIntent().getLongExtra("user_id", 0L);
        days = getIntent().getIntExtra("days", 0);
        startDate = getIntent().getStringExtra("start_date");
        endDate = getIntent().getStringExtra("end_date");
        Log.e("start", startDate);
        Log.e("start", endDate);

        Log.e("valor", String.valueOf(valorReserva));
        Log.e("car_id", String.valueOf(carId));
        Log.e("user_id", String.valueOf(userId));
        Log.e("days", String.valueOf(days));

        // Configurar informações do carro
        tvCarName.setText("Carro: " + carName);
        tvTotalValue.setText("Valor Total: R$ " + valorReserva);
        tvDays.setText("Dias: " + days);

        adapter = new CartaoSelecionarAdapter(this, cartaoList, this);
        lvCards.setAdapter(adapter);

        listarCartao(userId);
        btnConfirmReservation.setOnClickListener(v -> confirmReservation());
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
                    Toast.makeText(ReserveActivity.this, "Erro ao carregar a lista de cartões", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Cartao>> call, Throwable t) {
                Toast.makeText(ReserveActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onCartaoSelected(Long cartaoId) {
        selectedCardId = cartaoId; // Salvar o id do cartão selecionado
    }



    private void confirmReservation() {
        if (selectedCardId == null) {
            Toast.makeText(this, "Por favor, selecione um cartão", Toast.LENGTH_SHORT).show();
            return;
        }

        Reserva reserva = new Reserva();
        reserva.setIdUsuario(userId);
        reserva.setIdVeiculo(carId);
        reserva.setIdCartao(selectedCardId);
        reserva.setValor(valorReserva);
        reserva.setDataRetirada(startDate);
        reserva.setDataDevolcucao(endDate);


        Retrofit retrofit = RetrofitManager.getRetrofitInstance();
        ServiceReserva serviceReserva = retrofit.create(ServiceReserva.class);

        serviceReserva.createReserva(reserva).enqueue(new Callback<Reserva>() {
                @Override
                public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ReserveActivity.this, "Reserva confirmada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Fecha a atividade após a confirmação
                    } else {
                        Toast.makeText(ReserveActivity.this, "Erro ao confirmar reserva", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Reserva> call, Throwable t) {
                    Toast.makeText(ReserveActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });

    }
}