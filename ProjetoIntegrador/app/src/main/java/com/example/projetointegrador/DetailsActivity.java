package com.example.projetointegrador;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView carImageView;
    TextView carNameTextView, carPriceTextView,carDescriptionTextView,carFeaturesTextView;
    ExtendedFloatingActionButton reserveButton;

    BottomNavigationView bottomNavigationView;
    Long userId;
    String text;
    Long carId;
    String carName;
    String carPrice;
    boolean carDisp;
    String carFeatures;
    String carImageUrl;
    TextInputEditText dataInicio;
    TextInputEditText dataRetorno;
    Date inicio;
    Date retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        // Configurar a Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        carImageView = findViewById(R.id.carImageView);
        carNameTextView = findViewById(R.id.carNameTextView);
        carPriceTextView = findViewById(R.id.carPriceTextView);
        carDescriptionTextView = findViewById(R.id.carDescriptionTextView);
        carFeaturesTextView = findViewById(R.id.carFeaturesTextView);
        reserveButton = findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(this);

        dataInicio = findViewById(R.id.edtDataInicio);
        dataRetorno = findViewById(R.id.edtRetorno);
        dataInicio.setFocusable(false); // Impede que o teclado apareça
        dataInicio.setOnClickListener(v -> mostrarCalendario(dataInicio));
        dataRetorno.setFocusable(false); // Impede que o teclado apareça
        dataRetorno.setOnClickListener(v -> mostrarCalendario(dataRetorno));

        // Receber os dados via Intent
        Intent intent = getIntent();
        carId = intent.getLongExtra("car_id", 0);
        carName = intent.getStringExtra("car_nome");
        carPrice = intent.getStringExtra("car_preco");
        carDisp = intent.getBooleanExtra("car_disponibilidade", false);
        carFeatures = intent.getStringExtra("car_detalhamento");
        carImageUrl = intent.getStringExtra("car_image_url");
        userId = intent.getLongExtra("user_id", 0);
        Log.e("userdetails", userId.toString());
        Log.e("CARdetails", String.valueOf(carId));
        Log.e("booldetails", String.valueOf(carDisp));



        // Configurar os dados na UI
        carNameTextView.setText(carName);
        carPriceTextView.setText("Valor por dia R$: " + carPrice);
        if (carDisp) {
            text = "Disponível";
            carDescriptionTextView.setTextColor(Color.GREEN);
        } else {
            text = "Indisponível";
            carDescriptionTextView.setTextColor(Color.RED);
        }
        carDescriptionTextView.setText(text);
        carFeaturesTextView.setText(carFeatures);

        // Carregar a imagem usando Picasso
        Glide.with(this)
                .load(carImageUrl)
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.home)
                .into(carImageView);

    }

    @Override
    public void onClick(View v) {
        if(!carDisp){
            Toast.makeText(this, "Veiculo indisponível para reserva.", Toast.LENGTH_SHORT).show();
            return;
        }
        String startDate = dataInicio.getText().toString();
        String endDate = dataRetorno.getText().toString();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Por favor, selecione ambas as datas.", Toast.LENGTH_SHORT).show();
            return;
        }

        long days = calcularDias(startDate, endDate);
        if (days >= 0) {
            Toast.makeText(this, "Dias entre as datas: " + days, Toast.LENGTH_SHORT).show();
            Float preco = Float.parseFloat(carPrice);
            Float valorReserva = (float)(days*preco);
            // Abrir a Activity de reserva
            Intent reserveIntent = new Intent(DetailsActivity.this, ReserveActivity.class);
            reserveIntent.putExtra("car_name", carName);
            reserveIntent.putExtra("car_valorReserva", valorReserva);
            reserveIntent.putExtra("car_id", carId);
            reserveIntent.putExtra("user_id", userId);
            reserveIntent.putExtra("days", (int)days); // Passa os dias calculados
            reserveIntent.putExtra("start_date", startDate); // Adiciona a data de início
            reserveIntent.putExtra("end_date", endDate); // Adiciona a data de retorno
            Log.e("Detailsvalor", String.valueOf(valorReserva));
            Log.e("Detailscar_id", String.valueOf(carId));
            Log.e("Detailsuser_id", String.valueOf(userId));
            Log.e("Detailsdays", String.valueOf(days));
            startActivity(reserveIntent);
        } else {
            Toast.makeText(this, "A data de retorno deve ser maior que a data de início.", Toast.LENGTH_SHORT).show();
        }
        Log.d("DateDebug", "Data de início: " + startDate);
        Log.d("DateDebug", "Data de retorno: " + endDate);
        Log.d("DateDebug", "Dias calculados: " + days);
    }

    private long calcularDias(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            if (start != null && end != null) {
                long diffInMillis = end.getTime() - start.getTime();

                if (diffInMillis < 0) {
                    Log.e("DateError", "Data de início é maior que a data de retorno.");
                    return -1;
                }

                return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            } else {
                Log.e("DateError", "Erro ao converter datas.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("DateError", "Erro ao fazer parse das datas: " + e.getMessage());
        }
        return -1; // Retorna -1 para indicar erro
    }

    private void mostrarCalendario (TextInputEditText textInputEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
            textInputEditText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }


}