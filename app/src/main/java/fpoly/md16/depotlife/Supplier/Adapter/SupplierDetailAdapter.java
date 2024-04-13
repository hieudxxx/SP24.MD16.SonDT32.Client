package fpoly.md16.depotlife.Supplier.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpoly.md16.depotlife.Supplier.Fragment.DetailTabFragment;
import fpoly.md16.depotlife.Supplier.Fragment.HistoryTabFragment;

public class SupplierDetailAdapter extends FragmentStateAdapter {
    private Bundle bundle;
    private String id;

    public SupplierDetailAdapter(@NonNull FragmentActivity fragmentActivity, String id_supplier) {
        super(fragmentActivity);
        this.bundle = new Bundle();
        this.id = id_supplier;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        bundle.putString("id", id);
        if (position == 1) {
            HistoryTabFragment historyTabFragment = new HistoryTabFragment();
            historyTabFragment.setArguments(bundle);
            return historyTabFragment;
        }
        DetailTabFragment detailTabFragment = new DetailTabFragment();
        detailTabFragment.setArguments(bundle);
        return detailTabFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
