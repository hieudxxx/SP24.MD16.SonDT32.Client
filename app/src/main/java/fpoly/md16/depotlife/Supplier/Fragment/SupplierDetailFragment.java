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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Adapter.SupplierDetailAdapter;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentSupplierDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupplierDetailFragment extends Fragment {
    private FragmentSupplierDetailBinding binding;
    private SupplierDetailAdapter detailAdapter;
    private Bundle bundle;
    private Supplier supplier;
    private int id_supplier;

    private String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupplierDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = (String) Helper.getSharedPre(getContext(), "token", String.class);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbSupplier);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
//            requireActivity().finish();
        });

        bundle = getArguments();
        supplier = new Supplier();

        if (bundle != null) {
            supplier = (Supplier) bundle.getSerializable("supplier");
            id_supplier = supplier.getId();
            if (id_supplier > 0) {
                ApiSupplier.apiSupplier.getSupplier("Bearer " + token, supplier.getId()).enqueue(new Callback<Supplier>() {
                    @Override
                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {

                        if (response.isSuccessful()) {

                            supplier = response.body();
                            binding.tvName.setText(supplier.getName());

                            detailAdapter = new SupplierDetailAdapter(getActivity(), id_supplier);
                            binding.viewpager2.setAdapter(detailAdapter);
                            binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    binding.viewpager2.setCurrentItem(tab.getPosition());
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });

                            binding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);
                                    binding.tab.getTabAt(position).select();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Supplier> call, Throwable throwable) {
                        Log.d("onFailure", "onFailure: " + throwable.getMessage());
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.supplier_menu, menu);

        MenuItem item_inactive = menu.findItem(R.id.supplier_inactive);
        if (supplier.getStatus() == 0) {
            item_inactive.setTitle("Ngừng hợp tác");
        } else {
            item_inactive.setTitle("Tái hợp tác");
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.supplier_edit) {
//            bundle = new Bundle();
//            bundle.putInt("id", id_supplier);
//            Helper.loadFragment(getParentFragmentManager(), new SupplierEditFragment(), bundle, R.id.frag_container_supplier);
//            return true;
//        } else if (id == R.id.supplier_delete) {
//            Helper.onCheckdeleteDialog(getContext(), () -> {
////                ApiSupplier.apiSupplier.delete(id_supplier).enqueue(new Callback<Supplier>() {
////                    @Override
////                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
////                        if (response.code() == 403 || response.code() == 404) {
////                            Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
////                            requireActivity().getSupportFragmentManager().popBackStack();
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<Supplier> call, Throwable throwable) {
////                        Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
////
////                    }
////                });
//            });
//            return true;
//        } else if (id == R.id.supplier_inactive) {
////            ApiSupplier.apiSupplier.editByStatus(id_supplier, !supplier.isStatus()).enqueue(new Callback<Supplier>() {
////                @Override
////                public void onResponse(Call<Supplier> call, Response<Supplier> response) {
////
////                }
////
////                @Override
////                public void onFailure(Call<Supplier> call, Throwable throwable) {
////
////                }
////            });
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//    }
//

}