package fpoly.md16.depotlife.Statistic;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.databinding.ItemLoSpBinding;

public class ExpAdapter extends RecyclerView.Adapter<ExpAdapter.ExpViewHolder> {
    private List<InventoryModel.Expiry> list = new ArrayList<>();

    public void setData(List<InventoryModel.Expiry> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public ExpAdapter() {
    }

    @NonNull
    @Override
    public ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLoSpBinding binding = ItemLoSpBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ExpViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ExpViewHolder holder, int position) {
        InventoryModel.Expiry expiry = list.get(position);
        if (expiry == null) return;

        holder.binding.tvIdLo.setText(String.valueOf(position+1));
        holder.binding.tvTonKho.setText("Sl: "+expiry.getTotalQuantity());
        holder.binding.tvExp.setText(expiry.getExpiryDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExpViewHolder extends RecyclerView.ViewHolder {
        private ItemLoSpBinding binding;

        public ExpViewHolder(@NonNull ItemLoSpBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}