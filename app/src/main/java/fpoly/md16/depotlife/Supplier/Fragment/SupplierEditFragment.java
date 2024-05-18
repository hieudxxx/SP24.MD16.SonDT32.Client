package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private Supplier supplier;
    private String token;
    private Bundle bundle;
    private int status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        bundle = getArguments();
        if (bundle != null) {
            supplier = (Supplier) bundle.getSerializable("supplier");

            if (supplier != null) {

                binding.edtId.setText(supplier.getId()+"");
                binding.edtName.setText(supplier.getName());
                binding.edtPhone.setText(supplier.getPhone());
                binding.edtAddress.setText(supplier.getAddress());
                binding.edtTaxCode.setText(supplier.getTax_code());

                checkStatus();

                binding.tvSave.setOnClickListener(view12 -> {

                    String name = binding.edtName.getText().toString().trim();
                    String phone = binding.edtPhone.getText().toString().trim();
                    String address = binding.edtAddress.getText().toString().trim();
                    String taxCode = binding.edtTaxCode.getText().toString().trim();


                    if (name.isEmpty() || phone.isEmpty() ||  address.isEmpty() || taxCode.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isPhoneValid(phone, binding.tvWarPhone);
                        Helper.isContainSpace(address, binding.tvWarAddress);
                        Helper.isContainSpace(taxCode, binding.tvWarTaxCode);

                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarPhone.getText().toString().isEmpty() &&
                                binding.tvWarAddress.getText().toString().isEmpty() &&
                                binding.tvWarTaxCode.getText().toString().isEmpty()
                        ){
                            Supplier sup = new Supplier();
                            sup.setName(name);
                            sup.setPhone(phone);
                            sup.setAddress(address);
                            sup.setTax_code(taxCode);
                            sup.setStatus(status);
                            ApiSupplier.apiSupplier.update(token, supplier.getId(), sup).enqueue(new Callback<Supplier>() {
                                @Override
                                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                                    if (response.isSuccessful()) {
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
                        }else {
                            Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void checkStatus(){
        if (supplier.getStatus() == 1 ){
            binding.radioButtonOption1.setChecked(true);
        }else {
            binding.radioButtonOption2.setChecked(true);
        }
        binding.radioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (binding.radioButtonOption1.isChecked()){
                status = 1;
            } else if (binding.radioButtonOption2.isChecked()) {
                status = 0;
            }
        });
    }

}