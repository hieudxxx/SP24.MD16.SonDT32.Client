package fpoly.md16.depotlife.Product.Fragment;

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
import androidx.lifecycle.ViewModelProvider;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentProductEditBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditFragment extends Fragment {
    private FragmentProductEditBinding binding;
    private String token;
    private Product product;
    private Category category;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);

        bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
//            category = (Category) bundle.getSerializable("category");

            if (product != null) {
                binding.edtName.setText(product.getProduct_name());
                binding.edtId.setText(product.getId() + "");
                binding.tvCategory.setText(product.getCategory_name());
                binding.tvSupplier.setText(product.getSupplier_name());
                binding.edtExportPrice.setText(Helper.formatVND(product.getExport_price()));
                binding.edtImportPrice.setText(Helper.formatVND(product.getImport_price()));
                binding.edtInventory.setText(product.getInventory() + "");
                binding.edtUnit.setText(product.getUnit());

                if (product.getImg() == null) product.setImg("null");

                Helper.getImagesProduct(product, token, binding.imgProduct);

                binding.tvCategory.setOnClickListener(view13 -> {
                    bundle = new Bundle();
                    bundle.putInt("cat_id", product.getCategory_id());
                    Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), bundle, R.id.frag_container_product);
                });


                binding.tvSave.setOnClickListener(view12 -> {
                    String name = binding.edtName.getText().toString().trim();
                    String export_price = binding.edtExportPrice.getText().toString().trim();
                    String import_price = binding.edtImportPrice.getText().toString().trim();
                    String inventory = binding.edtInventory.getText().toString().trim();
                    String unit = binding.edtUnit.getText().toString().trim();
                    if (name.isEmpty() || export_price.isEmpty() || import_price.isEmpty() || inventory.isEmpty() || unit.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isNumberValid(export_price, binding.tvWarExportPrice);
                        Helper.isNumberValid(import_price, binding.tvWarImportPrice);
                        Helper.isNumberValid(inventory, binding.tvWarInventory);
                        Helper.isContainSpace(unit, binding.tvWarUnit);

                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarExportPrice.getText().toString().isEmpty() &&
                                binding.tvWarImportPrice.getText().toString().isEmpty() &&
                                binding.tvWarInventory.getText().toString().isEmpty() &&
                                binding.tvWarUnit.getText().toString().isEmpty()
                        ) {
                            if (category != null) {
//                                product = new Product(category.getId(), );
                            }

                            Product product1 = new Product(2, 2, name, unit, Double.parseDouble(import_price), Double.parseDouble(export_price), Integer.parseInt(inventory));
                            Log.d("tag_kiemTra", "onViewCreated: " + product1.toString());
                            ApiProduct.apiProduct.update(token, product.getId(), product1).enqueue(new Callback<Product>() {
                                @Override
                                public void onResponse(Call<Product> call, Response<Product> response) {
                                    Log.d("tag_kiemTra", "onResponse: " + response.code());
                                    Log.d("tag_kiemTra", "onResponse: " + response);
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Product> call, Throwable throwable) {
                                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }


                });

            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ShareViewModel<Category> viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
        viewModel.get().observe(getViewLifecycleOwner(), category1 -> {
            binding.tvCategory.setText(category1.getName());
            category = category1;
        });
    }
}