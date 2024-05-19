package fpoly.md16.depotlife.Product.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.Product.Model.BatchResponse;
import fpoly.md16.depotlife.databinding.ItemProductBatchBinding;

public class ProductBatchAdapter extends RecyclerView.Adapter<ProductBatchAdapter.ViewHolder> {
    private Context context;
    private List<BatchResponse> list;
    private onItemRcvClick onItemRcvClick;

    public ProductBatchAdapter(Context context, List<BatchResponse> list, onItemRcvClick onItemRcvClick) {
        this.context = context;
        this.list = list;
        this.onItemRcvClick = onItemRcvClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBatchBinding binding = ItemProductBatchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvIdBatch.setText("Số lô: " + list.get(position).getId());
        holder.binding.tvQuantity.setText("Số lượng: " + list.get(position).getQuantity_exp());
        holder.binding.tvExpiry.setText("Hạn sử dụng: " + list.get(position).getExpiry_date());

        holder.binding.imgDelete.setOnClickListener(view -> {
            if (onItemRcvClick != null) {
                onItemRcvClick.onClick(holder.getAdapterPosition());
            }
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
        private ItemProductBatchBinding binding;

        public ViewHolder(@NonNull ItemProductBatchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
