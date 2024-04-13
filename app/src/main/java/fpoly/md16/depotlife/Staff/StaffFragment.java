package fpoly.md16.depotlife.Staff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fpoly.md16.depotlife.Staff.Adapter.StaffAdapter;
import fpoly.md16.depotlife.Staff.Model.UserResponse;
import fpoly.md16.depotlife.databinding.FragmentStaffBinding;

public class StaffFragment extends Fragment {
    private FragmentStaffBinding binding;
    private ArrayList<UserResponse.User> list;
    private StaffAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStaffBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbStaff);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        list = new ArrayList<>();
//        getData();


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