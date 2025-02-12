package services;

import java.util.List;

import model.Cartao;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceCartao {

    @Headers("Accept: application/json")
    @POST("/cartao")
    Call<Cartao> postCartao(@Body Cartao cartao);

    @Headers("Accept: application/json")
    @GET("/cartao")
    Call<List<Cartao>> getCartao();

    @Headers("Accept: application/json")
    @GET("/cartao/usuario/{idUsuario}")
    Call<List<Cartao>> getCartaoByUsuarioId(@Path("idUsuario") long idUsuario);

    @Headers("Accept: application/json")
    @GET("/cartao{id}")
    Call<Cartao> selectCartao(@Path("id") long id);


    @Headers("Accept: application/json")
    @DELETE("/cartao/{id}")
    Call<Void> deleteCartao(@Path("id") Long id);
}
