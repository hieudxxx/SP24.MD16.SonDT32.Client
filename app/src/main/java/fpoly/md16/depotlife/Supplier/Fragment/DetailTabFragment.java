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

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Product.Fragment.ProductEditFragment;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.databinding.FragmentDetailTabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTabFragment extends Fragment {
    private FragmentDetailTabBinding binding;
    private Supplier supplier;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);


        Bundle bundle = getArguments();
        if (bundle != null) {
            supplier = (Supplier) bundle.getSerializable("supplier");
            if (supplier != null) {
                binding.tvId.setText(supplier.getId()+"");
                binding.tvName.setText(supplier.getName());
                binding.tvAddress.setText(supplier.getAddress());
                binding.tvEmail.setText(supplier.getEmail());
                binding.tvPhone.setText(supplier.getPhone());
                binding.tvTaxCode.setText(supplier.getTax_code());

                binding.layoutDelete.setOnClickListener(view12 -> {
                    Helper.onCheckdeleteDialog(getContext(), () -> {
                        ApiSupplier.apiSupplier.delete("Bearer " + token, supplier.getId()).enqueue(new Callback<SupplierResponse>() {
                            @Override
                            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                                if (response.isSuccessful() || response.code() == 200) {
                                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    requireActivity().finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                            }
                        });

                    });
                });

                binding.imgEdit.setOnClickListener(view13 -> {
                    Helper.loadFragment(getActivity().getSupportFragmentManager(), new SupplierEditFragment(), null, R.id.frag_container_supplier);
                });
            }
        }

    }
}