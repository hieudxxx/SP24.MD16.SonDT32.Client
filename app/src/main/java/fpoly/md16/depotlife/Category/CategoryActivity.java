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

import java.util.ArrayList;

import fpoly.md16.depotlife.Category.Adapter.CategoryAdapter;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityCategoryBinding;
import fpoly.md16.depotlife.databinding.DialogAddCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private ArrayList<Category> list;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    }



//    public List<Category> getCategoryList1() {
//        List<Category> list = new ArrayList<>();
//        list.add(new Category("id1", "Mỹ phẩm", true));
//        list.add(new Category("id2", "Gia dụng", true));
//        list.add(new Category("id3", "Thức ăn", true));
//        return list;
//    }

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
            Helper.onSort(this, list, adapter,null, Category.sortByNameAZ);

//            showSortDialogCategory();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        ApiCategory.apiCategory.getCategoryList().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
//                Log.d("tag_kiemTra", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    list = response.body();
//                    Log.d("tag_kiemTra", "onResponse: " + response);
                }
                if (list.isEmpty()) {
                    binding.rcvCategory.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.rcvCategory.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setVisibility(View.GONE);
                }
                adapter = new CategoryAdapter(CategoryActivity.this, list);
                binding.rcvCategory.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(CategoryActivity.this, "thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // Lưu trạng thái của menu item search và sort vào biến instance
//        searchMenuItem = binding.tbCategory.getMenu().findItem(R.id.searchCategory);
//        sortMenuItem = binding.tbCategory.getMenu().findItem(R.id.sortCategory);
//        return super.onPrepareOptionsMenu(menu);
//    }

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

//        addBinding.btnAddCateogry.setOnClickListener(v -> {
//            String name = addBinding.edtAddCategory.getText().toString().trim();
//            if (!name.isEmpty()) {
//                Category newCategory = new Category(UUID.randomUUID().toString(), name, true);
//                categoryList.add(newCategory);
//                categoryAdapter.notifyDataSetChanged();
//                addBinding.edtAddCategory.setText("");
//                Toast.makeText(CategoryActivity.this, "Đã thêm loại hàng hoá mới!", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//                // Cập nhật trạng thái của danh sách sau khi thêm mục mới
//                checkEmptyList();
//            } else {
//                Toast.makeText(CategoryActivity.this, "Vui lòng nhập tên loại hàng hoá!", Toast.LENGTH_SHORT).show();
//            }
//        });

        addBinding.imgClose.setOnClickListener(v -> {
            dialog.cancel();
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Đóng ô tìm kiếm nếu nó đang mở khi Activity mất focus
//        if (searchMenuItem != null && searchMenuItem.isActionViewExpanded()) {
//            searchMenuItem.collapseActionView();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        // Đóng ô tìm kiếm khi người dùng bấm nút Back
//        if (searchMenuItem != null && searchMenuItem.isActionViewExpanded()) {
//            searchMenuItem.collapseActionView();
//        } else {
//            super.onBackPressed();
//        }
//    }
}