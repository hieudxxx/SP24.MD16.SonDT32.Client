package fpoly.md16.depotlife.Invoice.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.databinding.ItemChooseProductBinding;

public class ChooseProductAdapter extends RecyclerView.Adapter<ChooseProductAdapter.ViewHolder> {
    private ArrayList<Product> list;
    private final InterClickItemData interClickItemData;
    private List<Invoice.ProductInvoice> productInvoices;
    private int valInvoiceType = 0;

    public interface InterClickItemData {
        void onProductInvoiceUpdated(List<Invoice.ProductInvoice> productInvoices);

        void removeItem(Product product);

    }

    public void setData(List<Product> list) {
        this.list = (ArrayList<Product>) list;
        productInvoices = new ArrayList<>(list.size());
        for (Product product : list) {
            productInvoices.add(new Invoice.ProductInvoice());
        }
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

        if (position != RecyclerView.NO_POSITION)
            productInvoices.get(position).setProduct_id(product.getId());

        holder.binding.tvName.setText(product.getProduct_name());
        holder.binding.tvInventory.setText(String.valueOf(product.getInventory()));
        if (valInvoiceType == 0){
            holder.binding.layoutExportPrice.setVisibility(View.GONE);
            holder.binding.layoutImportPrice.setVisibility(View.VISIBLE);
            holder.binding.tvImportPrice.setText(Helper.formatVND(product.getImport_price()));
        } else if (valInvoiceType == 1){
            holder.binding.layoutExportPrice.setVisibility(View.VISIBLE);
            holder.binding.layoutImportPrice.setVisibility(View.GONE);
            holder.binding.tvExportPrice.setText(Helper.formatVND(product.getExport_price()));
        }
        Helper.setImgProduct(product.getImg(), holder.binding.img);

        if (valInvoiceType == 0)
            holder.binding.tvTotalAmount.setText(String.valueOf(product.getImport_price()));
        else holder.binding.tvTotalAmount.setText(String.valueOf(product.getExport_price()));

        holder.binding.btnMinus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.binding.edQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                holder.binding.edQuantity.setText(String.valueOf(quantity));
                updateTotalAmount(holder, quantity, product);
                notifyProductInvoiceUpdated();
            }
        });

        holder.binding.btnPlus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.binding.edQuantity.getText().toString());
            if (valInvoiceType == 1) {
                if (quantity < product.getInventory()) {
                    quantity++;
                    holder.binding.edQuantity.setText(String.valueOf(quantity));
                    updateTotalAmount(holder, quantity, product);
                    notifyProductInvoiceUpdated();
                }
            } else {
                quantity++;
                holder.binding.edQuantity.setText(String.valueOf(quantity));
                updateTotalAmount(holder, quantity, product);
                notifyProductInvoiceUpdated();
            }

        });

        holder.binding.edExpiryDate.setOnClickListener(view -> Helper.onShowCaledar(holder.binding.edExpiryDate, view.getContext(), "%d-%02d-%02d"));

        holder.binding.edExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (position != RecyclerView.NO_POSITION) {
                    productInvoices.get(position).setExpiry(editable.toString());
                    notifyProductInvoiceUpdated();
                }
            }
        });

        holder.binding.edQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (position != RecyclerView.NO_POSITION) {
                    try {
                        int quantity = Integer.parseInt(editable.toString());
                        productInvoices.get(position).setQuantity(quantity);
                        updateTotalAmount(holder, quantity, product);
                        notifyProductInvoiceUpdated();
                    } catch (NumberFormatException e) {
                        // Handle case where editable is not a valid number
                    }
                }
            }
        });

        holder.binding.btnClose.setOnClickListener(view -> {
            interClickItemData.removeItem(list.get(position));
            notifyProductInvoiceUpdated();
        });
    }

    private void notifyProductInvoiceUpdated() {
        interClickItemData.onProductInvoiceUpdated(productInvoices);
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

    private void updateTotalAmount(ViewHolder holder, int quantity, Product product) {
        if (valInvoiceType == 0) {
            holder.binding.tvTotalAmount.setText(String.valueOf(quantity * product.getImport_price()));
        } else {
            holder.binding.tvTotalAmount.setText(String.valueOf(quantity * product.getExport_price()));
        }
    }
}