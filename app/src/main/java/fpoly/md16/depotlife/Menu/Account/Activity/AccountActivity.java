package fpoly.md16.depotlife.Menu.Account.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Menu.Account.Fragment.AccountFragment;
import fpoly.md16.depotlife.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Helper.loadFragment(getSupportFragmentManager(), new AccountFragment(), null, R.id.frag_container_account);

    }
}