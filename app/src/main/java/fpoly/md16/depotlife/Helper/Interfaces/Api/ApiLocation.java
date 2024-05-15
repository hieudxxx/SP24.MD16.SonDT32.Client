package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiLocation {
    ApiLocation apiLocation = getRetrofit().create(ApiLocation.class);
    @GET("location/get-zone")
    Call<List<Integer>> getZone(@Header("Authorization") String authToken);
    @GET("location/get-shelf")
    Call<List<String>> getShelf(@Header("Authorization") String authToken, @Query("zone") int zone);

    @GET("location/get-level")
    Call<List<Integer>> getLevel(@Header("Authorization") String authToken, @Query("shelf") String shelf);
}
