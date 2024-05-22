package fpoly.md16.depotlife.Invoice.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ItemInvoiceBinding;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private ArrayList<Invoice> list;
    private final InterClickItemData interClickItemData;

    public interface InterClickItemData {
        void clickItem(Invoice invoice);
    }

    public void setData(List<Invoice> list) {
        this.list = (ArrayList<Invoice>) list;
        notifyDataSetChanged();
    }

    public InvoiceAdapter(InterClickItemData interClickItemData) {
        this.interClickItemData = interClickItemData;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvoiceBinding binding = ItemInvoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {

        Invoice invoice = list.get(position);
        if (invoice == null) return;

        holder.binding.tvIdInvoice.setText("HD" + invoice.getId());
        if (invoice.getInvoiceType() == 0) {
            holder.binding.tvTypeInvoice.setText("Hóa đơn nhập");
        } else {
            holder.binding.tvTypeInvoice.setText("Hóa đơn xuất");
        }

        holder.binding.tvDateCreated.setText(invoice.getDate_created().substring(0, 10));
        if (invoice.getStatusPayment() == 0) {
            holder.binding.tvStatusInvoice.setText("Chưa thanh toán");
        } else if (invoice.getStatusPayment() == 1) {
            holder.binding.tvStatusInvoice.setTextColor(Color.RED);
        } else {
            holder.binding.tvStatusInvoice.setText("Đã thanh toán");
        }
        if (invoice.getStatusPayment() == 2) {
            holder.binding.tvStatusInvoice.setText("Quá hạn");
        } else {
            holder.binding.tvStatusInvoice.setText("Đã xóa");
            holder.binding.tvStatusInvoice.setTextColor(Color.parseColor("#6AA84F"));
        }
        holder.binding.tvTotalInvoice.setText(Helper.formatVNDLong(invoice.getTotalAmount()));

        holder.itemView.setOnClickListener(view -> {
            interClickItemData.clickItem(invoice);
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private ItemInvoiceBinding binding;

        public InvoiceViewHolder(@NonNull ItemInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}