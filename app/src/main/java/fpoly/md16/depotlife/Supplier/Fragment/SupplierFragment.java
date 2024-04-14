package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Api.ApiSupplier;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierAdapter;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BotSheetFilterSupplierBinding;
import fpoly.md16.depotlife.databinding.FragmentSupplierBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierFragment extends Fragment {

    private FragmentSupplierBinding binding;
    private ArrayList<Supplier> list;
    private SupplierAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        binding.fab.setOnClickListener(view12 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierAddFragment(), null, R.id.frag_container_supplier);

        });

        list = new ArrayList<>();
        getData();
    }

    private void getData() {
        ApiSupplier.apiSupplier.getSupplierList().enqueue(new Callback<ArrayList<Supplier>>() {
            @Override
            public void onResponse(Call<ArrayList<Supplier>> call, Response<ArrayList<Supplier>> response) {
//                Log.d("tag_kiemTra", "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    list = response.body();
//                    Log.d("tag_kiemTra", "onResponse: " + response);
                }
                if (list != null && !list.isEmpty()) {
                    binding.rcvInvoice.setVisibility(View.VISIBLE);
                    binding.layoutTotal.setVisibility(View.VISIBLE);
                    binding.tvEmpty.setVisibility(View.GONE);
                    setHasOptionsMenu(true);
                } else {
                    binding.rcvInvoice.setVisibility(View.GONE);
                    binding.layoutTotal.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    setHasOptionsMenu(false);
                }
                adapter = new SupplierAdapter(getContext(), list);
                binding.rcvInvoice.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Supplier>> call, Throwable t) {
                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_search) {
            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.item_sort) {
            Helper.onSort(getContext(), list, adapter, Supplier.sortByAsc, Supplier.sortByNameAZ);
            return true;
        } else if (id == R.id.item_filter) {
            onFilter();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onFilter() {
        BotSheetFilterSupplierBinding filterBinding = BotSheetFilterSupplierBinding.inflate(LayoutInflater.from(getActivity()));
        filterBinding.rdGr.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rd_filter_active) {
                adapter = new SupplierAdapter(getContext(), Supplier.filterByStatus(list, true));
            } else if (i == R.id.rd_filter_inactive) {
                adapter = new SupplierAdapter(getContext(), Supplier.filterByStatus(list, false));
            } else if (i == R.id.rd_filter_all) {
                adapter = new SupplierAdapter(getContext(), list);
            }
            binding.rcvInvoice.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        Helper.onSettingsBotSheet(getContext(), filterBinding);
    }
}