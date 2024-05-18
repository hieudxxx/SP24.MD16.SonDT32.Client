package fpoly.md16.depotlife.Supplier.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ItemSelectListBinding;

public class SupplierListSelectAdapter extends RecyclerView.Adapter<SupplierListSelectAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Supplier> list;
    private ArrayList<Supplier> mlist;
    private int index = -1;
    private onItemRcvClick onItemRcvClick;

    public SupplierListSelectAdapter(Context context, ArrayList<Supplier> list, onItemRcvClick onItemRcvClick) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.onItemRcvClick = onItemRcvClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectListBinding binding = ItemSelectListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getName());
        holder.binding.rdSelect.setChecked(position == index);

        holder.binding.rdSelect.setOnClickListener(view -> {
            index = holder.getAdapterPosition();
            if (onItemRcvClick != null) {
                onItemRcvClick.onClick(list.get(holder.getAdapterPosition()));
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectListBinding binding;

        public ViewHolder(@NonNull ItemSelectListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
