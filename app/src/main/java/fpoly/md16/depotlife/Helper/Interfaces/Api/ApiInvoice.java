package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Invoice.Model.Invoice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiInvoice {

    ApiInvoice apiInvoice = getRetrofit().create(ApiInvoice.class);

    @GET("invoice/add")
    Call<Invoice> add(@Header("Authorization") String authToken, @Body Invoice invoice);

    @GET("Invoice/{id}")
    Call<Invoice> getOne(@Path("id") String id);
}
