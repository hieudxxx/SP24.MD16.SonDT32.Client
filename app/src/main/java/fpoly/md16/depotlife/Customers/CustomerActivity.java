package fpoly.md16.depotlife.Customers;

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

import fpoly.md16.depotlife.Customers.Adapter.CustomerAdapter;
import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.Customers.Model.CustomerResponse;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCustomers;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityCategoryBinding;
import fpoly.md16.depotlife.databinding.ActivityCustomerBinding;
import fpoly.md16.depotlife.databinding.DialogAddCategoryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private ActivityCustomerBinding binding;
    private ArrayList<Customer> list;
    private CustomerAdapter adapter;
    private CustomerResponse customerResponse;

    private int pageIndex = 1;
    private int count = 0;
    private int perPage = 0;
    private String token;

    public static boolean isLoadData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
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
//            onAddCategory();
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
            Helper.onSort(this, list, adapter, null, Customer.sortByNameAZ);

//            showSortDialogCategory();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        ApiCustomers.API_CUSTOMERS.getData(token, pageIndex).enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                Log.d("onResponse_supplier", "response_code: " + response.code());
                if (response.isSuccessful()) {
                    customerResponse = response.body();
                    if (customerResponse != null) {
                        perPage = customerResponse.getLast_page();
                        onCheckList(customerResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CustomerActivity.this, "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(CustomerActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onCheckList(CustomerResponse customerResponse) {
        if (customerResponse.getData() != null) {
            List<Customer> tempList = Arrays.asList(customerResponse.getData()); // hoặc có thể dùng foreach để check từng item
            list.addAll(tempList);
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            adapter = new CustomerAdapter(this, list, token);
            binding.rcvCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageIndex++;
        } else {
            binding.rcvCategory.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }



//    private void onAddCategory() {
//        DialogAddCategoryBinding addBinding = DialogAddCategoryBinding.inflate(LayoutInflater.from(this));
//        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
//        builder.setView(addBinding.getRoot());
//        AlertDialog dialog = builder.create();
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        addBinding.btnAddCateogry.setOnClickListener(v -> {
//            String name = addBinding.edtAddCategory.getText().toString().trim();
//            if (name.isEmpty()){
//                Toast.makeText(CustomerActivity.this, "Vui lòng nhập tên loại hàng hoá!", Toast.LENGTH_SHORT).show();
//            }else {
//                Helper.isContainSpace(name, addBinding.tvWarName);
//
//                if (addBinding.tvWarName.getText().toString().isEmpty()){
//                    Customer cate = new Customer();
//                    cate.setName(name);
//                    cate.setStatus(1);
//                    ApiCategory.apiCategory.create(token, cate).enqueue(new Callback<Customer>() {
//                        @Override
//                        public void onResponse(Call<Customer> call, Response<Customer> response) {
//                            if (response.isSuccessful()) {
//                                Toast.makeText(CustomerActivity.this, "Thêm loại hàng hoá thành công!", Toast.LENGTH_SHORT).show();
//                                addBinding.edtAddCategory.setText("");
//                                dialog.dismiss();
//                                isLoadData = true;
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Customer> call, Throwable throwable) {
//                            Log.d("onFailure", "onFailure: " + throwable.getMessage());
//                            Toast.makeText(CustomerActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }else {
//                    Toast.makeText(CustomerActivity.this, "Thêm loại hàng hoá thất bại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        addBinding.imgClose.setOnClickListener(v -> {
//            dialog.cancel();
//        });
//    }

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