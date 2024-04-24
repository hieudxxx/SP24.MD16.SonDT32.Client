package fpoly.md16.depotlife.Supplier.Fragment;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Product.Adapter.ProductAdapter;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierAdapter;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.BotSheetFilterSupplierBinding;
import fpoly.md16.depotlife.databinding.FragmentSupplierBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierFragment extends Fragment {
    private FragmentSupplierBinding binding;
    private ArrayList<Supplier> list;
    private SupplierAdapter adapter;
    private SupplierResponse supplierResponse;
    private int pageIndex = 1;
    private int count = 0;
    private int perPage = 0;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.btnBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        binding.fab.setOnClickListener(view12 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierAddFragment(), null, R.id.frag_container_supplier);
        });

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        binding.rcv.setLayoutManager(manager);
        adapter = new SupplierAdapter(getContext(), getParentFragmentManager());
        getData();

        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    count++;
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    Log.d("onScrollChange", "onScrollChange: " + pageIndex);
                    getData();
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        list.clear();
        ApiSupplier.apiSupplier.getData("Bearer " + token, pageIndex).enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                Log.d("onResponse_supplier", "response_code: " + response.code());
                if (response.isSuccessful()) {
                    supplierResponse = response.body();
                    if (supplierResponse != null) {
                        binding.tvTotalInvoice.setText(supplierResponse.getTotal()+"");
                        perPage = supplierResponse.getLast_page();
                        onCheckList(supplierResponse);
                    }
//
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
            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onCheckList(SupplierResponse supplierResponse) {
        if (supplierResponse.getData() != null) {
            List<Supplier> tempList = Arrays.asList(supplierResponse.getData()); // hoặc có thể dùng foreach để check từng item
            list.addAll(tempList);
            binding.rcv.setVisibility(View.VISIBLE);
            binding.layoutTotal.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            setHasOptionsMenu(true);
            adapter.setData(list);
            binding.rcv.setAdapter(adapter);
            adapter.setData(list);
            pageIndex++;
        } else {
            setHasOptionsMenu(false);
            binding.rcv.setVisibility(View.GONE);
            binding.layoutTotal.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_search) {
            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.item_sort) {
            Helper.onSort(getContext(), list, adapter, Supplier.sortByAsc, Supplier.sortByNameAZ);
            return true;
        } else if (id == R.id.item_filter) {
            onFilter();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onFilter() {
        BotSheetFilterSupplierBinding filterBinding = BotSheetFilterSupplierBinding.inflate(LayoutInflater.from(getActivity()));
        filterBinding.rdGr.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rd_filter_active) {
                adapter.setData(Supplier.filterByStatus(list, true));
            } else if (i == R.id.rd_filter_inactive) {
                adapter.setData(Supplier.filterByStatus(list, true));
            } else if (i == R.id.rd_filter_all) {
                adapter.setData(list);
            }
            binding.rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        Helper.onSettingsBotSheet(getContext(), filterBinding);
    }
}