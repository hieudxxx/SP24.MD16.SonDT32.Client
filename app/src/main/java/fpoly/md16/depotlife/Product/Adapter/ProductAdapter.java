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
import fpoly.md16.depotlife.databinding.ItemProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private String token;

    public ProductAdapter(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void setData(List<Product> list) {
        this.list = (ArrayList<Product>) list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = list.get(holder.getAdapterPosition());
        Helper.setImgProduct(product.getImg(), holder.binding.img);

        holder.binding.tvName.setText(product.getProduct_name());
        if (product.getLocation() != null) {
            holder.binding.tvLocation.setText("Vị trí: " + product.getLocation().getCode());
        }
        holder.binding.tvInventory.setText("Tồn kho: " + product.getInventory());
        holder.binding.tvExportPrice.setText("Giá bán: " + Helper.formatVND(product.getExport_price()));
//        holder.binding.tvImportPrice.setText("Giá vốn: " + Helper.formatVND(product.getImport_price()));

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", list.get(holder.getAdapterPosition()));
            context.startActivity(new Intent(context, ProductActivity.class).putExtras(bundle));
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
