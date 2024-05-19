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
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;

import fpoly.md16.depotlife.Category.Adapter.CategoryListSelectAdapter;
import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Category.Model.CategoryResponse;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCategory;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.ViewModel.ShareViewModel;
import fpoly.md16.depotlife.databinding.FragmentCategoryListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryListFragment extends Fragment implements onItemRcvClick<Category> {
    private FragmentCategoryListBinding binding;
    private CategoryListSelectAdapter adapter;
    private ArrayList<Category> list;
    private int pageIndex = 1;
    private int perPage = 0;
    private String token;
    private CategoryResponse categoryResponse;
    private Category categorySelected;
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

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tb);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view12 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.imgAddCategory.setOnClickListener(view14 -> {

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
            viewModel.select(categorySelected);
            requireActivity().getSupportFragmentManager().popBackStack();
        });


    }

    private void getData() {
        ApiCategory.apiCategory.getData(token, pageIndex).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    categoryResponse = response.body();
                    if (categoryResponse != null) {
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
            list.addAll(Arrays.asList(categoryResponse.getData()));
            if (!list.isEmpty()) {
                binding.rcv.setVisibility(View.VISIBLE);
                binding.pbLoading.setVisibility(View.GONE);
                binding.pbLoadMore.setVisibility(View.GONE);
                setHasOptionsMenu(true);
                adapter = new CategoryListSelectAdapter(getContext(), list, this);
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
    public void onClick(Category category) {
        categorySelected = category;
    }
}