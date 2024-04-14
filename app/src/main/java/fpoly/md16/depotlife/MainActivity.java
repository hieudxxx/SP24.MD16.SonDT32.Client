package fpoly.md16.depotlife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceFragment;
import fpoly.md16.depotlife.Menu.Fragment.MenuFragment;
import fpoly.md16.depotlife.Product.Fragment.ProductFragment;
import fpoly.md16.depotlife.Statistic.StatisticFragment;
import fpoly.md16.depotlife.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Helper.loadFragment(getSupportFragmentManager(), new StatisticFragment(), null, R.id.frag_container_main);
        binding.botNav.setBackground(null);

        binding.botNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bot_nav_statistic)
                Helper.loadFragment(getSupportFragmentManager(), new StatisticFragment(), null, R.id.frag_container_main)
                        ;
            if (item.getItemId() == R.id.bot_nav_invoice)
                Helper.loadFragment(getSupportFragmentManager(), new InvoiceFragment(), null, R.id.frag_container_main)
                        ;
            if (item.getItemId() == R.id.bot_nav_product)
                Helper.loadFragment(getSupportFragmentManager(), new ProductFragment(), null, R.id.frag_container_main)
                        ;
            if (item.getItemId() == R.id.bot_nav_menu) {
                Helper.loadFragment(getSupportFragmentManager(), new MenuFragment(), null, R.id.frag_container_main)
                ;
            }
            return true;
        });

    }

}