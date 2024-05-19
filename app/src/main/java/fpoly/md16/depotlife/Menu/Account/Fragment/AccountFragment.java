package fpoly.md16.depotlife.Menu.Account.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

    private Bundle bundle;

    private String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbAccount);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().finish());

        int id = (Integer) Helper.getSharedPre(getContext(), "id", Integer.class);
        String name = (String) Helper.getSharedPre(getContext(), "name", String.class);
        String email = (String) Helper.getSharedPre(getContext(), "email", String.class);
        String dob = (String) Helper.getSharedPre(getContext(), "birthday", String.class);
        String avt = (String) Helper.getSharedPre(getContext(), "avatar", String.class);
        String phone = (String) Helper.getSharedPre(getContext(), "phone_number", String.class);
        String address = (String) Helper.getSharedPre(getContext(), "address", String.class);

        StaffResponse.User user = new StaffResponse.User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setBirthday(dob);
        user.setAvatar(avt);
        user.setAddress(address);


        binding.imgEdit.setOnClickListener(view12 -> {
            bundle = new Bundle();
            bundle.putSerializable("user", user);
            Log.d("userr", "userr: "+user);
            Helper.loadFragment(getActivity().getSupportFragmentManager(), new EditAccountFragment(), bundle, R.id.frag_container_account);
        });

//        binding.layoutEditPass.setOnClickListener(view13 -> {
//            Helper.loadFragment(getParentFragmentManager(), new EditPassFragment(), null, R.id.frag_container_account);
//        });

        if (getContext() != null) {

            binding.tvName.setText(name);
            binding.tvFullname.setText(name);
            binding.tvEmail.setText(email);
            binding.tvDob.setText(dob);
            binding.tvPhone.setText(phone);
            binding.tvAddress.setText(address);

            String ava = avt.replace("public","storage");

            if (avt.isEmpty()) {
                binding.imgAvt.setImageResource(R.drawable.unknow_avt);
            } else {
                Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" +ava).into(binding.imgAvt);
            }


        }


    }
}