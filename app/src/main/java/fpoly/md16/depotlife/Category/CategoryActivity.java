package fpoly.md16.depotlife.Category;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Category.Adapter.CategoryAdapter;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Product.Adapter.ProductAdapter;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.ActivityCategoryBinding;
import fpoly.md16.depotlife.databinding.DialogAddCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private ArrayList<Category> list;
    private CategoryAdapter adapter;
    private CategoryResponse categoryResponse;

    private int pageIndex = 1;
    private int count = 0;
    private int perPage = 0;
    private String token;

    public static boolean isLoadData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        token = "Bearer " +(String) Helper.getSharedPre(getApplicationContext(), "token", String.class);

        list = new ArrayList<>();
        getData();

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        setSupportActionBar(binding.tbCategory);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(v -> {
            onAddCategory();
        });

        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    Log.d("onScrollChange", "onScrollChange: " + pageIndex);
                    getData();
                    binding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.item_filter).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_search) {
            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.item_sort) {
            Helper.onSort(this, list, adapter, null, Category.sortByNameAZ);

//            showSortDialogCategory();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        ApiCategory.apiCategory.getData(token, pageIndex).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.d("onResponse_supplier", "response_code: " + response.code());
                if (response.isSuccessful()) {
                    categoryResponse = response.body();
                    if (categoryResponse != null) {
                        perPage = categoryResponse.getLast_page();
                        onCheckList(categoryResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CategoryActivity.this, "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(CategoryActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onCheckList(CategoryResponse categoryResponse) {
        if (categoryResponse.getData() != null) {
            List<Category> tempList = Arrays.asList(categoryResponse.getData()); // hoặc có thể dùng foreach để check từng item
            list.addAll(tempList);
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            adapter = new CategoryAdapter(this, list, token);
            binding.rcvCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageIndex++;
        } else {
//            setHasOptionsMenu(false);
            binding.rcvCategory.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }


//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Lưu trạng thái của menu item search và sort vào biến instance
//        searchMenuItem = binding.tbCategory.getMenu().findItem(R.id.searchCategory);
//        sortMenuItem = binding.tbCategory.getMenu().findItem(R.id.sortCategory);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    private void searchCategory(MenuItem menuItem) {
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Tìm kiếm loại hàng hoá");
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                categoryAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                categoryAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//    }

    private void onAddCategory() {
        DialogAddCategoryBinding addBinding = DialogAddCategoryBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
        builder.setView(addBinding.getRoot());
        AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        addBinding.btnAddCateogry.setOnClickListener(v -> {
            String name = addBinding.edtAddCategory.getText().toString().trim();
            if (name.isEmpty()){
                Toast.makeText(CategoryActivity.this, "Vui lòng nhập tên loại hàng hoá!", Toast.LENGTH_SHORT).show();
            }else {
                Helper.isContainSpace(name, addBinding.tvWarName);

                if (addBinding.tvWarName.getText().toString().isEmpty()){
                    Category cate = new Category();
                    cate.setName(name);
                    cate.setStatus(1);
                    ApiCategory.apiCategory.create(token, cate).enqueue(new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(CategoryActivity.this, "Thêm loại hàng hoá thành công!", Toast.LENGTH_SHORT).show();
                                addBinding.edtAddCategory.setText("");
                                dialog.dismiss();
                                isLoadData = true;
                            }
                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable throwable) {
                            Log.d("onFailure", "onFailure: " + throwable.getMessage());
                            Toast.makeText(CategoryActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(CategoryActivity.this, "Thêm loại hàng hoá thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addBinding.imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
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