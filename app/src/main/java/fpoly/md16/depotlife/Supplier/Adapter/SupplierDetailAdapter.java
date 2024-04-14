package fpoly.md16.depotlife.Supplier.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fpoly.md16.depotlife.Supplier.Fragment.DetailTabFragment;
import fpoly.md16.depotlife.Supplier.Fragment.HistoryTabFragment;

public class SupplierDetailAdapter extends FragmentStateAdapter {
    public SupplierDetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new HistoryTabFragment();
        }
        return new DetailTabFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
