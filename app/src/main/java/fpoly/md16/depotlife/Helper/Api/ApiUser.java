package fpoly.md16.depotlife.Helper.Api;

import static fpoly.md16.depotlife.Helper.Api.API.URL2;

import java.util.ArrayList;

import fpoly.md16.depotlife.Staff.Model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiUser {
    ApiUser apiUser = new Retrofit.Builder()
            .baseUrl(URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiUser.class);

    @GET("Staff")
    Call<ArrayList<User>> getStaffList();
}
