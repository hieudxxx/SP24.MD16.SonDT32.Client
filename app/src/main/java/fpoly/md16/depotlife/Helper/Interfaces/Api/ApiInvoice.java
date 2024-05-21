package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Invoice.Model.InvoiceResponse;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInvoice {

    ApiInvoice apiInvoice = getRetrofit().create(ApiInvoice.class);

    @GET("invoice")
    Call<InvoiceResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("invoice/add")
    Call<Invoice> add(@Header("Authorization") String authToken, @Body Invoice invoice);

    @GET("invoice/update/{id}")
    Call<Invoice> update(@Header("Authorization") String authToken,
                         @Part("note") RequestBody note,
                         @Part("term") RequestBody term,
                         @Part("signature_name") RequestBody signature_name,
                         @Part("due_date") RequestBody due_date,
                         @Part("pay_status") RequestBody pay_status,
                         @Part MultipartBody.Part signature);

    @GET("invoice/get-invoice/{id}")
    Call<Invoice> getDetail(@Header("Authorization") String authToken, @Path("id") int id);
}
