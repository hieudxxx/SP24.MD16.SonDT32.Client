package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentDetailTabBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailTabFragment extends Fragment {
    private FragmentDetailTabBinding binding;
    private Supplier supplier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supplier = new Supplier();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = bundle.getString("id");
//            supplier = (Supplier) bundle.getSerializable("supplier");
            if (id != null) {
                ApiSupplier.apiSupplier.getSupplier(id).enqueue(new Callback<Supplier>() {
                    @Override
                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                        if (response.isSuccessful()) {
                            supplier = response.body();

                            binding.tvId.setText(supplier.getId());
                            binding.tvName.setText(supplier.getName());
                            binding.tvAddress.setText(supplier.getAddress());
                            binding.tvEmail.setText(supplier.getEmail());
                            binding.tvPhone.setText(supplier.getPhone());
                            binding.tvTaxCode.setText(supplier.getTax_code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Supplier> call, Throwable throwable) {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }

    }
}