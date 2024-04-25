package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentSupplierAddBinding;

public class SupplierAddFragment extends Fragment {
    private FragmentSupplierAddBinding binding;

    private Supplier supplier;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierAddBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
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

        });
    }
}