package fpoly.md16.depotlife.Invoice.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Fragment.ProductAddFragment;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.FragmentInvoiceEditBinding;

public class InvoiceEditFragment extends Fragment {
    private FragmentInvoiceEditBinding binding;
    private Bundle bundle;
    private Invoice invoice;

    private int paymentStatus = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceEditBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            invoice = (Invoice) bundle.get("invoiceDetail");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        initData();
        
    }

    private void initData() {
        String[] items_status_payment = new String[]{"Chưa thanh toán", "Đã thanh toán"};
        ArrayAdapter<String> adapter_status = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items_status_payment);
        binding.spnPaymentStatus.setAdapter(adapter_status);

        binding.spnPaymentStatus.setText(items_status_payment[invoice.getStatusPayment()]);

        binding.spnPaymentStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    binding.borderDuedate.setVisibility(View.VISIBLE);
                }else{
                    binding.borderDuedate.setVisibility(View.GONE);
                }
                paymentStatus = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.edDueDate.setOnClickListener(view -> {
            showDatePickerDialog(view.getContext(),binding.edDueDate);
        });

        binding.edNotes.setText(invoice.getNote());
        binding.edTerms.setText(invoice.getTerm());
        binding.edSigName.setText(invoice.getSignature_name());

        Picasso.get().load("https://warehouse.sinhvien.io.vn/public/" + invoice.getSignature_img().replaceFirst("public", "storage")).into(binding.imgSignature);
    }

    public void showDatePickerDialog(Context context, final TextInputEditText editText) {
        // Lấy ngày hiện tại làm ngày mặc định trong bộ chọn
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Tạo và hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        editText.setText(formattedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

}