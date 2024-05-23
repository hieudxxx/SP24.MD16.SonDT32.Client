package fpoly.md16.depotlife.Statistic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiStatistic;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentStatisticBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticFragment extends Fragment {
    private FragmentStatisticBinding binding;

    private StatisticModel.StatsResponse statsResponseImport;
    private StatisticModel.StatsResponse statsResponseExport;

    private String token;

    private int maxTotalImport;
    private int maxTotalExport;

    private int totalMoneyImport = 0;
    private int totalMoneyExport = 0;
    private List<String> xValues = new ArrayList<>();
    private ArrayList<BarEntry> entries1 = new ArrayList<>();
    private ArrayList<BarEntry> entries2 = new ArrayList<>();
    private ArrayList<BarEntry> entries3 = new ArrayList<>();

    private StatisticAdapter statisticAdapter;

    private List<InventoryModel.Product> productList;

    private boolean moneyShow = false;

    private InventoryModel.InventoryResponse inventoryResponse;

    private MaterialAutoCompleteTextView fromDate, toDate;

    private String selectedText = "Hôm nay";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callApiToday();
        callApiInventory();
        productList = new ArrayList<>();
        statisticAdapter = new StatisticAdapter(new StatisticAdapter.InterClickItemData() {
            @Override
            public void clickItem(InventoryModel.Product product) {
                showDialog(product);
            }
        }, view.getContext());


        binding.rcvInventory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvInventory.setAdapter(statisticAdapter);

        callApiInventory();

        binding.layoutCalendar.setOnClickListener(view1 -> {
            showBottomSheet();
        });

        binding.listProd.setOnClickListener(view2 -> {
            // hiển thị popup list sản phẩm tồn kho
        });

        BarChart barChart = binding.idBarChart;
        barChart.getAxisRight().setDrawLabels(false);

        binding.btnShowMoney.setOnClickListener(view12 -> {
            moneyShow = !moneyShow;
            if (moneyShow) {
                binding.tvTotalInvoice.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.btnShowMoney.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.close_eye));
            } else {
                binding.tvTotalInvoice.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.btnShowMoney.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.open_eye));
            }
        });

    }

    private void showDialog(InventoryModel.Product product) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_inventory);
        RecyclerView recyclerView = dialog.findViewById(R.id.rcvInvenSp);

        ExpAdapter expAdapter = new ExpAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(expAdapter);

        expAdapter.setData(product.getExpiries());

        dialog.show();
    }

    private void setValueLayout() {
        if (statsResponseImport != null && statsResponseImport.getMonthlyStats() != null && !statsResponseImport.getMonthlyStats().isEmpty()) {
            String firstKeyImport = statsResponseImport.getMonthlyStats().keySet().iterator().next();
            totalMoneyImport = statsResponseImport.getMonthlyStats().get(firstKeyImport).getTotalValue();
            binding.totalImport.setText(String.valueOf(statsResponseImport.getMonthlyStats().get(firstKeyImport).getInvoice_count()));
        } else {
            binding.totalImport.setText("0");
        }

        if (statsResponseExport != null && statsResponseExport.getMonthlyStats() != null && !statsResponseExport.getMonthlyStats().isEmpty()) {
            String firstKeyExport = statsResponseExport.getMonthlyStats().keySet().iterator().next();
            totalMoneyExport = statsResponseExport.getMonthlyStats().get(firstKeyExport).getTotalValue();
            binding.totalExport.setText(String.valueOf(statsResponseExport.getMonthlyStats().get(firstKeyExport).getInvoice_count()));
        } else {
            binding.totalExport.setText("0");
        }

        if (statsResponseImport.getMonthlyStats().isEmpty() && statsResponseExport.getMonthlyStats().isEmpty()) {
            binding.tvTotalInvoice.setText("0 đ");
        } else {
            binding.tvTotalInvoice.setText(Helper.formatVND(totalMoneyExport - totalMoneyImport));
        }
    }

    private void dataXvalues(String option, Date startDate, Date endDate) {
        setValueLayout();
        if (statsResponseImport == null || statsResponseExport == null) {
            Log.e("StatisticFragment", "statsResponseImport hoặc statsResponseExport là null");
            return;
        }

        maxTotalImport = 0;
        maxTotalExport = 0;

        List<String> list = new ArrayList<>();
        entries1.clear();
        entries2.clear();
        entries3.clear();

        switch (option) {
            case "Hôm qua":
                list = getDatesForYesterday();
                break;
            case "Hôm nay":
                list = getDatesForToday();
                break;
            case "Tháng này":
                list = getDatesForCurrentMonth();
                break;
            case "Tháng trước":
                list = getDatesForLastMonth();
                break;
            case "Từ ngày đến ngày":
                list = getDatesBetween(startDate, endDate);
                break;
            default:
                Log.e("StatisticFragment", "Tùy chọn không hợp lệ");
                return;
        }

        // Tạo tập hợp các ngày có trong import và export
        Set<String> validDates = new HashSet<>();
        for (StatisticModel.DailyStat stat : statsResponseImport.getDailyStats().getData()) {
            validDates.add(stat.getTime());
        }
        for (StatisticModel.DailyStat stat : statsResponseExport.getDailyStats().getData()) {
            validDates.add(stat.getTime());
        }

        // Lọc xValues để chỉ giữ lại các ngày có trong validDates
        xValues.clear();
        for (String date : list) {
            if (validDates.contains(date)) {
                xValues.add(date);
            }
        }

        Map<String, Integer> dateIndexMap = new HashMap<>();
        for (int i = 0; i < xValues.size(); i++) {
            dateIndexMap.put(xValues.get(i), i);
        }

        // Initialize entries with zero values
        for (int i = 0; i < xValues.size(); i++) {
            entries1.add(new BarEntry(i, 0));
            entries2.add(new BarEntry(i, 0));
        }

        for (StatisticModel.DailyStat stat : statsResponseImport.getDailyStats().getData()) {
            String date = stat.getTime();
            if (dateIndexMap.containsKey(date)) {
                int index = dateIndexMap.get(date);
                entries1.set(index, new BarEntry(index, stat.getTotalValue()));
                if (stat.getTotalValue() > maxTotalImport) {
                    maxTotalImport = (int) Math.ceil(stat.getTotalValue());
                }
            }
        }

        for (StatisticModel.DailyStat stat : statsResponseExport.getDailyStats().getData()) {
            String date = stat.getTime();
            if (dateIndexMap.containsKey(date)) {
                int index = dateIndexMap.get(date);
                entries2.set(index, new BarEntry(index, stat.getTotalValue()));
                if (stat.getTotalValue() > maxTotalExport) {
                    maxTotalExport = (int) Math.ceil(stat.getTotalValue());
                }
            }
        }

        // Làm tròn maxTotalImport và maxTotalExport lên đến hàng triệu
        maxTotalImport = (int) (Math.ceil(maxTotalImport / 1000000.0) * 1000000);
        maxTotalExport = (int) (Math.ceil(maxTotalExport / 1000000.0) * 1000000);
    }

    private List<String> getDatesForYesterday() {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dates.add(sdf.format(cal.getTime()));
        return dates;
    }

    private List<String> getDatesForToday() {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dates.add(sdf.format(cal.getTime()));
        return dates;
    }

    private List<String> getDatesForCurrentMonth() {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar today = Calendar.getInstance();

        while (!cal.after(today)) {
            dates.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private List<String> getDatesForLastMonth() {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        int lastMonth = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == lastMonth) {
            dates.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private List<String> getDatesBetween(Date startDate, Date endDate) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        while (!cal.getTime().after(endDate)) {
            dates.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    public Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bottomsheetstatistic, null);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radioGroupBtn);
        LinearLayout customDateLayout = bottomSheetDialog.findViewById(R.id.customDateLayout);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = bottomSheetDialog.findViewById(checkedId);
            selectedText = selectedRadioButton.getText().toString();
            if (checkedId == R.id.radio_custom) {
                customDateLayout.setVisibility(View.VISIBLE);
            } else {
                customDateLayout.setVisibility(View.GONE);
            }
            fromDate.setText(null);
            toDate.setText(null);

            switch (selectedText) {
                case "Hôm qua":
                    callApiYesterday();
                    binding.tvDate.setText("Hôm qua");
                    break;
                case "Hôm nay":
                    callApiToday();
                    binding.tvDate.setText("Hôm nay");
                    break;
                case "Tháng này":
                    callApiThisMonth();
                    binding.tvDate.setText("Tháng này");
                    break;
                case "Tháng trước":
                    callApiLastMonth();
                    binding.tvDate.setText("Tháng trước");
                    break;
                case "Tùy chọn":
                    break;
            }

        });

        fromDate = bottomSheetDialog.findViewById(R.id.tvFromDate);
        toDate = bottomSheetDialog.findViewById(R.id.tvToDate);

        fromDate.setOnClickListener(view12 -> {
            showDatePickerDialog(view12.getContext(), fromDate);
        });

        toDate.setOnClickListener(view13 -> {
            showDatePickerDialog(view13.getContext(), toDate);
        });

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (!fromDate.getText().toString().isEmpty() && !toDate.getText().toString().isEmpty()) {
                    callApiToDateFromDate();
                    binding.tvDate.setText(fromDate.getText().toString() + " - " + toDate.getText().toString());
                }
            }
        });
    }

    public void showDatePickerDialog(Context context, final MaterialAutoCompleteTextView dateTextView) {
        // Lấy ngày hiện tại làm ngày mặc định trong bộ chọn
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo và hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        dateTextView.setText(formattedDate);
                    }
                }, year, month, day);

        // Đặt ngày tối đa là ngày hiện tại
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        datePickerDialog.show();
    }

    private void callApiThisMonth() {
        ApiStatistic.apiStatistic.getImportThisMonth(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseImport = response.body();
                    if (statsResponseExport != null) {
                        dataXvalues("Tháng này", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        ApiStatistic.apiStatistic.getExportThisMonth(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseExport = response.body();
                    if (statsResponseImport != null) {
                        dataXvalues("Tháng này", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiLastMonth() {
        ApiStatistic.apiStatistic.getImportLastMonth(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseImport = response.body();
                    if (statsResponseExport != null) {
                        dataXvalues("Tháng trước", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        ApiStatistic.apiStatistic.getExportLastMonth(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseExport = response.body();
                    if (statsResponseImport != null) {
                        dataXvalues("Tháng trước", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiToday() {
        ApiStatistic.apiStatistic.getImportToday(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseImport = response.body();
                    if (statsResponseExport != null) {
                        dataXvalues("Hôm nay", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        ApiStatistic.apiStatistic.getExportToday(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseExport = response.body();
                    if (statsResponseImport != null) {
                        dataXvalues("Hôm nay", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiYesterday() {
        ApiStatistic.apiStatistic.getImportYesterday(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseImport = response.body();
                    if (statsResponseExport != null) {
                        dataXvalues("Hôm qua", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        ApiStatistic.apiStatistic.getExportYesterday(token).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseExport = response.body();
                    if (statsResponseImport != null) {
                        dataXvalues("Hôm qua", null, null); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiToDateFromDate() {
        ApiStatistic.apiStatistic.getImportFromDateToDate(token, fromDate.getText().toString(), toDate.getText().toString()).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseImport = response.body();
                    if (statsResponseExport != null) {
                        dataXvalues("Từ ngày đến ngày", stringToDate(fromDate.getText().toString()), stringToDate(toDate.getText().toString())); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

        ApiStatistic.apiStatistic.getExportFromDateToDate(token, fromDate.getText().toString(), toDate.getText().toString()).enqueue(new Callback<StatisticModel.StatsResponse>() {
            @Override
            public void onResponse(Call<StatisticModel.StatsResponse> call, Response<StatisticModel.StatsResponse> response) {
                Log.d("zzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    statsResponseExport = response.body();
                    if (statsResponseImport != null) {
                        dataXvalues("Từ ngày đến ngày", stringToDate(fromDate.getText().toString()), stringToDate(toDate.getText().toString())); // Thay đổi tùy chọn và ngày bắt đầu/kết thúc nếu cần
                        updateChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatisticModel.StatsResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApiInventory() {
        ApiStatistic.apiStatistic.getInventory("Bearer " + token).enqueue(new Callback<InventoryModel.InventoryResponse>() {
            @Override
            public void onResponse(Call<InventoryModel.InventoryResponse> call, Response<InventoryModel.InventoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inventoryResponse = response.body();
                    productList.clear();
                    productList.addAll(inventoryResponse.getProducts());
                    binding.rcvInventory.setAdapter(statisticAdapter);
                    statisticAdapter.setData(productList);
                    binding.tvInventory.setText("Tổng hàng tồn kho: "+inventoryResponse.getTotalInventory());
                } else {
                    showNoDataMessage();
                }
            }

            @Override
            public void onFailure(Call<InventoryModel.InventoryResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNoDataMessage() {
        // Hiển thị thông báo không có dữ liệu
        Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
    }

    private void updateChart() {
        if ((entries1.isEmpty() || entries1.stream().allMatch(entry -> entry.getY() == 0)) &&
                (entries2.isEmpty() || entries2.stream().allMatch(entry -> entry.getY() == 0))) {
            // Hiển thị thông báo nếu không có dữ liệu
            binding.idBarChart.setVisibility(View.GONE);
            binding.noDataTextView.setVisibility(View.VISIBLE);
            binding.noDataTextView.setText("Không có hóa đơn nhập xuất");
            return;
        } else {
            // Hiển thị biểu đồ nếu có dữ liệu
            binding.idBarChart.setVisibility(View.VISIBLE);
            binding.noDataTextView.setVisibility(View.GONE);
        }

        BarChart barChart = binding.idBarChart;
        BarDataSet dataSet1 = new BarDataSet(entries1, "Tổng Tiền Nhập");
        dataSet1.setColor(ColorTemplate.COLORFUL_COLORS[0]);

        BarDataSet dataSet2 = new BarDataSet(entries2, "Tổng Tiền Xuất");
        dataSet2.setColor(ColorTemplate.COLORFUL_COLORS[1]);

        BarData barData = new BarData(dataSet1, dataSet2);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);

        // Cấu hình khoảng cách và chiều rộng của các thanh
        float groupSpace = 0.1f; // Giảm khoảng cách giữa các nhóm để nhãn nằm gần hơn
        float barSpace = 0.02f; // Giảm khoảng cách giữa các thanh trong cùng một nhóm
        float barWidth = 0.4f; // Điều chỉnh chiều rộng của thanh để phù hợp với khoảng cách nhóm
        barData.setBarWidth(barWidth);
        barChart.groupBars(0f, groupSpace, barSpace);

        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xValues.size());

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(Math.max(maxTotalImport, maxTotalExport));
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
    }

    @Override
    public void onResume() {
        super.onResume();
        callApiToday();
    }
}