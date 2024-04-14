package fpoly.md16.depotlife.Invoice.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ItemInvoiceBinding;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Invoice> list;
    private ArrayList<Invoice> mList;
    private FragmentManager fragmentManager;

    public InvoiceAdapter(Context context, ArrayList<Invoice> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.mList = list;
        this.fragmentManager = fragmentManager;
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

        holder.binding.tvIdInvoice.setText(list.get(position).getId() + "");
        if (list.get(position).getStatus() == 0) {
            holder.binding.tvStatusInvoice.setText("Phiếu tạm");
            holder.binding.tvStatusInvoice.setTextColor(Color.YELLOW);
        } else {
            holder.binding.tvStatusInvoice.setText("Hoàn thành");
            holder.binding.tvStatusInvoice.setTextColor(Color.GRAY);
        }
        holder.binding.tvIdUserInvoice.setText(list.get(position).getUser_id() + "");
        holder.binding.tvDateCreated.setText(list.get(position).getDate_created());
        if (list.get(position).getType() == 0) {
            holder.binding.tvTypeInvoice.setText("Phiếu nhập");
        } else {
            holder.binding.tvTypeInvoice.setText("Phiếu xuất");
        }
        holder.binding.tvTotalInvoice.setText(String.valueOf(list.get(position).getTotal()));
        holder.binding.tvTotalInvoice.setText(Helper.formatVND(list.get(position).getTotal()));
        holder.binding.tvTotalInvoice.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.tv_blue_bold));
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("invoice",list.get(holder.getAdapterPosition()));
            context.startActivity(new Intent(context, InvoiceActivity.class).putExtras(bundle));
        });

//        Double totalBill = invoice.getTotal_bill();
//        Locale locale = new Locale("vi", "VN");
//        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
//        String formatTotalBill = numberFormat.format(totalBill);
//        formatTotalBill = formatTotalBill.replaceAll("[^\\x00-\\x7F]", "");
//        holder.invoiceBinding.tvTotalBillItem.setText(formatTotalBill);

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
                if (TextUtils.isEmpty(strSearch)) {
                    list = mList;
                } else {
                    ArrayList<Invoice> listFilter = new ArrayList<>();
                    for (Invoice invoice : mList) {
                        if (invoice.getId().toLowerCase().contains(strSearch.toLowerCase())) {
                            listFilter.add(invoice);
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
                list = (ArrayList<Invoice>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private ItemInvoiceBinding binding;

        public InvoiceViewHolder(@NonNull ItemInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}