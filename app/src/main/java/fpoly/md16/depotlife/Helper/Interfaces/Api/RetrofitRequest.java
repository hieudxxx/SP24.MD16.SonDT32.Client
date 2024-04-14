package fpoly.md16.depotlife.Helper.Interfaces.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private static Retrofit retrofit = null;
    public static Retrofit getRetrofit(){
        return retrofit = new Retrofit.Builder()
                .baseUrl("https://warehouse.sinhvien.io.vn/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
