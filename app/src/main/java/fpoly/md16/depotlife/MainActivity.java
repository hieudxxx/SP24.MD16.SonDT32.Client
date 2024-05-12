package fpoly.md16.depotlife;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Invoice.Fragment.InvoiceFragment;
import fpoly.md16.depotlife.Menu.Fragment.MenuFragment;
import fpoly.md16.depotlife.Product.Fragment.ProductFragment;
import fpoly.md16.depotlife.Staff.Fragment.StaffFragment;
import fpoly.md16.depotlife.Statistic.StatisticFragment;
import fpoly.md16.depotlife.databinding.ActivityMainBinding;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    public static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Integer role = (Integer) Helper.getSharedPre(this, "role", Integer.class);



        if (role == 1){
            binding.bottomNav.show(1,true);
            binding.bottomNav.add(new MeowBottomNavigation.Model(1,R.drawable.statistic_bot_nav));
        }else {
            binding.bottomNav.show(2,true);
        }
        
        binding.bottomNav.add(new MeowBottomNavigation.Model(2,R.drawable.invoice_bot_nav));
        binding.bottomNav.add(new MeowBottomNavigation.Model(3,R.drawable.scan));
        binding.bottomNav.add(new MeowBottomNavigation.Model(4,R.drawable.product_bot_nav));
        binding.bottomNav.add(new MeowBottomNavigation.Model(5,R.drawable.menu_bot_nav));

        binding.bottomNav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                return null;
            }
        });
        binding.bottomNav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        Helper.loadFragment(getSupportFragmentManager(), new StatisticFragment(), null, R.id.frag_container_main);
                        break;

                    case 2:
                        Helper.loadFragment(getSupportFragmentManager(), new InvoiceFragment(), null, R.id.frag_container_main);
                        break;

                    case 4:
                        Helper.loadFragment(getSupportFragmentManager(), new ProductFragment(), null, R.id.frag_container_main);
                        break;

                    case 5:
                        Helper.loadFragment(getSupportFragmentManager(), new MenuFragment(), null, R.id.frag_container_main);
                        break;
                }

                return null;
            }
        });

    }


}