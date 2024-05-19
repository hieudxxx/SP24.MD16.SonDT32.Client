package fpoly.md16.depotlife.Invoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemInvoiceDetailBinding;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.InvoiceDetailViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private ArrayList<Product> mList;

    public InvoiceDetailAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        this.mList = list;
    }

    @NonNull
    @Override
    public InvoiceDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvoiceDetailBinding binding = ItemInvoiceDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceDetailViewHolder holder, int position) {
        holder.binding.tvNameProduct.setText(list.get(position).getId() + list.get(position).getProduct_name());

        holder.binding.tvPrice.setText(Helper.formatVND(list.get(position).getExport_price()));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class InvoiceDetailViewHolder extends RecyclerView.ViewHolder {
        private ItemInvoiceDetailBinding binding;

        public InvoiceDetailViewHolder(@NonNull ItemInvoiceDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
