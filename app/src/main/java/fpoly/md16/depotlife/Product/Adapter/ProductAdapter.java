package fpoly.md16.depotlife.Product.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Activity.ProductActivity;
import fpoly.md16.depotlife.Product.Model.ImagesResponse;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemProductBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> list;
    private ArrayList<Product> mlist;
    private String token;
    private ImagesResponse imagesResponse;

    public ProductAdapter(Context context, ArrayList<Product> list, String token) {
        this.context = context;
        this.list = list;
        this.mlist = list;
        this.token = token;
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
        if (product.getImg() == null) product.setImg("null");

        Helper.getImagesProduct(product, token, holder.binding.img);

        holder.binding.tvName.setText(product.getProduct_name());
        holder.binding.tvBarcode.setText(product.getBarcode());
        holder.binding.tvBarcode.setText(product.getBarcode());
        holder.binding.tvInventory.setText("Tồn kho: " + product.getInventory());
        holder.binding.tvExportPrice.setText("Giá bán: " + Helper.formatVND(product.getExport_price()));
        holder.binding.tvImportPrice.setText("Giá vốn: " + Helper.formatVND(product.getImport_price()));

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list = mlist;
                } else {
                    ArrayList<Product> listFilter = new ArrayList<>();
                    for (Product product : mlist) {
                        if (product.getProduct_name().toLowerCase().contains(strSearch.toLowerCase())) {
                            listFilter.add(product);
                        }
                    }
                    list = listFilter;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
