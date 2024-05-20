package fpoly.md16.depotlife.Invoice.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceAddFragment;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityInvoiceBinding;

public class InvoiceActivity extends AppCompatActivity {

    public ActivityInvoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int typeInvoice = getIntent().getIntExtra("type_invoice", 0);

        InvoiceAddFragment fragment = new InvoiceAddFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type_invoice", typeInvoice);
        Helper.loadFragment(getSupportFragmentManager(), fragment, bundle, R.id.frag_container_invoice);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }
}