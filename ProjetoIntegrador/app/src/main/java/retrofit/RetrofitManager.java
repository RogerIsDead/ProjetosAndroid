package retrofit;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;
    private static String baseUrl = "http://172.16.221.17:8080/"; // URL base inicial

    // Método para alterar a URL base
    public static void setBaseUrl(String newBaseUrl) {
        baseUrl = newBaseUrl;
        retrofit = null; // Força a criação de uma nova instância do Retrofit
    }

    // Método para obter a instância do Retrofit
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) // URL base configurável
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}