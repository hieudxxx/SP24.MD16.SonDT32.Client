package fpoly.md16.depotlife.Invoice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceInforBinding;


public class InvoiceInforFragment extends Fragment {
    private FragmentInvoiceInforBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceInforBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.layoutUser.setOnClickListener(view1 -> {
            Helper.loadFragment(getParentFragmentManager(), new StaffListFragment(), null, R.id.frag_container_invoice);

        });
    }
}