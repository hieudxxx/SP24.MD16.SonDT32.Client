package fpoly.md16.depotlife.Invoice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Invoice.Model.InvoiceResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragment extends Fragment {
    private FragmentInvoiceBinding binding;
    private InvoiceAdapter adapter;
    private ArrayList<Invoice> list;

    public String token;

    private InvoiceResponse invoiceResponse;

    private int pageIndex = 1;

    private int perPage = 0;
    private Boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        binding.fab.setOnClickListener(this::showPopupMenu);

        binding.layoutCalendar.setOnClickListener(view12 -> {
            Helper.onShowCaledar(binding.tvDate, getContext(), "%d-%02d-%02d");
        });

        adapter = new InvoiceAdapter(invoice -> {

        });

        list = new ArrayList<>();
        getData();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_invoice, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.invoice_import){
                getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type_invoice",0));
            }else if (item.getItemId() == R.id.invoice_export) {
                getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type_invoice",1));
            }
            return false;
        });

        popupMenu.show();
    }

    private void getData() {
        ApiInvoice.apiInvoice.getData(token,pageIndex).enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {

                if (response.isSuccessful()) {
                    invoiceResponse = response.body();
                    if (invoiceResponse != null) {
                        binding.tvTotalInvoice.setText("("+invoiceResponse.getTotal()+")");
                        perPage = invoiceResponse.getLast_page();
                        onCheckList(invoiceResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
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
}