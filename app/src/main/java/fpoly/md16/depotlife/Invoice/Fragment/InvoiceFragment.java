package fpoly.md16.depotlife.Invoice.Fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Invoice.Model.InvoiceResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.DialogLayoutBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;
import fpoly.md16.depotlife.databinding.LayoutMenuBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragment extends Fragment {
    private FragmentInvoiceBinding binding;
    private InvoiceAdapter adapter;
    private Runnable runnable;

    private final Handler handler = new Handler();
    private ArrayList<Invoice> list;

    public String token;

    private InvoiceResponse invoiceResponse;

    private int pageIndex = 1;

    private String invoiceType = null;

    private String payMentStatus = null;

    private int perPage = 0;
    private final Boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbInvoice);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        binding.fab.setOnClickListener(this::showPopupMenu);

        binding.layoutCalendar.setOnClickListener(view12 -> {
            Helper.onShowCaledar(binding.tvDate, getContext());
        });

        adapter = new InvoiceAdapter(new InvoiceAdapter.InterClickItemData() {
            @Override
            public void clickItem(Invoice invoice) {
                getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("invoiceId", invoice.getId()));
            }
        });

        list = new ArrayList<>();

        binding.filter.setOnClickListener(view1 -> {
            if (binding.layoutSpn.getVisibility() == View.GONE) {
                binding.layoutSpn.setVisibility(View.VISIBLE);
            } else {
                binding.layoutSpn.setVisibility(View.GONE);
            }
        });

        String[] items_status_payment = new String[]{"Tất cả", "Chưa thanh toán", "Đã thanh toán", "Quá hạn", "Đã xóa"};
        ArrayAdapter<String> adapter_status = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items_status_payment);
        binding.spnPaymentStatus.setAdapter(adapter_status);

        String[] items_invoice_type = new String[]{"Tất cả", "Hóa đơn nhập", "Hóa đơn xuất"};
        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items_invoice_type);
        binding.spnInvoiceType.setAdapter(adapter_type);

        binding.spnInvoiceType.setOnItemClickListener((adapterView, view13, i, l) -> {
            if (i != 0){
                invoiceType = String.valueOf(i - 1);
            }else {
                invoiceType = null;
            }
            filterData();
        });

        binding.spnPaymentStatus.setOnItemClickListener((adapterView, view14, i, l) -> {
            if (i != 0){
                payMentStatus = String.valueOf(i - 1);
            }else {
                payMentStatus = null;
            }
            filterData();
        });
    }

    @Override
    public void onDetach() {
        Log.d("TAG", "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
    }



    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_invoice, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.invoice_import) {
                    getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type_invoice", 0));
                } else if (item.getItemId() == R.id.invoice_export) {
                    getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type_invoice", 1));
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void onCheckList(InvoiceResponse invoiceResponse) {
        if (invoiceResponse.getData() != null) {
            list.addAll(Arrays.asList(invoiceResponse.getData()));
            if (!list.isEmpty()) {
                binding.rcvInvoice.setVisibility(View.VISIBLE);
                binding.tvTotalInvoice.setVisibility(View.VISIBLE);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                pageIndex++;
                binding.rcvInvoice.setAdapter(adapter);
                adapter.setData(list);
            } else {
                binding.rcvInvoice.setVisibility(View.INVISIBLE);
                binding.tvTotalInvoice.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
            }

        }
    }

    private void onCheckSearch(Invoice invoice) {
        if (invoice != null) {
            list.clear();
            list.add(invoice);
            binding.rcvInvoice.setVisibility(View.VISIBLE);
            binding.tvTotalInvoice.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            binding.rcvInvoice.setAdapter(adapter);
            adapter.setData(list);
        } else {
            binding.rcvInvoice.setVisibility(View.INVISIBLE);
            binding.tvTotalInvoice.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.invoice_menu, menu);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) getContext()).getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty()) {
                        list.clear();
                        getData();
                    } else {
                        debounceSearch(newText, 300);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void filterData() {
        list.clear();
        Log.d("tag_kiemTra", "invoiceType: " + invoiceType);
        Log.d("tag_kiemTra", "payMentStatus: " + payMentStatus);
        ApiInvoice.apiInvoice.filter(token,invoiceType,payMentStatus).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                Log.d("tag_kiemTra", "onFailure: " + response.code());
                if (response.isSuccessful()) {
                    invoiceResponse = response.body();
                    if (invoiceResponse != null) {
                        binding.tvTotalInvoice.setText("(" + invoiceResponse.getTotal() + ")");
                        perPage = invoiceResponse.getLast_page();
                        onCheckList(invoiceResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBodyData: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        ApiInvoice.apiInvoice.getData(token, pageIndex).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                Log.d("tag_kiemTra", "onFailure: " + response.code());
                if (response.isSuccessful()) {
                    invoiceResponse = response.body();
                    if (invoiceResponse != null) {
                        binding.tvTotalInvoice.setText("(" + invoiceResponse.getTotal() + ")");
                        perPage = invoiceResponse.getLast_page();
                        onCheckList(invoiceResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBodyData: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void debounceSearch(final String newText, long delayMillis) {
        // Hủy bỏ Runnable trước đó
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }

        // Tạo Runnable mới để thực hiện tìm kiếm
        runnable = new Runnable() {
            @Override
            public void run() {
                searchData(Integer.parseInt(newText));
            }
        };

        // Lên lịch thực hiện tìm kiếm sau một khoảng thời gian delay
        handler.postDelayed(runnable, delayMillis);
    }

    private void searchData(int id) {
        ApiInvoice.apiInvoice.search(token, id).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    Invoice invoice = response.body();
                    if (invoice != null) {
                        binding.tvTotalInvoice.setText("1");
                        onCheckSearch(invoice);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        getData();
    }
}