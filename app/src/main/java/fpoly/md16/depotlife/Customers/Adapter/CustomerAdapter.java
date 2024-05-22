package fpoly.md16.depotlife.Customers.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Customers.CustomerActivity;
import fpoly.md16.depotlife.Customers.Model.Customer;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiCustomers;
import fpoly.md16.depotlife.Helper.Interfaces.onClickListener.onItemRcvClick;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.BottomsheetcustomerBinding;
import fpoly.md16.depotlife.databinding.ItemCustomerBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements Filterable {
    Context context;
    List<Customer> customerList;
    List<Customer> oldcustomerList;


    private String token;
    private onItemRcvClick onItemRcvClick;

    public CustomerAdapter(Context context, List<Customer> customerList, String token, onItemRcvClick onItemRcvClick) {
        this.context = context;
        this.customerList = customerList;
        this.oldcustomerList = customerList;
        this.token = token;
        this.onItemRcvClick = onItemRcvClick;
    }

    public void setData(List<Customer> list) {
        this.customerList = (ArrayList<Customer>) list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomerBinding customerBinding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomerViewHolder(customerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customerList.get(position);


        if (customer.getAvatar() == null) {
            holder.customerBinding.imgAvt.setImageResource(R.drawable.images_default);
        } else {
            Picasso.get().load(API.URL_IMG + customer.getAvatar().replaceFirst("public", "")).into(holder.customerBinding.imgAvt);
        }

        holder.customerBinding.tvName.setText(customer.getCustomerName());
        holder.customerBinding.tvPhone.setText(customer.getCustomerPhone());
        holder.customerBinding.tvEmail.setText(customer.getCustomerEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(customer);
            }
        });

    }

    public void showBottomSheet(Customer customer) {
        BottomsheetcustomerBinding binding;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bottomsheetcustomer, null);
        binding = BottomsheetcustomerBinding.inflate(inflater, view, false);

        binding.tvName.setText(customer.getCustomerName());
        binding.tvPhone.setText(customer.getCustomerPhone());
        binding.tvEmail.setText(customer.getCustomerEmail());
        binding.tvQtyInvoice.setText(customer.getInvoiceQuantity() + "");
        binding.tvDebt.setText("12222");
        binding.tvPayment.setText(customer.getTotalInvoices() + "");

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemRcvClick != null){
                    bottomSheetDialog.cancel();
                    onItemRcvClick.onClick(customer);
                }
            }

        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.onCheckdeleteDialog(context, () -> {
                    ApiCustomers.API_CUSTOMERS.delete(token, customer.getId()).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            if (response.isSuccessful() || response.code() == 200) {
                                Toast.makeText(context, "Xóa khách hàng thành công!", Toast.LENGTH_SHORT).show();
                                customerList.remove(customer);
                                CustomerActivity.isLoadData = true;
                                notifyDataSetChanged();
                                bottomSheetDialog.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable throwable) {
                            Log.d("onFailure", "onFailure: " + throwable.getMessage());
                            Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (customerList != null) {
            return customerList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    customerList = oldcustomerList;
                } else {
                    List<Customer> list = new ArrayList<>();
                    for (Customer customer : oldcustomerList) {
                        if (customer.getCustomerName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(customer);
                        }
                    }
                    customerList = list;
                }

                FilterResults results = new FilterResults();
                results.values = customerList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                customerList = (List<Customer>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        ItemCustomerBinding customerBinding;

        public CustomerViewHolder(@NonNull ItemCustomerBinding customerBinding) {
            super(customerBinding.getRoot());
            this.customerBinding = customerBinding;


        }
    }
}
