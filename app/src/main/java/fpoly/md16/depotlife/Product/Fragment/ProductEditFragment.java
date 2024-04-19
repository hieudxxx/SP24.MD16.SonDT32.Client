package fpoly.md16.depotlife.Product.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentProductEditBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductEditFragment extends Fragment {
    private FragmentProductEditBinding binding;
    private String token;
    private Product product;

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

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            product = (Product) bundle.getSerializable("product");
            if (product != null) {

                binding.edtId.setText(product.getId() + "");
                binding.tvCategory.setText(product.getCategory_name());
                binding.tvSupplier.setText(product.getSupplier_name());
                binding.edtExportPrice.setText(Helper.formatVND(product.getExport_price()));
                binding.edtImportPrice.setText(Helper.formatVND(product.getImport_price()));
                binding.edtInventory.setText(product.getInventory()+"");
                binding.edtWeight.setText(product.getUnit());

                if (!product.getImg().isEmpty()) {
                    Picasso.get().load(product.getImg()).into(binding.imgProduct);
                } else {
                    binding.imgProduct.setImageResource(R.drawable.img_add);
                }

                binding.tvSave.setOnClickListener(view12 -> {
                    ApiProduct.apiProduct.update("Bearer " + token, product.getId(), product).enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {

                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable throwable) {

                        }
                    });
                    requireActivity().getSupportFragmentManager().popBackStack();
                });

            }
        }
    }
}