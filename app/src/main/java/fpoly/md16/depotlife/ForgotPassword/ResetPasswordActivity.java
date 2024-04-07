package fpoly.md16.depotlife.ForgotPassword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.widget.Toast;

import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityResetPasswordBinding;
import fpoly.md16.depotlife.databinding.DialogSuccessBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        binding.imgHiddenPass.setOnClickListener(v -> {
            Helper.onShowPass(binding.imgHiddenPass, binding.edtPass);
        });

        binding.imgHiddenRepass.setOnClickListener(v -> {
            Helper.onShowPass(binding.imgHiddenRepass, binding.edtRepass);
        });

        binding.btnResetForgotPwd.setOnClickListener(v1 -> {
            DialogSuccessBinding dialog_binding = DialogSuccessBinding.inflate(LayoutInflater.from(this));
            AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
            builder.setView(dialog_binding.getRoot());
            AlertDialog dialog = builder.create();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dialog_binding.imgClose.setOnClickListener(v -> {
                dialog.cancel();
            });
            dialog_binding.btnContinue.setOnClickListener(v -> {
                dialog.cancel();
                Toast.makeText(ResetPasswordActivity.this, "OK", Toast.LENGTH_SHORT).show();
            });
        });
    }
}