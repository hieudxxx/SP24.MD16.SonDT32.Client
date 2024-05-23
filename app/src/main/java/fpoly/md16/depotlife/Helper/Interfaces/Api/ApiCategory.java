package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCategory {
    ApiCategory apiCategory = getRetrofit().create(ApiCategory.class);

    @GET("category")
    Call<CategoryResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @POST("category/create")
    Call<Category> create(@Header("Authorization") String authToken, @Body Category data);
    @POST("category/update/{id}")
    Call<Category> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Category data);

    @GET("category/delete/{id}")
    Call<Category> delete(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("category/search")
    Call<List<Category>> getDataSearch(@Header("Authorization") String authToken, @Query("keyword") String keyword);
}
