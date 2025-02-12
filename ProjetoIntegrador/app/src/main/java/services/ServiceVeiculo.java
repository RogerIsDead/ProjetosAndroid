package services;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.Veiculos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ServiceVeiculo {

    @Headers("Accept: application/json")
    @GET("/Veiculo")
    Call<List<Veiculos>> getVeiculo();

    @Headers("Accept: application/json")
    @GET("/Veiculo/{id}")
    Call<Veiculos> getVeiculoById(@Path("id") Long id);
}