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

import com.squareup.picasso.Picasso;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentProductDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private Product product;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbProduct);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {
                binding.tvIdProduct.setText(product.getId() + "");
                binding.tvBarCode.setText(product.getBarcode());
                binding.tvName.setText(product.getProduct_name());
                binding.tvSupplier.setText(product.getSupplier_name());
                binding.tvCategory.setText(product.getCategory_name());
                binding.tvExportPrice.setText(Helper.formatVND(product.getExport_price()));
                binding.tvImportPrice.setText(Helper.formatVND(product.getImport_price()));
                binding.tvInventory.setText(product.getInventory() + "");

                if (!product.getImg().isEmpty()) {
                    Picasso.get().load(product.getImg()).into(binding.imgProduct);
                } else {
                    binding.imgProduct.setImageResource(R.drawable.img_add);
                }

                binding.layoutDelete.setOnClickListener(view12 -> {
                    Helper.onCheckdeleteDialog(getContext(), () -> {
                        ApiProduct.apiProduct.delete("Bearer " + token, product.getId()).enqueue(new Callback<ProductResponse>() {
                            @Override
                            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                                if (response.isSuccessful() || response.code() == 200) {
                                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    requireActivity().finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    });
                });

                binding.imgEdit.setOnClickListener(view13 -> {
                    Helper.loadFragment(getActivity().getSupportFragmentManager(), new ProductEditFragment(), null, R.id.frag_container_product);
                });
            }
        }


    }
}