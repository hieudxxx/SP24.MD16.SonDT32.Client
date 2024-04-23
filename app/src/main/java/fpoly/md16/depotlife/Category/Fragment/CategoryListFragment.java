package fpoly.md16.depotlife.Category.Fragment;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Category.Adapter.CategoryListAdapter;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.databinding.FragmentCategoryListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryListFragment extends Fragment {
    private FragmentCategoryListBinding binding;
    private CategoryListAdapter adapter;
    private ArrayList<Category> list;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    private CategoryResponse categoryResponse;
    private Bundle bundle;
    private int id_cat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgAddCategory.setOnClickListener(view1 -> {

        });

        binding.imgBack.setOnClickListener(view12 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        bundle = getArguments();
        if (bundle != null) {
            id_cat = bundle.getInt("cat_id");
        }

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);
        list = new ArrayList<>();
        getData();

        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                    count++;
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    getData();
                    binding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        ApiCategory.apiCategory.getData("Bearer " + token, pageIndex).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    categoryResponse = response.body();
                    if (categoryResponse != null) {

//                        binding.tvTotalProduct.setText(productResponse.getTotal() + "");
                        perPage = categoryResponse.getLast_page();
                        onCheckList(categoryResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCheckList(CategoryResponse categoryResponse) {
        if (categoryResponse.getData() != null) {
            List<Category> tempList = Arrays.asList(categoryResponse.getData()); // hoặc có thể dùng foreach để check từng item
            list.addAll(tempList);
            if (!list.isEmpty()) {
                binding.rcvSupplier.setVisibility(View.VISIBLE);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                setHasOptionsMenu(true);
                adapter = new CategoryListAdapter(getContext(), list, id_cat);
                binding.rcvSupplier.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
                pageIndex++;
            } else {
                setHasOptionsMenu(false);
                binding.rcvSupplier.setVisibility(View.GONE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }
}