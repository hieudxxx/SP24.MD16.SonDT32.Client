package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.ArrayList;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.Customers.Model.CustomerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCustomers {
    ApiCustomers API_CUSTOMERS = getRetrofit().create(ApiCustomers.class);

    @GET("customers")
    Call<CustomerResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("customers")
    Call<ArrayList<Customer>> getCustomerList();

    @POST("customers/create")
    Call<Customer> create(@Header("Authorization") String authToken, @Body Customer data);
    @POST("customers/update/{id}")
    Call<Customer> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Customer data);

    @GET("customers/delete/{id}")
    Call<Customer> delete(@Header("Authorization") String authToken, @Path("id") int id);

}
