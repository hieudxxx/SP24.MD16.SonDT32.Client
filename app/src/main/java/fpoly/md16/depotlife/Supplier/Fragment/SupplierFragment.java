package fpoly.md16.depotlife.Supplier.Fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierAdapter;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.BotSheetFilterSupplierBinding;
import fpoly.md16.depotlife.databinding.DialogSelectBinding;
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

    private Runnable runnable;
    private final Handler handler = new Handler();

    public static boolean isLoadData = false;

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

        binding.btnBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        binding.fab.setOnClickListener(view12 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierAddFragment(), null, R.id.frag_container_supplier);
        });

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        binding.rcv.setLayoutManager(manager);
        adapter = new SupplierAdapter(getContext(), getParentFragmentManager());
        getData();

        onSearch();

        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    getData();
                    binding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        ApiSupplier.apiSupplier.getData(token, pageIndex).enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if (response.isSuccessful()) {
                    supplierResponse = response.body();
                    if (supplierResponse != null) {
                        binding.tvTotalInvoice.setText(supplierResponse.getTotal()+"");
                        perPage = supplierResponse.getLast_page();
                        onCheckList(supplierResponse);
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
            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onCheckList(SupplierResponse supplierResponse) {
        if (supplierResponse.getData() != null) {
            list.addAll(Arrays.asList(supplierResponse.getData()));
            binding.rcv.setVisibility(View.VISIBLE);
            binding.layoutTotal.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            setHasOptionsMenu(true);
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

 private void onSearch() {
     SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
     MenuItem searchItem = binding.tbSupplier.getMenu().findItem(R.id.item_search); // Đảm bảo rằng ID này khớp với ID trong file menu của bạn
     SearchView searchView = (SearchView) searchItem.getActionView();
     if (searchView != null) {
         searchView.setSearchableInfo(searchManager.getSearchableInfo(((Activity) getContext()).getComponentName()));
         searchView.setMaxWidth(Integer.MAX_VALUE);
     }

     if (searchView != null) {
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 if (!newText.isEmpty()) {
                     if (runnable != null) handler.removeCallbacks(runnable);
                     runnable = () -> getDataSearchSup(newText);
                     handler.postDelayed(runnable, 500);
                     return true;
                 } else {
                     list.clear();
                     getData();
                 }
                 return false;
             }
         });
     }
 }

    private void getDataSearchSup(String keyword) {
        ApiSupplier.apiSupplier.getDataSearch("Bearer " + token, keyword).enqueue(new Callback<List<Supplier>>() {
            @Override
            public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onCheckListSearchSup(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Supplier>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
            }
        });
    }

    public void onCheckListSearchSup(List<Supplier> supplier) {
        if (supplier != null) {
            list.clear();
            list.addAll(supplier);
            binding.rcv.setVisibility(View.VISIBLE);
            binding.layoutTotal.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            setHasOptionsMenu(true);
            binding.rcv.setAdapter(adapter);
            adapter.setData(list);
        } else {
            list.clear();
            getData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isLoadData) {
            pageIndex = 1;
            list.clear();
            adapter.notifyDataSetChanged();
            getData();
            isLoadData = false;
        }
    }
}