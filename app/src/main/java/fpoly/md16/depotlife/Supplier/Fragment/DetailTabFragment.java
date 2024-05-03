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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Product.Fragment.ProductEditFragment;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.Model.ProductResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierDetailAdapter;
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

    private Bundle bundle;

    private int id_sup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);

        bundle = getArguments();
        if (bundle != null) {
            id_sup =  bundle.getInt("id");
            if (id_sup > 0) {
                getData();

                binding.layoutDelete.setOnClickListener(view12 -> {
                    delete();
                });


            }
        }
    }


    private void delete() {
        Helper.onCheckdeleteDialog(getContext(), () -> {
            ApiSupplier.apiSupplier.delete("Bearer " + token, supplier.getId()).enqueue(new Callback<Supplier>() {
                @Override
                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                    if (response.isSuccessful() || response.code() == 200) {
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        SupplierFragment.isLoadData = true;
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                }

                @Override
                public void onFailure(Call<Supplier> call, Throwable throwable) {
                    Log.d("onFailure", "onFailure: " + throwable.getMessage());
                    Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }


    private void getData() {
        ApiSupplier.apiSupplier.getSupplier("Bearer " + token, id_sup).enqueue(new Callback<Supplier>() {
            @Override
            public void onResponse(Call<Supplier> call, Response<Supplier> response) {

                if (response.isSuccessful()) {
                    supplier = response.body();
                    binding.tvId.setText(supplier.getId()+"");
                    binding.tvName.setText(supplier.getName());
                    binding.tvAddress.setText(supplier.getAddress());
                    binding.tvPhone.setText(supplier.getPhone());
                    binding.tvTaxCode.setText(supplier.getTax_code());

                    binding.imgEdit.setOnClickListener(view13 -> {
                        bundle = new Bundle();
                        bundle.putSerializable("supplier", supplier);
                        Helper.loadFragment(getActivity().getSupportFragmentManager(), new SupplierEditFragment(), bundle, R.id.frag_container_supplier);
                    });
                }
            }

            @Override
            public void onFailure(Call<Supplier> call, Throwable throwable) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

}