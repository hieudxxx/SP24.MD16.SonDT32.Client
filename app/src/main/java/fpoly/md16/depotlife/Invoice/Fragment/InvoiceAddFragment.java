package fpoly.md16.depotlife.Invoice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceAddAdapter;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceAddBinding;


public class InvoiceAddFragment extends Fragment {
    private FragmentInvoiceAddBinding binding;
    private String token;

    private ArrayList<Product> list;
    private InvoiceAddAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceAddBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        binding.tvDateTime.setText(currentDate);

        Bundle bundle = getActivity().getIntent().getExtras();
        int type = bundle.getInt("type");
        if (type == 0) {
            binding.tvToolbar.setText("Tạo phiếu nhập");
        } else {
            binding.tvToolbar.setText("Tạo phiếu xuất");
        }

        binding.imgBack.setOnClickListener(view1 -> requireActivity().finish());
        list = new ArrayList<>();

        binding.btnSave.setOnClickListener(view12 -> {
            
        });

//        ApiProduct.apiProduct.getProductList().enqueue(new Callback<ArrayList<Product>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
////                Log.d("tag_kiemTra", "onResponse: " + response.code());
//
//                if (response.isSuccessful()) {
//                    list = response.body();
////                    Log.d("tag_kiemTra", "onResponse: " + response);
//                }
//                adapter = new InvoiceAddAdapter(getContext(), list);
//                binding.rcvAddInvoice.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
//                Log.d("tag_kiemTra", "onFailure: " + t.getMessage());
//                Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.svProduct.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        binding.imgCategory.setOnClickListener(view1 -> {
            Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), null, R.id.frag_container_invoice);
        });
    }
}