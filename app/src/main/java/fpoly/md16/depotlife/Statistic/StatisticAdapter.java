package fpoly.md16.depotlife.Statistic;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.databinding.ItemProductStatisitcBinding;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
    private ArrayList<InventoryModel.Product> list;
    private final InterClickItemData interClickItemData;

    public interface InterClickItemData {
        void clickItem(InventoryModel.Product product);
    }

    public void setData(List<InventoryModel.Product> list) {
        this.list = (ArrayList<InventoryModel.Product>) list;
        notifyDataSetChanged();
    }

    public StatisticAdapter(InterClickItemData interClickItemData) {
        this.interClickItemData = interClickItemData;
    }




    @NonNull
    @Override
    public StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductStatisitcBinding binding = ItemProductStatisitcBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StatisticViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {

        InventoryModel.Product product = list.get(position);
        if (product == null) return;

        holder.binding.tvName.setText(product.getProductName());
        holder.binding.tvTonKho.setText(product.getTotalQuantity());
        holder.binding.tvInven.setText(product.getExpiries().size());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder {
        private ItemProductStatisitcBinding binding;

        public StatisticViewHolder(@NonNull ItemProductStatisitcBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}