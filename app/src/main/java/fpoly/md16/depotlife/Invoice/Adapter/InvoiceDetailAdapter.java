package fpoly.md16.depotlife.Invoice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ItemInvoiceDetailBinding;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.InvoiceDetailViewHolder> {
    private ArrayList<Invoice.ProductInvoice> list;
    public void setData(List<Invoice.ProductInvoice> list) {
        this.list = (ArrayList<Invoice.ProductInvoice>) list;
        notifyDataSetChanged();
    }

    private final int invoiceType;

    public InvoiceDetailAdapter(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    @NonNull
    @Override
    public InvoiceDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvoiceDetailBinding binding = ItemInvoiceDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceDetailViewHolder holder, int position) {
        Invoice.ProductInvoice productInvoice = list.get(position);

        if (productInvoice == null) return;

        holder.binding.tvIdProduct.setText(String.valueOf(position));

        holder.binding.tvNameProduct.setText(productInvoice.getProduct().getProduct_name());

        holder.binding.tvQuantityProduct.setText(String.valueOf(productInvoice.getQuantity()));

        if (invoiceType == 0) {
            holder.binding.tvPriceProduct.setText(Helper.formatVND(productInvoice.getProduct().getImport_price()));
        }else {
            holder.binding.tvPriceProduct.setText(Helper.formatVND(productInvoice.getProduct().getExport_price()));
        }

        if (invoiceType == 0) {
            holder.binding.tvTotalAmount.setText(Helper.formatVND(productInvoice.getQuantity() * productInvoice.getProduct().getImport_price()));
        } else {
            holder.binding.tvTotalAmount.setText(Helper.formatVND(productInvoice.getQuantity() * productInvoice.getProduct().getExport_price()));
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class InvoiceDetailViewHolder extends RecyclerView.ViewHolder {
        private final ItemInvoiceDetailBinding binding;

        public InvoiceDetailViewHolder(@NonNull ItemInvoiceDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
