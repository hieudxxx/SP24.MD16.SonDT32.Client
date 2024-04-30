package fpoly.md16.depotlife.Supplier.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierDetailFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ItemSupplierBinding;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Supplier> list;
    private ArrayList<Supplier> mlist;
    private FragmentManager fragmentManager;

    public void setData(List<Supplier> list) {
        this.list = (ArrayList<Supplier>) list;
        notifyDataSetChanged();
    }

    public SupplierAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSupplierBinding binding = ItemSupplierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.tvName.setText(list.get(position).getName());

        holder.binding.tvStatus.setText(list.get(position).getStatus() == 1 ? "Đang hợp tác" : "Đã ngừng hợp tác");

//        holder.binding.tvTotal.setText(Helper.formatVND(list.get(position).getTotal()));

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("supplier", list.get(holder.getAdapterPosition()));
            Helper.loadFragment(fragmentManager, new SupplierDetailFragment(), bundle, R.id.frag_container_supplier);
//            context.startActivity(new Intent(context, SupplierActivity.class).putExtras(bundle));
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<Supplier> listFilter = new ArrayList<>();
                    for (Supplier supplier : mlist) {
                        if (supplier.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listFilter.add(supplier);
                        }
                    }
                    list = listFilter;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Supplier>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSupplierBinding binding;

        public ViewHolder(@NonNull ItemSupplierBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
