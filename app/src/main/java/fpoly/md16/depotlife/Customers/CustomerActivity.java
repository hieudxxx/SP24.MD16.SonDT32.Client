package fpoly.md16.depotlife.Customers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Customers.Adapter.CustomerAdapter;
import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.Customers.Model.CustomerResponse;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCustomers;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityCustomerBinding;
import fpoly.md16.depotlife.databinding.DialogAddCustomerBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity implements onItemRcvClick {
    private ActivityCustomerBinding binding;
    private DialogAddCustomerBinding addBinding;
    private ArrayList<Customer> list;
    private CustomerAdapter adapter;
    private CustomerResponse customerResponse;
    private Uri uri;
    private MultipartBody.Part multipartBody;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;

    public static boolean isLoadData = false;


    public ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    uri = result.getData().getData();
                    addBinding.imgProduct.setImageURI(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        token = "Bearer " + Helper.getSharedPre(getApplicationContext(), "token", String.class);

        list = new ArrayList<>();
        getData();

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        setSupportActionBar(binding.tbCategory);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(v -> {
            onAddCustomer(null);
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        menu.findItem(R.id.item_filter).setVisible(false);
        return true;
    }

    private void getData() {
        ApiCustomers.API_CUSTOMERS.getData(token, pageIndex).enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful()) {
                    customerResponse = response.body();
                    if (customerResponse != null) {
                        perPage = customerResponse.getLast_page();
                        onCheckList(customerResponse);
                    }
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
            List<Customer> tempList = Arrays.asList(customerResponse.getData());
            list.addAll(tempList);
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            adapter = new CustomerAdapter(this, list, token, this);
            binding.rcvCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageIndex++;
        } else {
            binding.rcvCategory.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void onAddCustomer(Customer customer) {
        addBinding = DialogAddCustomerBinding.inflate(LayoutInflater.from(this));
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
        builder.setView(addBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (customer != null) {
            addBinding.tvReg.setText("SỬA KHÁCH HÀNG");
            addBinding.btnAdd.setText("SỬA");
            addBinding.edFullName.setText(customer.getCustomerName());
            addBinding.edPhone.setText(customer.getCustomerPhone());
            addBinding.edEmail.setText(customer.getCustomerEmail());
            addBinding.edAddress.setText(customer.getAddress());
            if ( customer.getAvatar() == null){
                addBinding.imgProduct.setImageResource(R.drawable.img_add);
            } else {
                String ava = customer.getAvatar().replace("public","storage");
                Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" +ava).into(addBinding.imgProduct);
            }




            addBinding.imgProduct.setOnClickListener(view -> onRequestPermission());

            addBinding.btnAdd.setOnClickListener(v -> {
                String name = addBinding.edFullName.getText().toString().trim();
                String email = addBinding.edEmail.getText().toString().trim();
                String phone = addBinding.edPhone.getText().toString().trim();
                String address = addBinding.edAddress.getText().toString().trim();


                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Helper.isContainSpace(name, addBinding.tvWarName);
                    Helper.isEmailValid(email, addBinding.tvWarEmail);
                    Helper.isPhoneValid(phone, addBinding.tvWarPhone);
//                    Helper.isContainSpace(address, addBinding.tvWarAddress);

                    if (addBinding.tvWarName.getText().toString().isEmpty() &&
                            addBinding.tvWarEmail.getText().toString().isEmpty() &&
                            addBinding.tvWarPhone.getText().toString().isEmpty() &&
                            addBinding.tvWarAddress.getText().toString().isEmpty()
                    ) {

                        if (uri != null) {
                            multipartBody = Helper.getRealPathFile(this, uri, "avatar");
                        }

                        Customer customer1 = new Customer(name, phone, email, address);

                        ApiCustomers.API_CUSTOMERS.update(token,customer.getId(),
                                Helper.createStringPart(customer1.getCustomerName()),
                                Helper.createStringPart(customer1.getCustomerPhone()),
                                Helper.createStringPart(customer1.getCustomerEmail()),
                                Helper.createStringPart(customer1.getAddress()),
                                multipartBody).enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(CustomerActivity.this, "Sửa khách hàng thành công!", Toast.LENGTH_SHORT).show();
                                    addBinding.edFullName.setText("");
                                    addBinding.edPhone.setText("");
                                    addBinding.edEmail.setText("");
                                    addBinding.edAddress.setText("");
                                    dialog.dismiss();
                                    pageIndex = 1;
                                    list.clear();
                                    adapter.notifyDataSetChanged();
                                    getData();
                                }
                            }

                            @Override
                            public void onFailure(Call<Customer> call, Throwable throwable) {
                                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                Toast.makeText(CustomerActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(CustomerActivity.this, "Sửa khách hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            addBinding.btnCancel.setOnClickListener(v -> {
                dialog.cancel();
            });

        } else {
            addBinding.tvReg.setText("THÊM KHÁCH HÀNG");

            addBinding.imgProduct.setOnClickListener(view -> onRequestPermission());

            addBinding.btnAdd.setOnClickListener(v -> {
                String name = addBinding.edFullName.getText().toString().trim();
                String email = addBinding.edEmail.getText().toString().trim();
                String phone = addBinding.edPhone.getText().toString().trim();
                String address = addBinding.edAddress.getText().toString().trim();


                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    Helper.isContainSpace(name, addBinding.tvWarName);
                    Helper.isEmailValid(email, addBinding.tvWarEmail);
                    Helper.isPhoneValid(phone, addBinding.tvWarPhone);
                    Helper.isContainSpace(address, addBinding.tvWarAddress);

                    if (addBinding.tvWarName.getText().toString().isEmpty() &&
                            addBinding.tvWarEmail.getText().toString().isEmpty() &&
                            addBinding.tvWarPhone.getText().toString().isEmpty() &&
                            addBinding.tvWarAddress.getText().toString().isEmpty()
                    ) {

                        if (uri != null) {
                            multipartBody = Helper.getRealPathFile(this, uri, "avatar");
                        }

                        Customer customer1 = new Customer(name, phone, email, address);

                        ApiCustomers.API_CUSTOMERS.create(token,
                                Helper.createStringPart(customer1.getCustomerName()),
                                Helper.createStringPart(customer1.getCustomerPhone()),
                                Helper.createStringPart(customer1.getCustomerEmail()),
                                Helper.createStringPart(customer1.getAddress()),
                                multipartBody).enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(CustomerActivity.this, "Thêm khách hàng thành công!", Toast.LENGTH_SHORT).show();
                                    addBinding.edFullName.setText("");
                                    addBinding.edPhone.setText("");
                                    addBinding.edEmail.setText("");
                                    addBinding.edAddress.setText("");
                                    dialog.dismiss();
                                    pageIndex = 1;
                                    list.clear();
                                    adapter.notifyDataSetChanged();
                                    getData();
                                }
                            }

                            @Override
                            public void onFailure(Call<Customer> call, Throwable throwable) {
                                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                Toast.makeText(CustomerActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(CustomerActivity.this, "Thêm khách hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            addBinding.btnCancel.setOnClickListener(v -> {
                dialog.cancel();
            });
        }

        dialog.show();


    }

    public void onRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Helper.openGallery(this, launcher);
            return;
        }
        if (this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Helper.openGallery(this, launcher);
        } else {
            String permission = (Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(new String[]{permission}, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Helper.openGallery(this, launcher);
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
    }



    @Override
    public void onClick(Object item) {
        if (item != null) {
            Customer customer = (Customer) item;
            onAddCustomer(customer);
        }
    }
}