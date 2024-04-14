package fpoly.md16.depotlife.Invoice.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemProductInvoiceBinding;

public class InvoiceAddAdapter extends RecyclerView.Adapter<InvoiceAddAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> list;
    private ArrayList<Product> mList;

    public InvoiceAddAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductInvoiceBinding binding = ItemProductInvoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvNameProduct.setText(list.get(position).getName());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (TextUtils.isEmpty(strSearch)) {
                    list = mList;
                } else {
                    ArrayList<Product> listFilter = new ArrayList<>();
                    for (Product product : mList) {
                        if (product.getName().toLowerCase().contains(strSearch.toLowerCase())) {
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

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemProductInvoiceBinding binding;

        public ViewHolder(@NonNull ItemProductInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
