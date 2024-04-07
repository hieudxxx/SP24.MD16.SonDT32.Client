package fpoly.md16.depotlife.Statistic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;
import fpoly.md16.depotlife.databinding.FragmentStatisticBinding;


public class StatisticFragment extends Fragment {
    private FragmentStatisticBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.layoutCalendar.setOnClickListener(view1 -> {
            Helper.onShowCaledar(binding.tvDate, getContext());
        });
    }
}