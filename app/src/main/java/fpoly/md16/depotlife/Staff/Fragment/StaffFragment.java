package fpoly.md16.depotlife.Staff.Fragment;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiUser;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onMenuClick;
import fpoly.md16.depotlife.Product.Adapter.ProductAdapter;
import fpoly.md16.depotlife.Product.Model.ImagesResponse;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Adapter.StaffAdapter;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.BotSheetSortStaffBinding;
import fpoly.md16.depotlife.databinding.FragmentStaffBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffFragment extends Fragment {
    private FragmentStaffBinding binding;
    private ArrayList<StaffResponse.User> userList;
    private StaffAdapter adapter;
    private StaffResponse staffResponse;
    private ImagesResponse imagesResponse;
    private int pageIndex = 1;
    private int perPage = 0;
    private int count = 0;
    private String token;
    private boolean isLoadData = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbStaff);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        token = "Bearer " + (String) Helper.getSharedPre(getContext(), "token", String.class);

        userList = new ArrayList<>();
        getData();

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);


        binding.nestScoll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                binding.pbLoadMore.setVisibility(View.VISIBLE);
                if (pageIndex <= perPage) {
                    Log.d("onScrollChange", "onScrollChange: " + pageIndex);
                    getData();
                    binding.pbLoadMore.setVisibility(View.GONE);
                } else {
                    binding.pbLoadMore.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tool_menu_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchCategory) {
            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.sortCategory) {
            Helper.onSortStaff(getContext());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    public void getData() {
        ApiUser.apiUser.getStaffList( token, pageIndex).enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                if (response.isSuccessful()) {
                    staffResponse = response.body();
                    if (staffResponse != null) {
                        perPage = staffResponse.getLast_page();
                        onCheckList(staffResponse);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("onResponse", "errorBody: " + errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StaffResponse> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onCheckList(StaffResponse staffResponse) {
        if (staffResponse.getUser() != null) {
            List<StaffResponse.User> tempList = Arrays.asList(staffResponse.getUser()); // hoặc có thể dùng foreach để check từng item
            userList.addAll(tempList);
            binding.rcvStaff.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            setHasOptionsMenu(true);
            adapter = new StaffAdapter(getContext(), userList, token);
            binding.rcvStaff.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            pageIndex++;
        } else {
            setHasOptionsMenu(false);
            binding.rcvStaff.setVisibility(View.GONE);
            binding.pbLoading.setVisibility(View.GONE);
            binding.pbLoadMore.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

}