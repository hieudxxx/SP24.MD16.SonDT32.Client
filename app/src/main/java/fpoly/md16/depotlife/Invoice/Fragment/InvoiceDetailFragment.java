package fpoly.md16.depotlife.Invoice.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Invoice.Activity.InvoiceActivity;
import fpoly.md16.depotlife.Invoice.Adapter.InvoiceDetailAdapter;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.DialogCheckDeleteBinding;
import fpoly.md16.depotlife.databinding.FragmentInvoiceDetailBinding;

public class InvoiceDetailFragment extends Fragment {
    private FragmentInvoiceDetailBinding binding;
    private Invoice invoice;
    private InvoiceDetailAdapter adapter;
    private List<Invoice.ProductInvoice> list;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            invoice = (Invoice) bundle.get("invoice");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.imgBack.setOnClickListener(v -> {
            requireActivity().finish();
        });

        list = invoice.getProductInvoice();

        setInit(invoice);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        binding.rcvProductList.setLayoutManager(manager);
        adapter = new InvoiceDetailAdapter(invoice.getInvoiceType());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL);
        binding.rcvProductList.addItemDecoration(dividerItemDecoration);


        binding.rcvProductList.setAdapter(adapter);
        adapter.setData(list);

        binding.imgEdit.setOnClickListener(view1 -> {
            getContext().startActivity(new Intent(getContext(), InvoiceActivity.class).putExtra("invoiceDetail",invoice));
        });

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
            binding.tvInvoiceType.setText("Payment Status: PAID");
            binding.tvPaymentStatus.setTextColor(Color.GREEN);
        }
        binding.tvDiscount.setText(invoice.getDiscount() + "%");
        binding.tvNotes.setText(invoice.getNote());
        binding.tvTerms.setText(invoice.getTerm());
        Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" + invoice.getSignature_img().replaceFirst("public", "storage")).into(binding.imgSignature);
        binding.tvNameSignature.setText(invoice.getSignature_name());
        double totalAmount = invoice.getTotalAmount();
        double discount = invoice.getDiscount();

// Calculate the total taxable amount
        double totalTaxable = totalAmount / 1.1 / (1 - discount / 100);

// Set the text to the calculated taxable amount, cast to an int
        binding.tvTotalTaxable.setText(Helper.formatVND((int) Math.ceil(totalTaxable)));
        binding.tvTotalAmount.setText(Helper.formatVND((int) invoice.getTotalAmount()));
    }
}