package fpoly.md16.depotlife.Helper.Interfaces.Api;

import static fpoly.md16.depotlife.Helper.Interfaces.Api.RetrofitRequest.getRetrofit;

import fpoly.md16.depotlife.Statistic.InventoryModel;
import fpoly.md16.depotlife.Statistic.StatisticModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiStatistic {
    ApiStatistic apiStatistic = getRetrofit().create(ApiStatistic.class);

    @GET("statistical/import-invoice")
    Call<StatisticModel.StatsResponse> getImportFromDateToDate(
            @Header("Authorization") String authToken,
            @Query("start_date") String start_date, @Query("end_date") String end_date);
    @GET("statistical/import-invoice/this-month")
    Call<StatisticModel.StatsResponse> getImportThisMonth(@Header("Authorization") String authToken);

    @GET("statistical/import-invoice/last-month")
    Call<StatisticModel.StatsResponse> getImportLastMonth(@Header("Authorization") String authToken);

    @GET("statistical/import-invoice/today")
    Call<StatisticModel.StatsResponse> getImportToday(@Header("Authorization") String authToken);

    @GET("statistical/import-invoice/yesterday")
    Call<StatisticModel.StatsResponse> getImportYesterday(@Header("Authorization") String authToken);

    //export
    @GET("statistical/export-invoice")
    Call<StatisticModel.StatsResponse> getExportFromDateToDate(
            @Header("Authorization") String authToken,
            @Query("start_date") String start_date, @Query("end_date") String end_date);
    @GET("statistical/export-invoice/this-month")
    Call<StatisticModel.StatsResponse> getExportThisMonth(@Header("Authorization") String authToken);

    @GET("statistical/export-invoice/last-month")
    Call<StatisticModel.StatsResponse> getExportLastMonth(@Header("Authorization") String authToken);

    @GET("statistical/export-invoice/today")
    Call<StatisticModel.StatsResponse> getExportToday(@Header("Authorization") String authToken);

    @GET("statistical/export-invoice/yesterday")
    Call<StatisticModel.StatsResponse> getExportYesterday(@Header("Authorization") String authToken);

    //tồn kho
    @GET("statistical/inventory/end-of-day")
    Call<InventoryModel.InventoryResponse> getInventory(@Header("Authorization") String authToken);

}
