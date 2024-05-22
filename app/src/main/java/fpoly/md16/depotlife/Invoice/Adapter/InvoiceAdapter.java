package fpoly.md16.depotlife.Invoice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.databinding.ItemInvoiceBinding;
import fpoly.md16.depotlife.databinding.ItemLoadingBinding;

public class InvoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Invoice> list;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;
    private boolean isLoading;

    //    private final InterClickItemData interClickItemData;
//    public interface InterClickItemData {
//        void clickItem(Invoice invoice);
//    }
    public InvoiceAdapter(Context context, ArrayList<Invoice> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<Invoice> list) {
        this.list = (ArrayList<Invoice>) list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {
            ItemInvoiceBinding binding = ItemInvoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new InvoiceViewHolder(binding);
        } else {
            ItemLoadingBinding loadBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingViewHolder(loadBinding);
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            InvoiceViewHolder viewHolder = (InvoiceViewHolder) holder;

            Invoice invoice = list.get(position);
            if (invoice == null) return;

            viewHolder.binding.tvIdInvoice.setText("HD" + invoice.getId());
            if (invoice.getInvoiceType() == 0) {
                viewHolder.binding.tvTypeInvoice.setText("Hóa đơn nhập");
            } else {
                viewHolder.binding.tvTypeInvoice.setText("Hóa đơn xuất");
            }

            viewHolder.binding.tvDateCreated.setText(invoice.getDate_created().substring(0, 10));
            if (invoice.getStatusPayment() == 0) {
                viewHolder.binding.tvStatusInvoice.setText("Chưa thanh toán");
                viewHolder.binding.tvStatusInvoice.setTextColor(Color.YELLOW);
            } else if (invoice.getStatusPayment() == 1) {
                viewHolder.binding.tvStatusInvoice.setText("Đã thanh toán");
                viewHolder.binding.tvStatusInvoice.setTextColor(Color.GREEN);
            } else if (invoice.getStatusPayment() == 2) {
                viewHolder.binding.tvStatusInvoice.setText("Quá hạn");
                viewHolder.binding.tvStatusInvoice.setTextColor(Color.BLUE);
            } else if (invoice.getStatusPayment() == 3) {
                viewHolder.binding.tvStatusInvoice.setText("Đã xóa");
                viewHolder.binding.tvStatusInvoice.setTextColor(Color.RED);
            }
            viewHolder.binding.tvTotalInvoice.setText(Helper.formatVNDLong(invoice.getTotalAmount()));

            holder.itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("invoiceId", list.get(holder.getAdapterPosition()).getId());
                context.startActivity(new Intent(context, InvoiceActivity.class).putExtras(bundle));
//            interClickItemData.clickItem(invoice);
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

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private ItemInvoiceBinding binding;

        public InvoiceViewHolder(@NonNull ItemInvoiceBinding binding) {
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