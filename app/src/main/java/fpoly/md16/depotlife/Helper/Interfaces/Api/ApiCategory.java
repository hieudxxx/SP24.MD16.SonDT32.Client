package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.ArrayList;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiCategory {

    ApiCategory apiCategory = getRetrofit().create(ApiCategory.class);

    @GET("category")
    Call<CategoryResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("category")
    Call<ArrayList<Category>> getCategoryList();

}
