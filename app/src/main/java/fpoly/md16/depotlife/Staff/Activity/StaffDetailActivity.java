package fpoly.md16.depotlife.Staff.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiUser;
import fpoly.md16.depotlife.Product.Model.Product;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.Staff.Model.StaffResponse;
import fpoly.md16.depotlife.databinding.ActivityProductFilterBinding;
import fpoly.md16.depotlife.databinding.ActivityStaffDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffDetailActivity extends AppCompatActivity {
    private ActivityStaffDetailBinding binding;
    private Bundle bundle;
    private String roleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbStaffDetail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        bundle = getIntent().getExtras();

        if (bundle != null) {
            StaffResponse.User user = (StaffResponse.User) bundle.getSerializable("staff");
            if (user != null) {
                binding.tvName.setText(user.getName());
                binding.tvEmail.setText(user.getEmail());
                if (user.getRole() == 0){
                    roleText = "Nhân viên";
                }else if (user.getRole()== 1){
                    roleText = "Quản lý";
                }else {
                    roleText = "Không xác định";
                }
                binding.tvRole.setText(roleText);
                binding.tvPhone.setText(String.valueOf(user.getPhoneNumber()));
            }
        }
    }

}