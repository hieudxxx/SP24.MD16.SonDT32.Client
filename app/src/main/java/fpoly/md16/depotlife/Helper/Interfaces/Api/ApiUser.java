package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Staff.Model.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiUser {
    ApiUser apiUser = getRetrofit().create(ApiUser.class);

    @POST("login")
    Call<UserResponse> login(@Body UserResponse data);



    //    @GET("Staff")
//    Call<ArrayList<User>> getStaffList();
}
