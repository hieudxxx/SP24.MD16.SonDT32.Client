package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import com.google.gson.JsonObject;

import java.util.List;

import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Invoice.Model.InvoiceResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInvoice {
    ApiInvoice apiInvoice = getRetrofit().create(ApiInvoice.class);

    @GET("invoice")
    Call<InvoiceResponse> getData(@Header("Authorization") String authToken, @Query("page") int page_index);

    @Multipart
    @POST("invoice/update/{id}")
    Call<Invoice> update(@Header("Authorization") String authToken,
                         @Path("id") int id,
                         @Part("note") RequestBody note,
                         @Part("term") RequestBody term,
                         @Part("signature_name") RequestBody signature_name,
                         @Part("due_date") RequestBody due_date,
                         @Part("pay_status") RequestBody pay_status,
                         @Part MultipartBody.Part signature);

    @POST("invoice/create")
    Call<JsonObject> add(@Header("Authorization") String authToken, @Body RequestBody invoiceDetails);

    @GET("invoice/get-invoice/{id}")
    Call<Invoice> getDetail(@Header("Authorization") String authToken, @Path("id") int id);

    @GET("invoice/search")
    Call<Invoice> search(@Header("Authorization") String authToken, @Query("invoice_id") int invoice_id);

    @GET("invoice/filter")
    Call<InvoiceResponse> filter(@Header("Authorization") String authToken, @Query("invoiceType") String invoiceType, @Query("payStatus") String payStatus);

    @GET("invoice/delete/{id}")
    Call<ResponseBody> delete(@Header("Authorization") String authToken, @Path("id") int id);

    public static class InvoiceRequest {
        private int discount;
        private List<Invoice.ProductInvoice> products;
        private String note;
        private String term;
        private String signature_name;
        private int invoice_type;
        private int pay_status;
        private int user_id;
        private int supplier_id;
        private int customer_id;
        private int total_amount;
        private String due_date;
        private MultipartBody.Part multipartBody;

        public InvoiceRequest() {
        }

        public InvoiceRequest(int discount, List<Invoice.ProductInvoice> products, String note, String term, String signature_name, int invoice_type, int pay_status, int user_id, int supplier_id, int customer_id, int total_amount, String due_date, MultipartBody.Part multipartBody) {
            this.discount = discount;
            this.products = products;
            this.note = note;
            this.term = term;
            this.signature_name = signature_name;
            this.invoice_type = invoice_type;
            this.pay_status = pay_status;
            this.user_id = user_id;
            this.supplier_id = supplier_id;
            this.customer_id = customer_id;
            this.total_amount = total_amount;
            this.due_date = due_date;
            this.multipartBody = multipartBody;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public List<Invoice.ProductInvoice> getProducts() {
            return products;
        }

        public void setProducts(List<Invoice.ProductInvoice> products) {
            this.products = products;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getSignature_name() {
            return signature_name;
        }

        public void setSignature_name(String signature_name) {
            this.signature_name = signature_name;
        }

        public int getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(int invoice_type) {
            this.invoice_type = invoice_type;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public String getDue_date() {
            return due_date;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public MultipartBody.Part getMultipartBody() {
            return multipartBody;
        }

        public void setMultipartBody(MultipartBody.Part multipartBody) {
            this.multipartBody = multipartBody;
        }
    }
}
