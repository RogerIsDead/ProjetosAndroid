package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetointegrador.DetailsActivity;
import com.example.projetointegrador.MainActivity;
import com.example.projetointegrador.R;

import java.util.List;

import model.Veiculos;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceVeiculo;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Veiculos> veiculosList;
    private Long userId;
    private Context context;

    public CarAdapter(Context context, List<Veiculos> veiculosList, Long userId) {
        this.veiculosList = veiculosList;
        this.userId = userId;
        this.context = context;
    }



    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Veiculos veiculo = veiculosList.get(position);
        holder.carNameTextView.setText(veiculo.getNome());
        holder.carPriceTextView.setText("Diária: R$ " + veiculo.getPreco().toString());


        Glide.with(holder.itemView.getContext())
                .load(veiculo.getImgUrl())
                .placeholder(R.drawable.app_logo)
                .error(R.drawable.home)
                .into(holder.carImageView);

        holder.itemView.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitManager.getRetrofitInstance();
            ServiceVeiculo veiculoService = retrofit.create(ServiceVeiculo.class);
            veiculoService.getVeiculoById(veiculo.getId()).enqueue(new Callback<Veiculos>() {
                @Override
                public void onResponse(Call<Veiculos> call, Response<Veiculos> response) {
                    if(response.isSuccessful()){
                        Veiculos veiculoAtualizado = response.body();
                        Intent telaDetalhes = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                        telaDetalhes.putExtra("car_id", veiculoAtualizado.getId());
                        telaDetalhes.putExtra("car_nome", veiculoAtualizado.getNome());
                        telaDetalhes.putExtra("car_preco", veiculoAtualizado.getPreco().toString());
                        telaDetalhes.putExtra("car_detalhamento", veiculoAtualizado.getDetalhamento());
                        telaDetalhes.putExtra("car_image_url", veiculoAtualizado.getImgUrl());
                        telaDetalhes.putExtra("car_disponibilidade", veiculoAtualizado.getDisponivel());
                        telaDetalhes.putExtra("user_id", userId);
                        Log.e("caradapterid", String.valueOf(veiculo.getId()));
                        holder.itemView.getContext().startActivity(telaDetalhes);
                    } else{
                        Toast.makeText(context, "Erro ao remover cartão", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Veiculos> call, Throwable t) {

                }
            });


        });
    }

    @Override
    public int getItemCount() {
        return veiculosList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView carNameTextView;
        TextView carPriceTextView;
        ImageView carImageView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carNameTextView = itemView.findViewById(R.id.carNameTextView);
            carPriceTextView = itemView.findViewById(R.id.carPriceTextView);
            carImageView = itemView.findViewById(R.id.carImageView);

        }
    }
}

