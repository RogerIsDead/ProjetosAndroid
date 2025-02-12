package services;

import java.util.List;

import model.Cartao;
import model.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceReserva {
    @POST("/reserva")
    Call<Reserva> createReserva(@Body Reserva reserva);

    @Headers("Accept: application/json")
    @GET("/reserva/usuario/{idUsuario}")
    Call<List<Reserva>> getReservasByIdUsuario(@Path("idUsuario") long idUsuario);
}