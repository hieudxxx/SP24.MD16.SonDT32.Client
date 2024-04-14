package fpoly.md16.depotlife.Invoice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BotSheetFilterInvoiceBinding;
import fpoly.md16.depotlife.databinding.BotSheetSortInvoiceBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceFragment extends Fragment {
    private FragmentInvoiceBinding binding;
    private InvoiceAdapter adapter;
    private ArrayList<Invoice> list;
    private Boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbInvoice);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(view1 -> {
            onShowFab();
        });

        binding.layoutCalendar.setOnClickListener(view12 -> {
            Helper.onShowCaledar(binding.tvDate, getContext());
        });

        list = new ArrayList<>();
        getData();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    private void getData() {
        ApiInvoice.apiInvoice.getInvoiceList().enqueue(new Callback<ArrayList<Invoice>>() {
            @Override
            public void onResponse(Call<ArrayList<Invoice>> call, Response<ArrayList<Invoice>> response) {
//                Log.d("tag_kiemTra", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    list = response.body();
//                    Log.d("tag_kiemTra", "onResponse: " + response);
                }
                if (list != null && !list.isEmpty()) {
                    binding.rcvInvoice.setVisibility(View.VISIBLE);
                    binding.layoutTotal.setVisibility(View.VISIBLE);
                    binding.layoutCalendar.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setVisibility(View.GONE);
                    setHasOptionsMenu(true);
                } else {
                    binding.rcvInvoice.setVisibility(View.GONE);
                    binding.layoutTotal.setVisibility(View.GONE);
                    binding.layoutCalendar.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    setHasOptionsMenu(false);
                }
                adapter = new InvoiceAdapter(getContext(), list, getParentFragmentManager());
                binding.rcvInvoice.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Invoice>> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onShowFab() {
        binding.fab.shrink();
        if (!isExpanded) {
            binding.overlay.setVisibility(View.VISIBLE);
            binding.fabAddExport.show();
            binding.fabAddImport.show();
            binding.tvAddImport.setVisibility(View.VISIBLE);
            binding.tvAddExport.setVisibility(View.VISIBLE);
            binding.fab.extend();
            isExpanded = true;
        } else {
            binding.overlay.setVisibility(View.GONE);
            binding.fabAddExport.hide();
            binding.fabAddImport.hide();
            binding.tvAddImport.setVisibility(View.GONE);
            binding.tvAddExport.setVisibility(View.GONE);
            binding.fab.shrink();
            isExpanded = false;
        }

        Bundle bundle = new Bundle();
        binding.fabAddExport.setOnClickListener(view5 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type", 1));
        });

        binding.fabAddImport.setOnClickListener(view5 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type", 0));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_search) {
            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.item_sort) {
            onSort();
            return true;
        } else if (id == R.id.item_filter) {
            onFilter();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onSort() {
        BotSheetSortInvoiceBinding sortBinding = BotSheetSortInvoiceBinding.inflate(LayoutInflater.from(getActivity()));
        sortBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == R.id.rd_sort_asc) {
                Collections.sort(list, Invoice.sortByAsc);
            } else if (i == R.id.rd_sort_decs) {
                Collections.sort(list, Collections.reverseOrder(Invoice.sortByAsc));
            } else if (i == R.id.rd_sort_new) {
                Collections.sort(list, Invoice.sortByNewestDate);
            } else if (i == R.id.rd_sort_old) {
                Collections.sort(list, Collections.reverseOrder(Invoice.sortByNewestDate));
            }
            adapter.notifyDataSetChanged();
        }));
        Helper.onSettingsBotSheet(getContext(), sortBinding);

    }

    private void onFilter() {
        BotSheetFilterInvoiceBinding filterBinding = BotSheetFilterInvoiceBinding.inflate(LayoutInflater.from(getActivity()));
        filterBinding.rdGr.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rd_filter_import) {
                adapter = new InvoiceAdapter(getContext(), Invoice.filterByType(list, 0), getParentFragmentManager());
            } else if (i == R.id.rd_filter_export) {
                adapter = new InvoiceAdapter(getContext(), Invoice.filterByType(list, 1), getParentFragmentManager());
            } else if (i == R.id.rd_filter_done) {
                adapter = new InvoiceAdapter(getContext(), Invoice.filterByStatus(list, 1), getParentFragmentManager());
            } else if (i == R.id.rd_filter_not_done) {
                adapter = new InvoiceAdapter(getContext(), Invoice.filterByStatus(list, 0), getParentFragmentManager());
            }
            binding.rcvInvoice.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        Helper.onSettingsBotSheet(getContext(), filterBinding);
    }
}