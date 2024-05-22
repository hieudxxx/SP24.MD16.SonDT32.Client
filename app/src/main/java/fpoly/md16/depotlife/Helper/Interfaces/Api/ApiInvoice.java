package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Invoice.Model.InvoiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInvoice {
    ApiInvoice apiInvoice = getRetrofit().create(ApiInvoice.class);

    @GET("invoice")
    Call<InvoiceResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @GET("invoice/add")
    Call<Invoice> add(@Header("Authorization") String authToken, @Body Invoice invoice);

    @GET("Invoice/{id}")
    Call<Invoice> getOne(@Path("id") String id);
}
