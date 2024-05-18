package fpoly.md16.depotlife.Invoice.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ItemSupplierBinding;

public class DialogSupplierAdapter extends RecyclerView.Adapter<DialogSupplierAdapter.ViewHolder> {
    private ArrayList<Supplier> list;

    private InterClickItemData interClickItemData;

    public interface InterClickItemData {
        void chooseItem(Supplier supplier);
    }
    public void setData(List<Supplier> list) {
        this.list = (ArrayList<Supplier>) list;
        notifyDataSetChanged();
    }


    public DialogSupplierAdapter(InterClickItemData interClickItemData) {
        this.interClickItemData = interClickItemData;
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

        holder.binding.tvStatus.setText(list.get(position).getStatus() == 1 ? "Hợp tác" : "Đã hủy");
        holder.binding.tvStatus.setTextColor(list.get(position).getStatus() == 1 ? Color.GREEN : Color.RED);

        holder.binding.tvPhone.setText(list.get(position).getPhone());
        holder.binding.tvTax.setText(list.get(position).getTax_code());
        holder.binding.tvTotalMoney.setText(String.valueOf(list.get(position).getTotal()));

        holder.itemView.setOnClickListener(view -> {
            interClickItemData.chooseItem(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSupplierBinding binding;

        public ViewHolder(@NonNull ItemSupplierBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
