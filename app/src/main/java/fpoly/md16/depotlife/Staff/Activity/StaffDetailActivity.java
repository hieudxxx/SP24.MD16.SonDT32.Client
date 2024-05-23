package fpoly.md16.depotlife.Staff.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.API;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiProduct;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiUser;
import fpoly.md16.depotlife.Product.Fragment.ProductEditFragment;
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
    private StaffResponse.User staff;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbStaffDetail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        token = "Bearer " + (String) Helper.getSharedPre(this, "token", String.class);

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });


        bundle = getIntent().getExtras();
        if (bundle != null) {
            staff = (StaffResponse.User) bundle.getSerializable("staff");
            if (staff != null) {
                getData();
            }
        }
    }


    private void getData() {
        ApiUser.apiUser.getStaffById(token, staff.getId()).enqueue(new Callback<StaffResponse.User>() {
            @Override
            public void onResponse(Call<StaffResponse.User> call, Response<StaffResponse.User> response) {
                if (response.isSuccessful()) {
                    staff = response.body();
                    Log.d("staff", "staff: "+staff);

                    if (staff != null) {
                        binding.tvId.setText(staff.getId() + "");
                        binding.tvName.setText(staff.getName());
                        binding.tvEmail.setText(staff.getEmail());
                        if (staff.getRole() == 0) {
                            roleText = "Nhân viên";
                        } else if (staff.getRole() == 1) {
                            roleText = "Quản lý";
                        } else {
                            roleText = "Không xác định";
                        }
                        binding.tvRole.setText(roleText);
                        binding.tvPhone.setText(String.valueOf(staff.getPhoneNumber()));

                        if (staff.getAvatar() == null) {
                            binding.imgAvt.setImageResource(R.drawable.images_default);
                        } else {
                            Picasso.get().load(API.URL_IMG + staff.getAvatar().replaceFirst("public", "")).into(binding.imgAvt);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StaffResponse.User> call, Throwable throwable) {
                Log.d("onFailure", "onFailure: " + throwable.getMessage());
                Toast.makeText(StaffDetailActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

}