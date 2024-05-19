package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import com.google.gson.JsonObject;

import java.util.List;

import fpoly.md16.depotlife.Product.Model.BatchResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
    Call<List<Product>> getProductById(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("products/search")
    Call<List<Product>> productSearch(@Header("Authorization") String authToken, @Query("keyword") String keyword);

    @GET("products/filter-supplier")
    Call<List<Product>> productFilterBySupplier(@Header("Authorization") String authToken, @Query("supplier_id") int supplier_id);

    @GET("products/getBatch/{id}")
    Call<List<BatchResponse>> getBatch(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("products/delete-image/{id}")
    Call<Product> deleteImage(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("products/search")
    Call<List<Product>> searchByName(@Header("Authorization") String authToken, @Query("keyword") String keyword);

    @Multipart
    @POST("products/update/{id}")
    Call<List<String>> update(@Header("Authorization") String authToken,
                              @Path("id") int id,
                              @Part("product_name") RequestBody name,
                              @Part("sell_price") RequestBody export_price,
                              @Part("import_price") RequestBody import_price,
                              @Part("unit") RequestBody unit,
                              @Part("supplier_id") RequestBody supplier_id,
                              @Part("categories_id") RequestBody categories_id,
                              @Part("zone") RequestBody zone,
                              @Part("shelf") RequestBody shelf,
                              @Part("level") RequestBody level,
                              @Part("pin_image") RequestBody pin_image,
                              @Part MultipartBody.Part[] images
    );

    @Multipart
    @POST("products/create")
    Call<Product> add(@Header("Authorization") String authToken,
                      @Part("product_name") RequestBody name,
                      @Part("sell_price") RequestBody export_price,
                      @Part("import_price") RequestBody import_price,
                      @Part("unit") RequestBody unit,
                      @Part("supplier_id") RequestBody supplier_id,
                      @Part("categories_id") RequestBody categories_id,
                      @Part("zone") RequestBody zone,
                      @Part("shelf") RequestBody shelf,
                      @Part("level") RequestBody level,
                      @Part MultipartBody.Part[] images
    );

    @DELETE("products/deleteBatch/{productId}/{expiryId}")
    Call<JsonObject> delete(@Header("Authorization") String authToken, @Path("productId") int id, @Path("expiryId") int expiryId, @Query("quantity") int quantity);
}
