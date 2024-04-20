package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.ArrayList;

import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSupplier {
    ApiSupplier apiSupplier = getRetrofit().create(ApiSupplier.class);

    @GET("suppliers")
    Call<ArrayList<Supplier>> getSupplierList();
    @GET("suppliers")
    Call<SupplierResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("Supplier/{id}")
    Call<Supplier> getSupplier(@Path("id") String id);

//    @DELETE("Supplier/{id}")
//    Call<Supplier> delete(@Path("id") String id);
    @GET("suppliers/delete/{id}")
    Call<SupplierResponse> delete(@Header("Authorization") String authToken, @Path("id") int id);

    @PUT("Supplier/edit/{id}")
    Call<Supplier> editById(@Path("id") String id, @Body Supplier data);

    @PUT("Supplier/edit/status{id}")
    Call<Supplier> editByStatus(@Path("id") String id, @Body boolean status);

    @POST("Supplier")
    Call<Supplier> addSupplier(@Body Supplier data);
}
