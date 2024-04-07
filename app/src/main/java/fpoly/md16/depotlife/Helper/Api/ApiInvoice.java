package fpoly.md16.depotlife.Helper.Api;

import static fpoly.md16.depotlife.Helper.Api.API.URL;

import java.util.ArrayList;

import fpoly.md16.depotlife.Invoice.Model.Invoice;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInvoice {
    ApiInvoice apiInvoice = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInvoice.class);

    @GET("Invoice")
    Call<ArrayList<Invoice>> getInvoiceList();

    @GET("Invoice/{id}")
    Call<Invoice> getOne(@Path("id") String id);
}
