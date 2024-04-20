package fpoly.md16.depotlife.Supplier.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Fragment.ProductAddFragment;
import fpoly.md16.depotlife.Product.Fragment.ProductDetailFragment;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Supplier.Fragment.DetailTabFragment;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierAddFragment;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierDetailFragment;
import fpoly.md16.depotlife.Supplier.Fragment.SupplierFragment;
import fpoly.md16.depotlife.Supplier.Model.Supplier;
import fpoly.md16.depotlife.databinding.ActivityProductBinding;
import fpoly.md16.depotlife.databinding.ActivitySupplierBinding;

public class SupplierActivity extends AppCompatActivity {
    private ActivitySupplierBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Supplier supplier = (Supplier) bundle.getSerializable("supplier");
            bundle.putSerializable("supplier", supplier);
//            Helper.loadFragment(getSupportFragmentManager(), new SupplierDetailFragment(), null, R.id.frag_container_supplier);
            Helper.loadFragment(getSupportFragmentManager(), new DetailTabFragment(), bundle, R.id.frag_container_supplier);
        } else {
            Helper.loadFragment(getSupportFragmentManager(), new SupplierFragment(), null, R.id.frag_container_supplier);
        }
    }
}