package services;

import java.util.Map;
import java.util.Objects;

import model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceUsuario {

    @Headers("Accept: application/json")
    @POST("/usuario")
    Call<Usuario> postUsuario(@Body Usuario usuario);

    @Headers("Accept: application/json")
    @GET("/usuario")
    Call<Usuario> getUsuario();

    @Headers("Accept: application/json")
    @GET("/usuario/{id}")
    Call<Usuario> getUsuarioById(@Path("id") long id);

    @Headers("Accept: application/json")
    @POST("/login")
    Call<Map<String, Object>> login(@Body Map<String, Object> loginData);
}