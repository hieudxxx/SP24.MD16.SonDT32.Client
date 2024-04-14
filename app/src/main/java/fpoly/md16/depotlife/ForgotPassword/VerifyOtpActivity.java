package fpoly.md16.depotlife.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import fpoly.md16.depotlife.databinding.ActivityVerifyOtpBinding;

public class VerifyOtpActivity extends AppCompatActivity {

    private ActivityVerifyOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnVerifyOtp.setOnClickListener(v -> {
            Intent intent = new Intent(VerifyOtpActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
            Toast.makeText(VerifyOtpActivity.this, "TEST TEST", Toast.LENGTH_SHORT).show();
        });

    }
}