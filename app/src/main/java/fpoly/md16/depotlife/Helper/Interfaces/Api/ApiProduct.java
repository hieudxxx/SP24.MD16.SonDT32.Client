package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Product.Model.ImagesResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiProduct {
    ApiProduct apiProduct = getRetrofit().create(ApiProduct.class);

    @GET("products")
    Call<ProductResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("products/get-id/{id}")
    Call<Product> getProductById(@Header("Authorization") String authToken, @Path("id") int id);
    @GET("products/get-images/{id}/{pin_image}")
    Call<ImagesResponse> getProductImages(@Header("Authorization") String authToken, @Path("id") int id, @Path("pin_image") String pin_image);

    @POST("products/update/{id}")
    Call<Product> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Product data);

//    @POST("products/update/{id}")
//    Call<Product> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Product data);

    @GET("products/delete/{id}")
    Call<Product> delete(@Header("Authorization") String authToken, @Path("id") int id);


}
