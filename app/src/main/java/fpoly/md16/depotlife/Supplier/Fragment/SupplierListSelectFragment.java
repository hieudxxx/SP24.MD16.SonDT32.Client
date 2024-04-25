package fpoly.md16.depotlife.Supplier.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.Supplier.Model.SupplierResponse;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentSupplierListSelectBinding;

public class SupplierListSelectFragment extends Fragment {
    private FragmentSupplierListSelectBinding binding;

    private SupplierListSelectFragment adapter;
    private ArrayList<Supplier> list;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    private SupplierResponse supplierResponse;
    private Supplier supplierSelected;
    private Bundle bundle;

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


        bundle = getArguments();
        if (bundle != null) {

//            getData();

            binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    binding.pbLoadMore.setVisibility(View.VISIBLE);
                    if (pageIndex <= perPage) {
//                        getData();
                        binding.pbLoadMore.setVisibility(View.GONE);
                    } else {
                        binding.pbLoadMore.setVisibility(View.GONE);
                    }
                }
            });

            binding.tvSave.setOnClickListener(view13 -> {
//                bundle = new Bundle();
//                bundle.putSerializable("category",categorySelected);
//                Helper.loadFragment(getParentFragmentManager(), new ProductEditFragment(), bundle, R.id.frag_container_product);

                ShareViewModel viewModel = new ViewModelProvider(requireActivity()).get(ShareViewModel.class);
                viewModel.select(supplierSelected);
                requireActivity().getSupportFragmentManager().popBackStack();

            });

        }



    }

//    private void getData() {
//        ApiSupplier.apiSupplier.getData(token, pageIndex).enqueue(new Callback<SupplierResponse>() {
//            @Override
//            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
//                if (response.isSuccessful()) {
//                    supplierResponse = response.body();
//                    if (supplierResponse != null) {
//                        perPage = supplierResponse.getLast_page();
//                        onCheckList(categoryResponse);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
//                Log.d("onFailure", "onFailure: " + throwable.getMessage());
//                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}