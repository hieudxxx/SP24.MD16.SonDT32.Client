//package fpoly.md16.depotlife.ForgotPassword;

package fpoly.md16.depotlife.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import fpoly.md16.depotlife.databinding.ActivityConfirmOtpBinding;

public class ConfirmOtpActivity extends AppCompatActivity {
    private ActivityConfirmOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
        binding.rltForgotPassMail.setOnClickListener(view -> {
            startActivity(new Intent(this, VerifyOtpActivity.class));
        });

        binding.rlForgotPwdPhone.setOnClickListener(view -> {
            startActivity(new Intent(this, VerifyOtpActivity.class));
        });
    }
}