package fpoly.md16.depotlife.Invoice.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceAddFragment;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceDetailFragment;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceEditFragment;
import fpoly.md16.depotlife.Invoice.Model.Invoice;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityInvoiceBinding;

public class InvoiceActivity extends AppCompatActivity {

    public ActivityInvoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Invoice invoice = (Invoice) getIntent().getSerializableExtra("invoice");
        Invoice invoiceDetail = (Invoice) getIntent().getSerializableExtra("invoiceDetail");
        int typeInvoice = getIntent().getIntExtra("type_invoice", 0);

        if (invoice != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("invoice", invoice);
            Helper.loadFragment(getSupportFragmentManager(), new InvoiceDetailFragment(), bundle, R.id.frag_container_invoice);
        } else if (invoiceDetail != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("invoiceDetail", invoiceDetail);
            Helper.loadFragment(getSupportFragmentManager(), new InvoiceEditFragment(), bundle, R.id.frag_container_invoice);
        } else {
            InvoiceAddFragment fragment = new InvoiceAddFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type_invoice", typeInvoice);
            Helper.loadFragment(getSupportFragmentManager(), fragment, bundle, R.id.frag_container_invoice);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }
}