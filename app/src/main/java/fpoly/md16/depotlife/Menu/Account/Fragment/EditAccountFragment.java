package fpoly.md16.depotlife.Menu.Account.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.databinding.BotSheetImportImgBinding;
import fpoly.md16.depotlife.databinding.FragmentEditAccountBinding;

public class EditAccountFragment extends Fragment {
    private FragmentEditAccountBinding binding;
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

        binding.img.setOnClickListener(view1 -> {
            BotSheetImportImgBinding importBinding = BotSheetImportImgBinding.inflate(LayoutInflater.from(getActivity()));


            Helper.onSettingsBotSheet(getContext(), importBinding);

        });
    }
}