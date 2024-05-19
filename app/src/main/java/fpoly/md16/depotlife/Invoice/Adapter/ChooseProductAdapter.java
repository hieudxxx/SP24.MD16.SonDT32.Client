package fpoly.md16.depotlife.Invoice.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

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
        // Initialize productInvoices list with same size as product list
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

        // Ensure the position is valid before updating the product invoice
        if (position != RecyclerView.NO_POSITION) {
            productInvoices.get(position).setProductId(product.getId());
        }

        holder.binding.tvName.setText(product.getProduct_name());
        holder.binding.tvInventory.setText(String.valueOf(product.getInventory()));
        holder.binding.tvExportPrice.setText(Helper.formatVND(product.getExport_price()));
        holder.binding.tvImportPrice.setText(Helper.formatVND(product.getImport_price()));
        Helper.setImgProduct(product.getImg(), holder.binding.img);

        if (valInvoiceType == 0) {
            holder.binding.tvTotalAmount.setText(String.valueOf(product.getImport_price()));
        } else {
            holder.binding.tvTotalAmount.setText(String.valueOf(product.getExport_price()));
        }

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
            quantity++;
            holder.binding.edQuantity.setText(String.valueOf(quantity));
            updateTotalAmount(holder, quantity, product);
            notifyProductInvoiceUpdated();
        });

        holder.binding.edExpiryDate.setOnClickListener(view -> showDatePickerDialog(holder.itemView.getContext(), holder.binding.edExpiryDate));

        holder.binding.edExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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

    public void showDatePickerDialog(Context context, final TextInputEditText editText) {
        // Get current date as default in the date picker
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    editText.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}
