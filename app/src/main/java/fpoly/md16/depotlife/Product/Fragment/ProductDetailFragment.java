package fpoly.md16.depotlife.Product.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Adapter.ProductImagesAdapter;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierDetailFragment;
import fpoly.md16.depotlife.databinding.FragmentProductDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private Product product;
    private List<Product> products;
    private String token;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
            ProductFragment.isLoadData = true;
        });

        bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {
                getData();

                binding.layoutDelete.setOnClickListener(view12 -> {
                    delete();
                });

                binding.tvSupplier.setOnClickListener(view14 -> {
                    bundle = new Bundle();
                    bundle.putSerializable("supplier", product.getSupplier());
                    Helper.loadFragment(getParentFragmentManager(), new SupplierDetailFragment(), bundle, R.id.frag_container_product);
                });

                binding.imgEdit.setOnClickListener(view13 -> {
                    bundle = new Bundle();
                    bundle.putSerializable("product", product);
                    Helper.loadFragment(getActivity().getSupportFragmentManager(), new ProductEditFragment(), bundle, R.id.frag_container_product);
                });
            }
        }
    }

    private void delete() {
        bundle = new Bundle();
        bundle.putSerializable("product", product);
        Helper.loadFragment(getParentFragmentManager(), new ProductDeleteFragment(), bundle, R.id.frag_container_product);
//        Helper.onCheckdeleteDialog(getContext(), () -> {
//            ApiProduct.apiProduct.delete(token, product.getId()).enqueue(new Callback<Product>() {
//                @Override
//                public void onResponse(Call<Product> call, Response<Product> response) {
//                    if (response.isSuccessful() || response.code() == 200) {
//                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
//                        ProductFragment.isLoadData = true;
//                        requireActivity().finish();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Product> call, Throwable throwable) {
//                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
//                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        });
    }

    private void getData() {
        ApiProduct.apiProduct.getProductById(token, product.getId()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    if (products != null) {
                        binding.tvIdProduct.setText(products.get(0).getId() + "");
                        binding.tvBarCode.setText(products.get(0).getId() + "");
                        binding.tvName.setText(products.get(0).getProduct_name());
                        binding.tvSupplier.setText(products.get(0).getSupplier().getName());
                        binding.tvCategory.setText(products.get(0).getCategory().getName());
                        binding.tvExportPrice.setText(Helper.formatVND(products.get(0).getExport_price()));
                        binding.tvImportPrice.setText(Helper.formatVND(products.get(0).getImport_price()));
                        binding.tvInventory.setText(products.get(0).getInventory() + "");
                        binding.tvUnit.setText(products.get(0).getUnit());
                        if (products.get(0).getLocation() != null) binding.tvLocation.setText(products.get(0).getLocation().getCode());
                        else binding.tvLocation.setText("Chưa có vị trí");

                        ProductImagesAdapter imagesAdapter = new ProductImagesAdapter(getContext(), Arrays.asList(products.get(0).getImg()), null, token, false);
                        binding.rcvImages.setAdapter(imagesAdapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                        binding.rcvImages.setLayoutManager(layoutManager);

                        Helper.setImgProduct(products.get(0).getImg(), binding.imgProduct);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}