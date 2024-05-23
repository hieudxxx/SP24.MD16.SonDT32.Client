package fpoly.md16.depotlife.Statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.databinding.ItemProductStatisitcBinding;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {
    private List<InventoryModel.Product> list = new ArrayList<>();
    private final InterClickItemData interClickItemData;

    private Context context;

    public interface InterClickItemData {
        void clickItem(InventoryModel.Product product);
    }

    public void setData(List<InventoryModel.Product> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public StatisticAdapter(InterClickItemData interClickItemData, Context context) {
        this.interClickItemData = interClickItemData;
        this.context = context;
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
        holder.binding.tvTonKho.setText("Sl: " + product.getTotalQuantity());
        if (product.getExpiries() != null) {
            holder.binding.tvInven.setText("Số lô: " + product.getExpiries().size());
        } else {
            holder.binding.tvInven.setText("Số lô: 0");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getExpiries() != null) {
                    interClickItemData.clickItem(product);
                }else {
                    clickData();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder {
        private ItemProductStatisitcBinding binding;

        public StatisticViewHolder(@NonNull ItemProductStatisitcBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void clickData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Sản phẩm hết hàng!");
        builder.setPositiveButton("OK",null );
        builder.show();
    }
}