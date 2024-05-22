package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.Customers.Model.CustomerResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCustomers {
    ApiCustomers API_CUSTOMERS = getRetrofit().create(ApiCustomers.class);

    @GET("customers")
    Call<CustomerResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);
    @Multipart
    @POST("customers/create")
    Call<Customer> create(@Header("Authorization") String authToken,
                          @Part("customer_name") RequestBody customerName,
                          @Part("customer_phone") RequestBody customerPhone,
                          @Part("customer_email") RequestBody customerEmail,
                          @Part("address") RequestBody address,
                          @Part MultipartBody.Part images
    );
    @Multipart
    @POST("customers/update/{id}")
    Call<Customer> update(@Header("Authorization") String authToken,
                          @Path("id") int id,
                          @Part("customer_name") RequestBody customerName,
                          @Part("customer_phone") RequestBody customerPhone,
                          @Part("customer_email") RequestBody customerEmail,
                          @Part("address") RequestBody address,
                          @Part MultipartBody.Part images
    );

//    @POST("customers/update/{id}")
//    Call<Customer> update(@Header("Authorization") String authToken, @Path("id") int id, @Body Customer data);

    @GET("customers/delete/{id}")
    Call<Customer> delete(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("customers/search")
    Call<List<Customer>> getDataSearch(@Header("Authorization") String authToken, @Query("keyword") String keyword);

}
