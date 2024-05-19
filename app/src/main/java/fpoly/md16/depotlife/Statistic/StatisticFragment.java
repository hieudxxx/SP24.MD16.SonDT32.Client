package fpoly.md16.depotlife.Statistic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BottomsheetstatisticBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;
import fpoly.md16.depotlife.databinding.FragmentStatisticBinding;


public class StatisticFragment extends Fragment {
    private FragmentStatisticBinding binding;

    private CombinedChart chart;

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
//            Helper.onShowCaledar(binding.tvDate, getContext());
            showBottomSheet();
        });

        chart = binding.combinedChart;

        setupCombinedChart();

        binding.listProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hiển thị popup list sản phẩm tồn kho
            }
        });

//        binding.filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showBottomSheet();
//            }
//        });


    }

    public void showBottomSheet() {
        BottomsheetstatisticBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bottomsheetstatistic, null);
        binding = BottomsheetstatisticBinding.inflate(inflater,view,false);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.btnThietlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnApdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setupCombinedChart() {
        CombinedData combinedData = new CombinedData();

        // Set line data
        combinedData.setData(generateLineData());

        // Set bar data
        combinedData.setData(generateBarData());

        chart.setData(combinedData);
        chart.invalidate();

        // Description setup
        Description description = new Description();
        description.setText("Combined Chart Example");
        chart.setDescription(description);
    }

    private LineData generateLineData() {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(0, 5));
        lineEntries.add(new Entry(1, 10));
        lineEntries.add(new Entry(2, 15));
        lineEntries.add(new Entry(3, 20));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Line Data");
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleRadius(5f);

        return new LineData(lineDataSet);
    }

    private BarData generateBarData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 5));
        barEntries.add(new BarEntry(1, 10));
        barEntries.add(new BarEntry(2, 15));
        barEntries.add(new BarEntry(3, 20));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Bar Data");
        barDataSet.setColor(Color.RED);

        return new BarData(barDataSet);
    }
}
