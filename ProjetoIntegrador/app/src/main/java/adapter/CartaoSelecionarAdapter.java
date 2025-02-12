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

public class CartaoSelecionarAdapter extends ArrayAdapter<Cartao> {
    private List<Cartao> cartaoList;
    private Context context;
    private OnCartaoSelectedListener listener;


    public CartaoSelecionarAdapter(Context context, List<Cartao> cartaoList, OnCartaoSelectedListener listener) {
        super(context, R.layout.item_cartao, cartaoList);
        this.context = context;
        this.cartaoList = cartaoList;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cartao_selecionar, parent, false);
        }

        TextView tvCardInfo = convertView.findViewById(R.id.tvCardInfo);
        Button btnSelectCard = convertView.findViewById(R.id.btnSelectCard);

        Cartao cartao = cartaoList.get(position);
        String nCartao = cartao.getNumeroCartao();
        tvCardInfo.setText("Final do cartão: " + nCartao.substring(nCartao.length() - 4));

        btnSelectCard.setText("Selecionar");

        btnSelectCard.setOnClickListener(v -> {
            // Lógica para selecionar o cartão
            Toast.makeText(context, "Cartão Selecionado: " + cartao.getNumeroCartao(), Toast.LENGTH_SHORT).show();
            if (listener != null ){
                listener.onCartaoSelected(cartao.getIdCartao());
            }
        });

        return convertView;
    }
    public interface OnCartaoSelectedListener {
        void onCartaoSelected(Long cartaoId);
    }
}