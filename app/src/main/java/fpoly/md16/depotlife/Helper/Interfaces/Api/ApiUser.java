package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Login.Model.UserResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiUser {
    ApiUser apiUser = getRetrofit().create(ApiUser.class);

    @POST("login")
    Call<UserResponse> login(@Body UserResponse data);


    @GET("staff")
    Call<StaffResponse> getStaffList(@Header("Authorization") String authToken,@Query("page") int page_index);

    @GET("staff/get-id/{id}")
    Call<StaffResponse.User> getStaffById(@Header("Authorization") String authToken, @Path("id") int id);
}
