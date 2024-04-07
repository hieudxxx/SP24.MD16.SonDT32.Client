package fpoly.md16.depotlife.Product.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Product.Fragment.ProductAddFragment;
import fpoly.md16.depotlife.Product.Fragment.ProductDetailFragment;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityProductBinding;

public class ProductActivity extends AppCompatActivity {
    private ActivityProductBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            bundle.putSerializable("product", product);
            Helper.loadFragment(getSupportFragmentManager(), new ProductDetailFragment(), bundle, R.id.frag_container_product);
        } else {
            Helper.loadFragment(getSupportFragmentManager(), new ProductAddFragment(), null, R.id.frag_container_product);
        }
    }
}