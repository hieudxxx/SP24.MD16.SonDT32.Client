package fpoly.md16.depotlife.Category;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
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

    private Runnable runnable;
    private final Handler handler = new Handler();

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





    private void getData() {
        ApiCategory.apiCategory.getData(token, pageIndex).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.d("onResponse_supplier", "response_code: " + response.code());
                if (response.isSuccessful()) {
                    categoryResponse = response.body();
                    if (categoryResponse != null) {
                        binding.tvTotalInvoice.setText(categoryResponse.getTotal()+"");
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


    private void onCheckSearch(List<Category> categories) {
        if (categories != null) {
            list.clear();
            list.addAll(categories);
            Log.d("TAG", "onCheckSearch: "+categories.toString());
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.tvTotalInvoice.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            binding.rcvCategory.setAdapter(adapter);
            adapter.setData(list);
        } else {
            binding.rcvCategory.setVisibility(View.INVISIBLE);
            binding.tvTotalInvoice.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invoice_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.item_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
                        debounceSearch(newText, 500);
                        return true;
                    }
                    return false;
                }
            });
        }
        return true;
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
                searchData(newText);
            }
        };

        // Lên lịch thực hiện tìm kiếm sau một khoảng thời gian delay
        handler.postDelayed(runnable, delayMillis);
    }

    private void searchData(String keyword) {
        ApiCategory.apiCategory.getDataSearch(token, keyword).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    if (categories != null) {
                        binding.tvTotalInvoice.setText("1");
                        onCheckSearch(categories);
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
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(CategoryActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
            }
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