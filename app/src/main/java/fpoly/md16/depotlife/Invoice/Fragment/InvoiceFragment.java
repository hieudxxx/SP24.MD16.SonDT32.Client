package fpoly.md16.depotlife.Invoice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.databinding.FragmentInvoiceBinding;

public class InvoiceFragment extends Fragment {
    private FragmentInvoiceBinding binding;
    private InvoiceAdapter adapter;
    private ArrayList<Invoice> list;
    private Boolean isExpanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbInvoice);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.fab.setOnClickListener(view1 -> {
//            onShowFab();
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class));
        });

        binding.layoutCalendar.setOnClickListener(view12 -> {
            Helper.onShowCaledar(binding.tvDate, getContext());
        });

        list = new ArrayList<>();
//        getData();

    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.toolbar_menu, menu);
//    }

//    private void getData() {
//        ApiInvoice.apiInvoice.add().enqueue(new Callback<ArrayList<Invoice>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Invoice>> call, Response<ArrayList<Invoice>> response) {
////                Log.d("tag_kiemTra", "onResponse: " + response.code());
//
//                if (response.isSuccessful()) {
//                    list = response.body();
////                    Log.d("tag_kiemTra", "onResponse: " + response);
//                }
//                if (list != null && !list.isEmpty()) {
//                    binding.rcvInvoice.setVisibility(View.VISIBLE);
//                    binding.layoutTotal.setVisibility(View.VISIBLE);
//                    binding.layoutCalendar.setVisibility(View.VISIBLE);
//                    binding.tvEmpty.setVisibility(View.GONE);
//                    setHasOptionsMenu(true);
//                } else {
//                    binding.rcvInvoice.setVisibility(View.GONE);
//                    binding.layoutTotal.setVisibility(View.GONE);
//                    binding.layoutCalendar.setVisibility(View.GONE);
//                    binding.tvEmpty.setVisibility(View.VISIBLE);
//                    setHasOptionsMenu(false);
//                }
//                adapter = new InvoiceAdapter(getContext(), list, getParentFragmentManager());
//                binding.rcvInvoice.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Invoice>> call, Throwable t) {
//                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
//                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void onShowFab() {
        binding.fab.shrink();
        if (!isExpanded) {
            binding.overlay.setVisibility(View.VISIBLE);
            binding.fabAddExport.show();
            binding.fabAddImport.show();
            binding.tvAddImport.setVisibility(View.VISIBLE);
            binding.tvAddExport.setVisibility(View.VISIBLE);
            binding.fab.extend();
            isExpanded = true;
        } else {
            binding.overlay.setVisibility(View.GONE);
            binding.fabAddExport.hide();
            binding.fabAddImport.hide();
            binding.tvAddImport.setVisibility(View.GONE);
            binding.tvAddExport.setVisibility(View.GONE);
            binding.fab.shrink();
            isExpanded = false;
        }

        Bundle bundle = new Bundle();
        binding.fabAddExport.setOnClickListener(view5 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type", 1));
        });

        binding.fabAddImport.setOnClickListener(view5 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("type", 0));
        });
    }
}