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
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentSupplierAddBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierAddFragment extends Fragment {
    private FragmentSupplierAddBinding binding;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbAccount);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);

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
                Helper.isTaxValid(taxCode, binding.tvWarTaxCode);

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
//                    sup.setTotal(0);
                    sup.setStatus(1);
                    Log.d("tag_kiemTra", "onResponse: " + sup.toString());
                    ApiSupplier.apiSupplier.create(token, sup).enqueue(new Callback<Supplier>() {
                        @Override
                        public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                            Log.d("tag_kiemTra", "onResponse: " + response.code());
                            Log.d("tag_kiemTra", "onResponse: " + response);
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm ncc thành công", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Thêm ncc thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}