package fpoly.md16.depotlife.Product.Fragment;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Activity.ProductActivity;
import fpoly.md16.depotlife.Product.Adapter.ProductAdapter;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.Product.ProductFilterActivity;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentProductBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private FragmentProductBinding binding;
    private ProductAdapter adapter;
    private ArrayList<Product> list = new ArrayList<>();
    private ProductResponse productResponse;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    public static boolean isLoadData = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true); // Bật hiển thị menu

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ProductActivity.class));
        });

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        adapter = new ProductAdapter(getContext(), list, token);
        getData();
//        onResume();

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
            Helper.onSort(getContext(), list, adapter, Product.sortByAsc, Product.sortByNameAZ);
            return true;
        } else if (id == R.id.item_filter) {
            Intent intent = new Intent(getActivity(), ProductFilterActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        ApiProduct.apiProduct.getData(token, pageIndex).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    productResponse = response.body();
                    if (productResponse != null) {
                        binding.tvTotalProduct.setText(productResponse.getTotal() + "");
                        perPage = productResponse.getLast_page();
                        onCheckList(productResponse);
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
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCheckList(ProductResponse productResponse) {
        if (productResponse.getData() != null) {
            list.addAll(Arrays.asList(productResponse.getData()));
            if (!list.isEmpty()) {
                binding.rcvProduct.setVisibility(View.VISIBLE);
                binding.layoutTotal.setVisibility(View.VISIBLE);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                setHasOptionsMenu(true);
                pageIndex++;
                binding.rcvProduct.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                setHasOptionsMenu(false);
                binding.rcvProduct.setVisibility(View.GONE);
                binding.layoutTotal.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
            }

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
//        perPage = 1;
//        binding.rcvProduct.setVisibility(View.VISIBLE);
    }

}