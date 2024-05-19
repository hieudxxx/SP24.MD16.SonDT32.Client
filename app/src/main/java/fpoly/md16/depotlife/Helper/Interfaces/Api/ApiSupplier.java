package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Product.Model.Product;
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
    Call<SupplierResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("suppliers/get-id/{id}")
    Call<Supplier> getSupplier(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("suppliers/delete/{id}")
    Call<Supplier> delete(@Header("Authorization") String authToken, @Path("id") int id);

    @POST("suppliers/update/{id}")
    Call<Supplier> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Supplier data);

    @POST("suppliers/create")
    Call<Supplier> create(@Header("Authorization") String authToken, @Body Supplier data);

    @GET("suppliers/search")
    Call<List<Supplier>> getDataSearch(@Header("Authorization") String authToken, @Query("keyword") String keyword);

}
