package fpoly.md16.depotlife.Invoice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Fragment.ProductAddFragment;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceEditBinding;

public class InvoiceEditFragment extends Fragment {
    private FragmentInvoiceEditBinding binding;
    private Bundle bundle;
    private Invoice invoice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceEditBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        bundle = getArguments();
        if (bundle!=null){
            invoice = (Invoice) bundle.getSerializable("invoice");
            bundle = new Bundle();
            bundle.putSerializable("invoice", invoice);
        }

        binding.imgInfor.setOnClickListener(view12 -> {

            Helper.loadFragment(getParentFragmentManager(), new InvoiceInforFragment(), bundle, R.id.frag_container_invoice);
        });

        binding.layoutSupplier.setOnClickListener(view13 -> {
            Helper.loadFragment(getParentFragmentManager(), new SupplierListFragment(), bundle, R.id.frag_container_invoice);

        });

        binding.imgCategory.setOnClickListener(view14 -> {
            Helper.loadFragment(getParentFragmentManager(), new CategoryListFragment(), bundle, R.id.frag_container_invoice);

        });

        binding.imgAddProduct.setOnClickListener(view15 -> {
            Helper.loadFragment(getParentFragmentManager(), new ProductAddFragment(), null, R.id.frag_container_invoice);

        });
    }
}