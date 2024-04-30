package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierListSelectAdapter;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentSupplierListSelectBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierListSelectFragment extends Fragment implements onItemRcvClick<Supplier> {
    private FragmentSupplierListSelectBinding binding;
    private SupplierListSelectAdapter adapter;
    private ArrayList<Supplier> list;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    private SupplierResponse supplierResponse;
    private Supplier supplierSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierListSelectBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgAddSupplider.setOnClickListener(view1 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierAddFragment(), null, R.id.frag_container_product);
        });

        binding.imgBack.setOnClickListener(view12 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);
        list = new ArrayList<>();

        getData();

        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    getData();
                    binding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });

        binding.tvSave.setOnClickListener(view13 -> {
            ShareViewModel viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
            viewModel.select(supplierSelected);
            requireActivity().getSupportFragmentManager().popBackStack();

        });
    }

    private void getData() {
        ApiSupplier.apiSupplier.getData(token, pageIndex).enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if (response.isSuccessful()) {
                    supplierResponse = response.body();
                    if (supplierResponse != null) {
                        perPage = supplierResponse.getLast_page();
                        onCheckList(supplierResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCheckList(SupplierResponse supplierResponse) {
        if (supplierResponse.getData() != null) {
            list.addAll(Arrays.asList(supplierResponse.getData()));
            if (!list.isEmpty()) {
                binding.rcv.setVisibility(View.VISIBLE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                setHasOptionsMenu(true);
                adapter = new SupplierListSelectAdapter(getContext(), list, this);
                binding.rcv.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                pageIndex++;
            } else {
                setHasOptionsMenu(false);
                binding.rcv.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(Supplier item) {
        supplierSelected = item;
    }
}