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
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Adapter.StaffAdapter;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.BotSheetSortStaffBinding;
import fpoly.md16.depotlife.databinding.FragmentStaffBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffFragment extends Fragment {
    private FragmentStaffBinding binding;
    private ArrayList<StaffResponse.User> userList;
    private StaffAdapter adapter;
    private String token;
    private StaffResponse staffResponse;
    private  int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

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

        userList = new ArrayList<>();
        adapter = new StaffAdapter(getContext(), userList, new onMenuClick() {
            @Override
            public void onMenu(StaffResponse.User user) {
                Helper.onOptionStaff(getContext(),user);
            }
        });


        binding.rcvStaff.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvStaff.setAdapter(adapter);

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        binding.rcvStaff.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Kiểm tra nếu người dùng đã cuộn đến đầu trang và không có quá trình tải dữ liệu đang diễn ra và không phải là trang cuối cùng
                if (!isLoading && !isLastPage && (firstVisibleItemPosition + visibleItemCount) >= totalItemCount) {
                    // Tải dữ liệu mới
                    getData(currentPage);
                }
            }
        });
        getData(currentPage);


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


    public void getData(int page) {
        // Kiểm tra xem có đang loading dữ liệu không
        if (isLoading) {
            return;
        }
        // Đánh dấu là đang loading
        isLoading = true;

        ApiUser.apiUser.getStaffList("Bearer " + token, page).enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                Log.d("Res_Staff", "res: " + response.code());
                Log.d("STAFF", "RESULT: " + response);
                if (response.isSuccessful()) {
                     staffResponse = response.body();
                    Log.d("STAFF", "RESULT: " + staffResponse.toString());
                    if (staffResponse != null) {
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
                // Đánh dấu là không còn loading nữa
                isLoading = false;
            }
        });
    }


    private void onCheckList(StaffResponse staffResponse) {
        if (staffResponse.getUser() != null) {
            userList.addAll(Arrays.asList(staffResponse.getUser()));
            adapter.notifyDataSetChanged();
            currentPage++;
            isLoading = false;
            if (currentPage > staffResponse.getLast_page()) {
                isLastPage = true;
            }
            setHasOptionsMenu(true);
            binding.rcvStaff.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
        } else {
            setHasOptionsMenu(false);
            binding.rcvStaff.setVisibility(View.GONE);
            binding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

}