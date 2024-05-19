package fpoly.md16.depotlife.Invoice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BotSheetSortInvoiceBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceImportBinding;

public class InvoiceImportFragment extends Fragment {
    private FragmentInvoiceImportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceImportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAdd.setOnClickListener(view1 -> {

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.invoice_export_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.searchInvoice) {
            return true;
//        } else
//        if (item.getItemId() == R.id.sort_bar) {
//            onShowBotSheetSort();
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
    }

    private void onShowBotSheetSort() {
        BotSheetSortInvoiceBinding sortBinding = BotSheetSortInvoiceBinding.inflate(LayoutInflater.from(getActivity()));
        sortBinding.rdGr.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == R.id.rd_sort_asc) {
//                Collections.sort(list, Bill_Out.sortByAscSum);
            } else if (i == R.id.rd_sort_decs) {
//                Collections.sort(list, Bill_Out.sortByDescSum);
            } else if (i == R.id.rd_sort_new) {
//                Collections.sort(list, Bill_Out.sortByNameAZ);
            } else if (i == R.id.rd_sort_old) {
//                Collections.sort(list, Bill_Out.sortByNameZA);
            }
//            adapter.notifyDataSetChanged();
        }));
        Helper.onSettingsBotSheet(getContext(), sortBinding);

    }
}