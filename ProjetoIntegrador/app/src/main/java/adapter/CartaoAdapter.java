package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetointegrador.R;

import java.util.List;

import model.Cartao;
import retrofit.RetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ServiceCartao;

public class CartaoAdapter extends ArrayAdapter<Cartao> {
    private List<Cartao> cartaoList;
    private Context context;

    public CartaoAdapter(Context context, List<Cartao> cartaoList) {
        super(context, R.layout.item_cartao, cartaoList);
        this.context = context;
        this.cartaoList = cartaoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cartao, parent, false);
        }

        TextView tvCardInfo = convertView.findViewById(R.id.tvCardInfo);
        Button btnRemoveCard = convertView.findViewById(R.id.btnRemoveCard);

        Cartao cartao = cartaoList.get(position);
        String nCartao = cartao.getNumeroCartao();
        tvCardInfo.setText("Final do cartão: " + nCartao.substring(nCartao.length() - 4)); // Ajuste conforme a propriedade do cartão.

        btnRemoveCard.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitManager.getRetrofitInstance();
            ServiceCartao serviceCartao = retrofit.create(ServiceCartao.class);

            serviceCartao.deleteCartao(cartao.getIdCartao()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        cartaoList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cartão removido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Erro ao remover cartão", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return convertView;
    }
}

