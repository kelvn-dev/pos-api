package net.vinpos.api.config;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class RetrofitConfig {
  private final UniqodeConfig uniqodeConfig;

  @Bean
  public Retrofit uniqodeRetrofit() {
    OkHttpClient client =
        new OkHttpClient.Builder()
            .addInterceptor(
                new Interceptor() {
                  @Override
                  public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request requestWithHeaders =
                        originalRequest
                            .newBuilder()
                            .header("Authorization", "Token " + uniqodeConfig.getApiKey())
                            .build();
                    return chain.proceed(requestWithHeaders);
                  }
                })
            .build();

    return new Retrofit.Builder()
        .baseUrl(uniqodeConfig.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }
}
