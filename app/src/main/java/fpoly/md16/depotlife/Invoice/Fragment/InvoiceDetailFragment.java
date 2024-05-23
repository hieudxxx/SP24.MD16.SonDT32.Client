package fpoly.md16.depotlife.Invoice.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceDetailAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceDetailBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceDetailFragment extends Fragment {
    private FragmentInvoiceDetailBinding binding;
    private InvoiceDetailAdapter adapter;
    private String token;
    private int ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ID = bundle.getInt("invoiceId");
            token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(v -> {
            requireActivity().finish();
        });

    }

    private void setupAdapter(List<Invoice.ProductInvoice> list, Invoice invoice) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.rcvProductList.setLayoutManager(manager);
        adapter = new InvoiceDetailAdapter(invoice.getInvoiceType());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        binding.rcvProductList.addItemDecoration(dividerItemDecoration);


        binding.rcvProductList.setAdapter(adapter);
        adapter.setData(list);
    }

    private void setInit(Invoice invoice) {
        binding.tvIdInvoice.setText("Invoice No: #" + invoice.getId());
        binding.tvInvoiceDate.setText("Invoice Date: " + invoice.getDate_created().substring(0, 10));
        if (invoice.getInvoiceType() == 0) {
            binding.tvInvoiceType.setText("Invoice Import");
            binding.tvSupplierName.setText(invoice.getSupplier().getName());
            binding.tvSupplierPhone.setText(invoice.getSupplier().getPhone());
            binding.tvAddress.setText(invoice.getSupplier().getAddress());
            binding.tvCustomerName.setText("Warehouse Bắc Từ Liêm");
            binding.tvCustomerPhone.setText("0862850761");
            binding.tvCustomerEmail.setText("warehousesuport@gmail.com");
            binding.tvCustomerAddress.setText("Xuân Phương, Bắc Từ Liêm, Hà Nội");
        } else {
            binding.tvInvoiceType.setText("Invoice Export");
            binding.tvCustomerName.setText(invoice.getCustomer().getCustomerName());
            binding.tvCustomerPhone.setText(invoice.getCustomer().getCustomerPhone());
            binding.tvCustomerEmail.setText(invoice.getCustomer().getCustomerEmail());
            binding.tvCustomerAddress.setText(invoice.getCustomer().getAddress());
        }
        if (invoice.getStatusPayment() == 0) {
            if (invoice.getDueDate() != null) {
                binding.tvDueDate.setVisibility(View.VISIBLE);
                binding.tvDueDate.setText("Due Date: " + invoice.getDueDate());
            }
            binding.tvPaymentStatus.setText("Payment Status: NOT PAID");
            binding.tvPaymentStatus.setTextColor(Color.RED);
        } else {
            binding.tvDueDate.setVisibility(View.INVISIBLE);
            binding.tvPaymentStatus.setText("Payment Status: PAID");
            binding.tvPaymentStatus.setTextColor(Color.GREEN);
        }
        binding.tvDiscount.setText(invoice.getDiscount() + "%");
        binding.tvNotes.setText(invoice.getNote());
        binding.tvTerms.setText(invoice.getTerm());
        if (invoice.getSignature_img() == null)
            binding.imgSignature.setImageResource(R.drawable.add);
        else
            Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" + invoice.getSignature_img().replaceFirst("public", "storage")).into(binding.imgSignature);


        binding.tvNameSignature.setText(invoice.getSignature_name());
        double totalAmount = invoice.getTotalAmount();
        double discount = invoice.getDiscount();

// Calculate the total taxable amount
        double totalTaxable = totalAmount / 1.1 / (1 - discount / 100);

// Set the text to the calculated taxable amount, cast to an int
        binding.tvTotalTaxable.setText(Helper.formatVND((int) Math.ceil(totalTaxable)));
        binding.tvTotalAmount.setText(Helper.formatVND((int) invoice.getTotalAmount()));

        binding.imgEdit.setOnClickListener(view1 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("invoiceDetail", invoice));
        });

        binding.imgDelete.setOnClickListener(view -> {
            clickDeleteData(invoice);
        });

    }

    private void getData() {
        ApiInvoice.apiInvoice.getDetail("Bearer " + token, ID).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                Log.d("zzzzzzzzzzzzzzzz", "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setupAdapter(response.body().getProductInvoice(), response.body());
                        setInit(response.body());
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Không thể lấy dữ liệu danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickDeleteData(Invoice invoice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa hóa đơn (" + invoice.getId() + ") không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteInvoice(invoice.getId());
            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }

    private void deleteInvoice(int id) {
        ApiInvoice apiInvoice = ApiInvoice.apiInvoice;

// Gọi phương thức delete
        ApiInvoice.apiInvoice.delete("Bearer " + token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Lấy phản hồi JSON từ API
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        // Lấy thông báo từ phản hồi JSON
                        String message = jsonObject.getString("success");


                        // Hiển thị thông báo cho người dùng
                        alert(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        // Lấy phản hồi lỗi JSON từ API
                        String errorResponse = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorResponse);

                        // Lấy thông báo lỗi từ phản hồi JSON
                        String errorMessage = jsonObject.getString("error");
                        alertFail(errorMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý lỗi khi gọi API
                Log.e("API", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requireActivity().finish();
            }
        });
        builder.show();
    }

    private void alertFail(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}