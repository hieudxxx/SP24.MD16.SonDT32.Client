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
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import fpoly.md16.depotlife.Category.CategoryActivity;
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
    private ArrayList<Product> list;
    private ProductResponse productResponse;

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

        list = new ArrayList<>();
        getData();

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
        String token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        ApiProduct.apiProduct.getData("Bearer " + token).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                if (response.isSuccessful()) {

                    productResponse = response.body();
                    Log.d("onResponse", "productResponse: " + productResponse.toString());
                } else {
                    Log.e("onResponse", "responseCode: " + response.code());
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Xử lý lỗi cụ thể
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();

            }
        });
//        ApiProduct.apiProduct.getProductList().enqueue(new Callback<ArrayList<Product>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
//
//                if (response.isSuccessful()) {
//                    list = response.body();
////                    Log.d("tag_kiemTra", "onResponse: " + response);
//                }
//                if (list.isEmpty()) {
//                    binding.rcvProduct.setVisibility(View.GONE);
//                    binding.tvEmpty.setVisibility(View.VISIBLE);
//                    binding.layoutTotal.setVisibility(View.GONE);
//                } else {
//                    binding.rcvProduct.setVisibility(View.VISIBLE);
//                    binding.tvEmpty.setVisibility(View.GONE);
//                    binding.layoutTotal.setVisibility(View.VISIBLE);
//                }
//                adapter = new ProductAdapter(getContext(), list);
//                binding.rcvProduct.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
//                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
//                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    //    public List<Product> getProductList() {
//        List<Product> list = new ArrayList<>();
//        list.add(new Product("1", "1", "1", "Sữa tắm", "QEQ", "Unit 1", "https://static.kfcvietnam.com.vn/images/items/lg/Burger-Zinger.jpg?v=3oOMd3", 10.0, 2000.0, 100, true));
//        list.add(new Product("2", "2", "2", "Bánh Cosy", "QFA", "Unit 2", "https://static.kfcvietnam.com.vn/images/items/lg/Burger-Zinger.jpg?v=3oOMd3", 15.0, 25000.0, 150, false));
//        return list;
//    }


}