package fpoly.md16.depotlife.Invoice.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.databinding.ItemCustomterSelectBinding;

public class AdapterCusSelect extends RecyclerView.Adapter<AdapterCusSelect.ViewHolder> {
    private ArrayList<Customer> list;
    private InterClickItemData interClickItemData;

    public interface InterClickItemData {
        void chooseItem(Customer Customer);
    }

    public void setData(List<Customer> list) {
        this.list = (ArrayList<Customer>) list;
        notifyDataSetChanged();
    }

    public AdapterCusSelect(InterClickItemData interClickItemData) {
        this.interClickItemData = interClickItemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomterSelectBinding binding = ItemCustomterSelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvName.setText(list.get(position).getCustomerName());

        holder.binding.tvStatus.setText(list.get(position).getStatus() == 1 ? "Online" : "Off");
        holder.binding.tvStatus.setTextColor(list.get(position).getStatus() == 1 ? Color.GREEN : Color.RED);

        holder.binding.tvPhone.setText(list.get(position).getCustomerPhone());
        holder.binding.tvTotalInvoice.setText(list.get(position).getTotalInvoices()+"");
        holder.binding.email.setText(list.get(position).getCustomerEmail());

        holder.itemView.setOnClickListener(view -> {
            interClickItemData.chooseItem(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCustomterSelectBinding binding;

        public ViewHolder(@NonNull ItemCustomterSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
