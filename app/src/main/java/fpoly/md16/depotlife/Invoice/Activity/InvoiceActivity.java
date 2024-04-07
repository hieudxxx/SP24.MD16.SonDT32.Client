package fpoly.md16.depotlife.Invoice.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceAddFragment;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceDetailFragment;
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

        Bundle bundle = getIntent().getExtras();
        int type = getIntent().getIntExtra("type", 2);
        if (type == 2) {
            if (bundle != null) {
                Invoice invoice = (Invoice) bundle.getSerializable("invoice");
                bundle.putSerializable("invoice", invoice);
                Helper.loadFragment(getSupportFragmentManager(), new InvoiceDetailFragment(), bundle, R.id.frag_container_invoice);
            }
        } else {
            bundle = new Bundle();
            bundle.putInt("type", type);
            Helper.loadFragment(getSupportFragmentManager(), new InvoiceAddFragment(), bundle, R.id.frag_container_invoice);
        }
    }
}