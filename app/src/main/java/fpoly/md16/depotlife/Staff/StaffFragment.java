package fpoly.md16.depotlife.Staff;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.OnItemClickListener;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Product.ProductFilterActivity;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Adapter.StaffAdapter;
import fpoly.md16.depotlife.Staff.Model.UserResponse;
import fpoly.md16.depotlife.databinding.BotSheetSortStaffBinding;
import fpoly.md16.depotlife.databinding.FragmentStaffBinding;

public class StaffFragment extends Fragment {
    private FragmentStaffBinding binding;
    private ArrayList<UserResponse.User> list;
    private StaffAdapter adapter;

    BotSheetSortStaffBinding sortStaffBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStaffBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbStaff);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);




        ArrayList<UserResponse.User> list1 = new ArrayList<>();
        list1.add(new UserResponse.User("Nguyen Van Sang","0961984330","","abc@gmail.com",false));

        adapter =new StaffAdapter(getContext(), list1, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Helper.onSortStaff(getContext());
            }
        });
        binding.rcvStaff.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.rcvStaff.setAdapter(adapter);

//        list = new ArrayList<>();
//        getData();


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
//            Helper.onSearch(item, adapter);
            return true;
        } else if (id == R.id.sortCategory) {
                Helper.onSortStaff(getContext());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }







//    private void getData() {
//        ApiUser.apiUser.getStaffList().enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
////                Log.d("tag_kiemTra", "onResponse: " + response.code());
//
//                if (response.isSuccessful()) {
//                    list = response.body();
////                    Log.d("tag_kiemTra", "onResponse: " + response);
//                }
//                if (list != null && !list.isEmpty()) {
//                    binding.rcvStaff.setVisibility(View.VISIBLE);
////                    binding.layoutTotal.setVisibility(View.VISIBLE);
//                    binding.tvEmpty.setVisibility(View.GONE);
//                    setHasOptionsMenu(true);
//                } else {
//                    binding.rcvStaff.setVisibility(View.GONE);
////                    binding.layoutTotal.setVisibility(View.GONE);
//                    binding.tvEmpty.setVisibility(View.VISIBLE);
//                    setHasOptionsMenu(false);
//                }
//                adapter = new StaffAdapter(getContext(), list);
//                binding.rcvStaff.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<UserResponse.User>> call, Throwable t) {
//                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
//                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}