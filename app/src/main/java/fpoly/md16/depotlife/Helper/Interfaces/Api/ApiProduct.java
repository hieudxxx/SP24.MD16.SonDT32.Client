package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Product.Model.ImagesResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("products/update/{id}")
    Call<Product> update(@Header("Authorization") String authToken,
                         @Path("id") int id,
                         @Part("product_name") RequestBody name,
                         @Part("sell_price") RequestBody export_price,
                         @Part("import_price") RequestBody import_price,
                         @Part("total_quantity") RequestBody inventory,
                         @Part("unit") RequestBody unit,
                         @Part("supplier_id") RequestBody supplier_id,
                         @Part("categories_id") RequestBody categories_id,
                         @Part("pin_image") RequestBody pin_image,
                         @Part MultipartBody.Part[] images
    );

    @Multipart
    @POST("products/create")
    Call<Product> add(@Header("Authorization") String authToken,
                      @Part("product_name") RequestBody name,
                      @Part("sell_price") RequestBody export_price,
                      @Part("import_price") RequestBody import_price,
                      @Part("total_quantity") RequestBody inventory,
                      @Part("unit") RequestBody unit,
                      @Part("supplier_id") RequestBody supplier_id,
                      @Part("categories_id") RequestBody categories_id,
                      @Part MultipartBody.Part[] images
    );

    @Multipart
    @POST("products/create")
    Call<Product> update(@Header("Authorization") String authToken,
                         @Part("product_name") RequestBody name,
                         @Part("sell_price") RequestBody export_price,
                         @Part("import_price") RequestBody import_price,
                         @Part("total_quantity") RequestBody inventory,
                         @Part("unit") RequestBody unit,
                         @Part("supplier_id") RequestBody supplier_id,
                         @Part("categories_id") RequestBody categories_id,
                         @Part("pin_image") RequestBody pin_image,
                         @Part MultipartBody.Part[] images
    );

    @GET("products/delete/{id}")
    Call<Product> delete(@Header("Authorization") String authToken, @Path("id") int id);

}
