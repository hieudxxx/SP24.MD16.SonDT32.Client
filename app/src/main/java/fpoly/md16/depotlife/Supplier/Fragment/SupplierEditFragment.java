package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentSupplierEditBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupplierEditFragment extends Fragment {
    private FragmentSupplierEditBinding binding;
    private Supplier supplier;

    private String token;

    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbAccount);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);


        bundle = getArguments();
        if (bundle != null) {
            supplier = (Supplier) bundle.getSerializable("supplier");

            if (supplier != null) {

                binding.edtId.setText(supplier.getId()+"");
                binding.edtName.setText(supplier.getName());
                binding.edtPhone.setText(supplier.getPhone());
//                binding.edtEmail.setText(supplier.getEmail());
                binding.edtAddress.setText(supplier.getAddress());
                binding.edtTaxCode.setText(supplier.getTax_code());


                binding.tvSave.setOnClickListener(view12 -> {

                    String name = binding.edtName.getText().toString().trim();
                    String phone = binding.edtPhone.getText().toString().trim();
//                    String email = binding.edtEmail.getText().toString().trim();
                    String address = binding.edtAddress.getText().toString().trim();
                    String taxCode = binding.edtTaxCode.getText().toString().trim();
                    int id = supplier.getId();
                    double total = supplier.getTotal();
                    int status = supplier.getStatus();

                    if (name.isEmpty() || phone.isEmpty() ||  address.isEmpty() || taxCode.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isPhoneValid(phone, binding.tvWarPhone);
//                        Helper.isEmailValid(email, binding.tvWarEmail);
                        Helper.isContainSpace(address, binding.tvWarAddress);
                        Helper.isContainSpace(taxCode, binding.tvWarTaxCode);
//
                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarPhone.getText().toString().isEmpty() &&
                                binding.tvWarEmail.getText().toString().isEmpty() &&
                                binding.tvWarAddress.getText().toString().isEmpty() &&
                                binding.tvWarTaxCode.getText().toString().isEmpty()
                        ){
                            Supplier supplier1 = new Supplier(id,name,taxCode,address,total,status);
                            Log.d("tag_kiemTra", "onViewCreated: " + supplier1.toString());
                            ApiSupplier.apiSupplier.update(token, supplier1.getId(), supplier1).enqueue(new Callback<Supplier>() {
                                @Override
                                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                                    Log.d("tag_kiemTra", "onResponse: " + response.code());
                                    Log.d("tag_kiemTra", "onResponse: " + response);
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Supplier> call, Throwable throwable) {
                                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

//                    String name = binding.edtName.getText().toString().trim();
//                    String export_price = binding.edtExportPrice.getText().toString().trim();
//                    String import_price = binding.edtImportPrice.getText().toString().trim();
//                    String inventory = binding.edtInventory.getText().toString().trim();
//                    String unit = binding.edtUnit.getText().toString().trim();
//                    if (name.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || inventory.isEmpty() || unit.isEmpty()) {
//                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Helper.isContainSpace(name, binding.tvWarName);
//                        Helper.isNumberValid(export_price, binding.tvWarExportPrice);
//                        Helper.isNumberValid(import_price, binding.tvWarImportPrice);
//                        Helper.isNumberValid(inventory, binding.tvWarInventory);
//                        Helper.isContainSpace(unit, binding.tvWarUnit);
//
//                        if (binding.tvWarName.getText().toString().isEmpty() &&
//                                binding.tvWarExportPrice.getText().toString().isEmpty() &&
//                                binding.tvWarImportPrice.getText().toString().isEmpty() &&
//                                binding.tvWarInventory.getText().toString().isEmpty() &&
//                                binding.tvWarUnit.getText().toString().isEmpty()
//                        ) {
//
//                            Product product1 = new Product(2, 2, name, unit, Double.parseDouble(import_price), Double.parseDouble(export_price), Integer.parseInt(inventory));
//                            Log.d("tag_kiemTra", "onViewCreated: " + product1.toString());
//                            ApiProduct.apiProduct.update(token, product.getId(), product1).enqueue(new Callback<Product>() {
//                                @Override
//                                public void onResponse(Call<Product> call, Response<Product> response) {
//                                    Log.d("tag_kiemTra", "onResponse: " + response.code());
//                                    Log.d("tag_kiemTra", "onResponse: " + response);
//                                    if (response.isSuccessful()) {
//                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
//                                        requireActivity().getSupportFragmentManager().popBackStack();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Product> call, Throwable throwable) {
//                                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
//                                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else {
//                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }


                });

            }
        }
    }
//        if (bundle != null) {
//            id_supplier = bundle.getString("id");
//            if (id_supplier != null) {
//                ApiSupplier.apiSupplier.getSupplier(id_supplier).enqueue(new Callback<Supplier>() {
//                    @Override
//                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
//                        if (response.isSuccessful()) {
//                            supplier = response.body();
//
//                            binding.edtId.setText(supplier.getId());
//                            binding.edtName.setText(supplier.getName());
//                            binding.edtPhone.setText(supplier.getPhone());
//                            binding.edtEmail.setText(supplier.getEmail());
//                            binding.edtAddress.setText(supplier.getAddress());
//                            binding.edtTaxCode.setText(supplier.getTax_code());
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Supplier> call, Throwable throwable) {
//                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                binding.tvSave.setOnClickListener(view12 -> {
//                    String name = binding.edtName.getText().toString().trim();
//                    String phone = binding.edtPhone.getText().toString().trim();
//                    String email = binding.edtEmail.getText().toString().trim();
//                    String address = binding.edtAddress.getText().toString().trim();
//                    String taxCode = binding.edtTaxCode.getText().toString().trim();
//
//                    String tvName = binding.tvWarName.getText().toString();
//                    String tvPhone = binding.tvWarPhone.getText().toString();
//                    String tvEmail = binding.tvWarEmail.getText().toString();
//                    String tvAddress = binding.tvWarAddress.getText().toString();
//                    String tvTaxCode = binding.tvWarTaxCode.getText().toString();
//
//
//                    if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || taxCode.isEmpty()) {
//                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Helper.isContainSpace(name, binding.tvWarName);
//
//                        Helper.isPhoneValid(phone, binding.tvWarPhone);
//                        Helper.isEmailValid(email, binding.tvWarEmail);
//
//
//                        Helper.isContainSpace(address, binding.tvWarAddress);
//                        Helper.isContainSpace(taxCode, binding.tvWarTaxCode);
////
//                        if (tvName.isEmpty() && tvPhone.isEmpty() && tvEmail.isEmpty() && tvAddress.isEmpty() && tvTaxCode.isEmpty()) {
//                            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
//                            requireActivity().getSupportFragmentManager().popBackStack();
//                        }
//                    }
//                });
//            }
//        }
}