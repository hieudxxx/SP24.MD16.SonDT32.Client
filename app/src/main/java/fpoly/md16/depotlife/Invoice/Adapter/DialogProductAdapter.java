package fpoly.md16.depotlife.Invoice.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ItemProductBinding;
import fpoly.md16.depotlife.databinding.ItemProductDialogBinding;
import fpoly.md16.depotlife.databinding.ItemSupplierBinding;

public class DialogProductAdapter extends RecyclerView.Adapter<DialogProductAdapter.ViewHolder> {
    private ArrayList<Product> list;
    private final InterClickItemData interClickItemData;

    private List<Product> listChooseProduct;

    public interface InterClickItemData {
        void chooseItem(Product product, Boolean aBoolean);
    }
    public void setData(List<Product> list) {
        this.list = (ArrayList<Product>) list;
        notifyDataSetChanged();
    }


    public DialogProductAdapter(InterClickItemData interClickItemData, List<Product> listChooseProduct) {
        this.interClickItemData = interClickItemData;
        this.listChooseProduct = listChooseProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductDialogBinding binding = ItemProductDialogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list.get(position) == null) return;

        holder.binding.tvName.setText(list.get(position).getProduct_name());
        if (list.get(position).getSupplier() != null) {
            holder.binding.tvSupplier.setText(list.get(position).getSupplier().getName());
        }
        if (list.get(position).getCategory() != null){
            holder.binding.tvCategories.setText(list.get(position).getCategory().getName());
        }
        holder.binding.tvInventory.setText(String.valueOf(list.get(position).getInventory()));
        holder.binding.tvExportPrice.setText(Helper.formatVND(list.get(position).getExport_price()));
        holder.binding.tvImportPrice.setText(Helper.formatVND(list.get(position).getImport_price()));
        if (list.get(position).getLocation() != null) {
            holder.binding.tvLocation.setText(list.get(position).getLocation().getCode());
        }else {
            holder.binding.tvLocation.setText("Không có vị trí");
        }
        Helper.setImgProduct(list.get(position).getImg(), holder.binding.img);

        for (Product val: listChooseProduct
             ) {
            if (list.get(position) == val){
                holder.binding.cbxProduct.setChecked(true);
            }
        }

        holder.itemView.setOnClickListener(view -> {
            holder.binding.cbxProduct.setChecked(!holder.binding.cbxProduct.isChecked());
            interClickItemData.chooseItem(list.get(position),holder.binding.cbxProduct.isChecked());
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
        private final ItemProductDialogBinding binding;

        public ViewHolder(@NonNull ItemProductDialogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
