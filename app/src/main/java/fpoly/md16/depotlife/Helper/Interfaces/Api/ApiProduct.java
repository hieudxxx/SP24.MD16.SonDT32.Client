package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.API.URL;

import java.util.ArrayList;

import fpoly.md16.depotlife.Product.Model.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiProduct {
    ApiProduct apiProduct = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProduct.class);

    @GET("Product")
    Call<ArrayList<Product>> getProductList();
}
