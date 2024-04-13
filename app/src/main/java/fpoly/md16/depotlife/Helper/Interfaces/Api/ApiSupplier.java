package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.API.URL1;

import java.util.ArrayList;

import fpoly.md16.depotlife.Supplier.Model.Supplier;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSupplier {
    ApiSupplier apiSupplier = new Retrofit.Builder()
            .baseUrl(URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiSupplier.class);

    @GET("Supplier")
    Call<ArrayList<Supplier>> getSupplierList();

    @GET("Supplier/{id}")
    Call<Supplier> getSupplier(@Path("id") String id);

    @DELETE("Supplier/{id}")
    Call<Supplier> delete(@Path("id") String id);

    @PUT("Supplier/edit/{id}")
    Call<Supplier> editById(@Path("id") String id, @Body Supplier data);

    @PUT("Supplier/edit/status{id}")
    Call<Supplier> editByStatus(@Path("id") String id, @Body boolean status);

    @POST("Supplier")
    Call<Supplier> addSupplier(@Body Supplier data);
}
