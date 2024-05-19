package fpoly.md16.depotlife.Statistic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

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

        PieChart pieChart = binding.pieChart;

        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(0f,"Hoá đơn xuất"));
//        entries.add(new PieEntry(0f,"Hoá đơn nhập"));
        entries.add(new PieEntry(60f,"Hoá đơn xuất"));
        entries.add(new PieEntry(75f,"Hoá đơn nhập"));


        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
//        pieChart.isvalidate();


    }
}