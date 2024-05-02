package fpoly.md16.depotlife.Menu.Account.Fragment;

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
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiUser;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.BotSheetImportImgBinding;
import fpoly.md16.depotlife.databinding.FragmentEditAccountBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccountFragment extends Fragment {
    private FragmentEditAccountBinding binding;

    private Bundle bundle;

    private StaffResponse.User user;
    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbAccount);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);


        bundle = getArguments();
        if (bundle != null) {
            user = (StaffResponse.User) bundle.getSerializable("user");

            if (user != null) {

                binding.edtName.setText(user.getName());
                binding.edtPhone.setText(user.getPhoneNumber());
                binding.edtAddress.setText(user.getAddress());
                binding.edtBirthday.setText(user.getBirthday());


                binding.tvSave.setOnClickListener(view12 -> {

                    String name = binding.edtName.getText().toString().trim();
                    String phone = binding.edtPhone.getText().toString().trim();
                    String address = binding.edtAddress.getText().toString().trim();
                    String birthday = binding.edtBirthday.getText().toString().trim();



                    if (name.isEmpty() || phone.isEmpty() ||  address.isEmpty()  || birthday.isEmpty()) {
                        Toast.makeText(getContext(), "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        Helper.isContainSpace(name, binding.tvWarName);
                        Helper.isPhoneValid(phone, binding.tvWarPhone);
                        Helper.isContainSpace(address, binding.tvWarAddress);
//                        Helper.isContainSpace(birthday, binding.tvWarBday);

                        if (binding.tvWarName.getText().toString().isEmpty() &&
                                binding.tvWarPhone.getText().toString().isEmpty() &&
                                binding.tvWarAddress.getText().toString().isEmpty()
//                                binding.tvWarBday.getText().toString().isEmpty()
                        ){
                            StaffResponse.User user1 = new StaffResponse.User();
                            user1.setName(name);
                            user1.setPhoneNumber(phone);
                            user1.setBirthday(birthday);
                            user1.setAddress(address);
//                            user.setAvatar("avt");

                            ApiUser.apiUser.update(token, user.getId(), user1).enqueue(new Callback<StaffResponse.User>() {
                                @Override
                                public void onResponse(Call<StaffResponse.User> call, Response<StaffResponse.User> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                }

                                @Override
                                public void onFailure(Call<StaffResponse.User> call, Throwable throwable) {
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
}