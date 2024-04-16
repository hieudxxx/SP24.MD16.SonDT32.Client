package fpoly.md16.depotlife.Helper.Interfaces.Api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private static Retrofit retrofit = null;
//    static String token;
//
//    public static String getToken(Context context) {
//        token = (String) Helper.getSharedPre(context, "token", String.class);
//        return token;
//    }
//
//    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//            Request original = chain.request();
//            Request request = original.newBuilder()
//                    .header("Authorization", "Bearer " + token) // Thêm token vào header "Authorization"
//                    .method(original.method(), original.body())
//                    .build();
//            return chain.proceed(request);
//        }
//    });

    public static Retrofit getRetrofit() {
        return retrofit = new Retrofit.Builder()
                .baseUrl("https://warehouse.sinhvien.io.vn/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(httpClient.build())
                .build();
    }
}
