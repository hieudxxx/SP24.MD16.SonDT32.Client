package fpoly.md16.depotlife.ForgotPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.databinding.ActivityForgotPasswordBinding;
import fpoly.md16.depotlife.databinding.DialogCheckUsernameBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(v -> finish());

        binding.btnReset.setOnClickListener(view -> {
            String username = binding.edtUsername.getText().toString();
            if (username.isEmpty()) {
                Toast.makeText(this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                Helper.isContainSpace(username, binding.tvWarUsername);
                DialogCheckUsernameBinding dialog_binding = DialogCheckUsernameBinding.inflate(LayoutInflater.from(this));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dialog_binding.getRoot());
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog_binding.btnRetry.setOnClickListener(view1 -> {
                    dialog.cancel();
                    startActivity(new Intent(ForgotPasswordActivity.this, ConfirmOtpActivity.class));
                });
            }
        });
    }
}