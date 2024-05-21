package fpoly.md16.depotlife.Invoice.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

import fpoly.md16.depotlife.Category.Fragment.CategoryListFragment;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiInvoice;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiSupplier;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.Product.Fragment.ProductAddFragment;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.FragmentInvoiceEditBinding;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceEditFragment extends Fragment {
    private FragmentInvoiceEditBinding binding;
    private Bundle bundle;
    private Invoice invoice;

    private String token;

    private Uri imageUri;

    private MultipartBody.Part multipartBody;

    private int paymentStatus = 0;

    private String formattedDate = null;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    binding.imgSignature.setImageURI(imageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceEditBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            invoice = (Invoice) bundle.get("invoiceDetail");
        }
        token = "Bearer " + Helper.getSharedPre(getContext(), "token", String.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(view1 -> {
            requireActivity().finish();
        });

        initData();

        binding.tvSave.setOnClickListener(view12 -> {
            updateInvoice();
        });

    }

    private void updateInvoice() {
        String note = binding.edNotes.getText().toString();
        String term = binding.edTerms.getText().toString();
        String signtureName = binding.edSigName.getText().toString();

        if (signtureName.isEmpty() || imageUri.toString().isEmpty()){
            Toast.makeText(getContext(), "Chữ ký không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri != null) {
            multipartBody = Helper.getRealPathFile(getContext(), imageUri, "signature");
        }

        ApiInvoice.apiInvoice.update(token,
                Helper.createStringPart(note),
                Helper.createStringPart(term),
                Helper.createStringPart(signtureName),
                Helper.createStringPart(formattedDate),
                Helper.createIntPart(paymentStatus),
                multipartBody).enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if (response.isSuccessful()) {
                    SupplierFragment.isLoadData = true;
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(getActivity(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {

        String[] items_status_payment = new String[]{"Chưa thanh toán", "Đã thanh toán"};
        ArrayAdapter<String> adapter_status = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items_status_payment);
        binding.spnPaymentStatus.setAdapter(adapter_status);
        binding.spnPaymentStatus.setText(items_status_payment[invoice.getStatusPayment()],false);

        if (invoice.getStatusPayment() == 0) {
            binding.borderStatusPayment.setVisibility(View.VISIBLE);
            binding.borderDuedate.setVisibility(View.VISIBLE);
        }else {
            binding.borderStatusPayment.setVisibility(View.GONE);
            binding.borderDuedate.setVisibility(View.GONE);
        }

        binding.spnPaymentStatus.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                binding.borderDuedate.setVisibility(View.VISIBLE);
            } else {
                binding.borderDuedate.setVisibility(View.GONE);
            }
            paymentStatus = i;
        });

        binding.edDueDate.setOnClickListener(view -> {
            showDatePickerDialog(view.getContext(), binding.edDueDate);
        });

        binding.edNotes.setText(invoice.getNote());
        binding.edTerms.setText(invoice.getTerm());
        binding.edSigName.setText(invoice.getSignature_name());

        binding.idSignature.setOnClickListener(view -> {
            onRequestPermission();
        });

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
                        formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        editText.setText(formattedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void onRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Helper.openGallery(getActivity(), launcher);
            return;
        }
        if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Helper.openGallery(getActivity(), launcher);
        } else {
            String permission = (Manifest.permission.READ_EXTERNAL_STORAGE);
            requestPermissions(new String[]{permission}, 10);
        }
    }

}