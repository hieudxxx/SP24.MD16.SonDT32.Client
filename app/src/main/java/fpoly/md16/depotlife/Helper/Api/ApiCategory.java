package fpoly.md16.depotlife.Helper.Api;

import static fpoly.md16.depotlife.Helper.Api.API.URL1;

import java.util.ArrayList;

import fpoly.md16.depotlife.Category.Model.Category;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiCategory {

    ApiCategory apiCategory= new Retrofit.Builder()
            .baseUrl(URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCategory.class);

    @GET("Category")
    Call<ArrayList<Category>> getCategoryList();
}
