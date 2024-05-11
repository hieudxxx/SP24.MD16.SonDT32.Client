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

        Helper.loadFragment(getSupportFragmentManager(), new InvoiceAddFragment(),null, R.id.frag_container_invoice);

    }
}