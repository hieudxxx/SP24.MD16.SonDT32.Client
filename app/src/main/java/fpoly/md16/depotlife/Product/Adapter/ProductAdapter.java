package fpoly.md16.depotlife.Product.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Activity.ProductActivity;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemLoadingBinding;
import fpoly.md16.depotlife.databinding.ItemProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private String token;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private boolean isLoading;

    public ProductAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void setData(List<Product> list) {
        this.list = (ArrayList<Product>) list;
        notifyDataSetChanged();
    }

//    public void addData(List<Product> newData) {
//        if (newData != null && !newData.isEmpty()) {
//            if (this.list == null) {
//                this.list = new ArrayList<>(); // Khởi tạo list nếu nó là null
//            }
//            int startPosition = this.list.size();
//            this.list.addAll(newData);
//            notifyItemRangeInserted(startPosition, newData.size());
//        }
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {
            ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProductViewHolder(binding);

        } else {
            ItemLoadingBinding loadBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingViewHolder(loadBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ProductViewHolder viewHolder = (ProductViewHolder) holder;

            Product product = list.get(holder.getAdapterPosition());
            Helper.setImgProduct(product.getImg(), viewHolder.binding.img);

            viewHolder.binding.tvName.setText(product.getProduct_name());
            if (product.getLocation() != null) {
                viewHolder.binding.tvLocation.setText("Vị trí: " + product.getLocation().getCode());
            }
            viewHolder.binding.tvInventory.setText("Tồn kho: " + product.getInventory());
            viewHolder.binding.tvExportPrice.setText("Giá bán: " + Helper.formatVND(product.getExport_price()));
//        holder.binding.tvImportPrice.setText("Giá vốn: " + Helper.formatVND(product.getImport_price()));

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", list.get(holder.getAdapterPosition()));
                context.startActivity(new Intent(context, ProductActivity.class).putExtras(bundle));
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && position == list.size() - 1 && isLoading) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ItemLoadingBinding loadBinding;

        public LoadingViewHolder(@NonNull ItemLoadingBinding loadBinding) {
            super(loadBinding.getRoot());
            this.loadBinding = loadBinding;
        }
    }

    public void addFooterLoading() {
        isLoading = true;
    }

    public void removeFooterLoading() {
        isLoading = false;
        int position = list.size() - 1;
        if (list.get(position) != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }
}
