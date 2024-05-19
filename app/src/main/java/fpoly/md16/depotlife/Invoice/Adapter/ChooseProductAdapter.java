package fpoly.md16.depotlife.Invoice.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemChooseProductBinding;
import fpoly.md16.depotlife.databinding.ItemProductDialogBinding;

public class ChooseProductAdapter extends RecyclerView.Adapter<ChooseProductAdapter.ViewHolder> {
    private ArrayList<Product> list;
    private final InterClickItemData interClickItemData;

    private Invoice.ProductInvoice productInvoice;

    private int Quantity = 1;

    private int valInvoiceType = 0;

    public interface InterClickItemData {
        void ProductInvoice(Invoice.ProductInvoice productInvoice);
    }
    public void setData(List<Product> list) {
        this.list = (ArrayList<Product>) list;
        notifyDataSetChanged();
    }


    public ChooseProductAdapter(InterClickItemData interClickItemData, int valInvoiceType) {
        this.interClickItemData = interClickItemData;
        this.valInvoiceType = valInvoiceType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChooseProductBinding binding = ItemChooseProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        if (product == null) return;

        Quantity = Integer.parseInt(holder.binding.edQuantity.getText().toString());

        Invoice.ProductInvoice productInvoice = new Invoice.ProductInvoice(); // Khởi tạo mới cho mỗi sản phẩm
        productInvoice.setProductId(product.getId());

        holder.binding.tvName.setText(product.getProduct_name());
        holder.binding.tvInventory.setText(String.valueOf(product.getInventory()));
        holder.binding.tvExportPrice.setText(Helper.formatVND(product.getExport_price()));
        holder.binding.tvImportPrice.setText(Helper.formatVND(product.getImport_price()));
        Helper.setImgProduct(product.getImg(), holder.binding.img);

        holder.binding.btnMinus.setOnClickListener(view -> {
            if (Quantity > 1){
                Quantity--;
                holder.binding.edQuantity.setText(String.valueOf(Quantity));
                productInvoice.setQuantity(Quantity);
                interClickItemData.ProductInvoice(productInvoice); // Gửi dữ liệu khi có thay đổi
            }
        });

        holder.binding.btnPlus.setOnClickListener(view -> {
            Quantity++;
            holder.binding.edQuantity.setText(String.valueOf(Quantity));
            productInvoice.setQuantity(Quantity);
            interClickItemData.ProductInvoice(productInvoice); // Gửi dữ liệu khi có thay đổi
        });

        holder.binding.edExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productInvoice.setExpiry(s.toString());
                interClickItemData.ProductInvoice(productInvoice); // Gửi dữ liệu khi có thay đổi
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (valInvoiceType == 0){
            holder.binding.tvTotalAmount.setText(String.valueOf(Quantity * product.getImport_price()));
        }else{
            holder.binding.tvTotalAmount.setText(String.valueOf(Quantity * product.getExport_price()));
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChooseProductBinding binding;

        public ViewHolder(@NonNull ItemChooseProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
