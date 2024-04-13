package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentSupplierEditBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupplierEditFragment extends Fragment {
    private FragmentSupplierEditBinding binding;
    private String id_supplier;
    private Supplier supplier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierEditBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbAccount);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        supplier = new Supplier();
        Bundle bundle = getArguments();

        if (bundle != null) {
            id_supplier = bundle.getString("id");
            if (id_supplier != null) {
                ApiSupplier.apiSupplier.getSupplier(id_supplier).enqueue(new Callback<Supplier>() {
                    @Override
                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                        if (response.isSuccessful()) {
                            supplier = response.body();

                            binding.edtId.setText(supplier.getId());
                            binding.edtName.setText(supplier.getName());
                            binding.edtPhone.setText(supplier.getPhone());
                            binding.edtEmail.setText(supplier.getEmail());
                            binding.edtAddress.setText(supplier.getAddress());
                            binding.edtTaxCode.setText(supplier.getTax_code());

                        }
                    }

                    @Override
                    public void onFailure(Call<Supplier> call, Throwable throwable) {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

                binding.tvSave.setOnClickListener(view12 -> {
                    String name = binding.edtName.getText().toString().trim();
                    String phone = binding.edtPhone.getText().toString().trim();
                    String email = binding.edtEmail.getText().toString().trim();
                    String address = binding.edtAddress.getText().toString().trim();
                    String taxCode = binding.edtTaxCode.getText().toString().trim();

                    String tvName = binding.tvWarName.getText().toString();
                    String tvPhone = binding.tvWarPhone.getText().toString();
                    String tvEmail = binding.tvWarEmail.getText().toString();
                    String tvAddress = binding.tvWarAddress.getText().toString();
                    String tvTaxCode = binding.tvWarTaxCode.getText().toString();


                    if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || taxCode.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);

                        Helper.isPhoneValid(phone, binding.tvWarPhone);
                        Helper.isEmailValid(email, binding.tvWarEmail);


                        Helper.isContainSpace(address, binding.tvWarAddress);
                        Helper.isContainSpace(taxCode, binding.tvWarTaxCode);
//
                        if (tvName.isEmpty() && tvPhone.isEmpty() && tvEmail.isEmpty() && tvAddress.isEmpty() && tvTaxCode.isEmpty()) {
                            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        }
                    }
                });
            }
        }

    }
}