package fpoly.md16.depotlife.Menu.Account.Fragment;

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
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

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

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        binding.imgEdit.setOnClickListener(view12 -> {
            Helper.loadFragment(getParentFragmentManager(), new EditAccountFragment(), null, R.id.frag_container_account);
        });

        binding.layoutEditPass.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new EditPassFragment(), null, R.id.frag_container_account);
        });

        if (getContext() != null) {
            String name = (String) Helper.getSharedPre(getContext(), "name", String.class);
            String email = (String) Helper.getSharedPre(getContext(), "email", String.class);
            String dob = (String) Helper.getSharedPre(getContext(), "birthday", String.class);
            String avt = (String) Helper.getSharedPre(getContext(), "avatar", String.class);

            binding.tvName.setText(name);
            binding.tvFullname.setText(name);
            binding.tvEmail.setText(email);
            binding.tvDob.setText(dob);
            binding.tvDob.setText(dob);

            if (avt.isEmpty()) {
                binding.imgAvt.setImageResource(R.drawable.unknow_avt);
            } else {
                Picasso.get().load(avt).into(binding.imgAvt);

            }


        }


    }
}