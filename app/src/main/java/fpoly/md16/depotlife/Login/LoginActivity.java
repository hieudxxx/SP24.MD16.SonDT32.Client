package fpoly.md16.depotlife.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import fpoly.md16.depotlife.ForgotPassword.ForgotPasswordActivity;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.MainActivity;
import fpoly.md16.depotlife.R;
import fpoly.md16.depotlife.databinding.ActivityLoginBinding;
import fpoly.md16.depotlife.databinding.DialogLoadingBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvForgot.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        onShowPass();

        binding.btnLogin.setOnClickListener(view -> {
            String username = binding.edtPass.getText().toString();
            String pass = binding.edtPass.getText().toString();

            if (username.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                Helper.isContainSpace(username, binding.edtUsername, binding.tvWarUsername);
                Helper.isContainSpace(pass, binding.edtPass, binding.tvWarPass);
            }
            onLoadingNavigate();

        });
    }

    private void onLoadingNavigate() {
        binding.btnLogin.setOnClickListener(v -> {
            DialogLoadingBinding dialogLoadingBinding = DialogLoadingBinding.inflate(LayoutInflater.from(this));
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setView(dialogLoadingBinding.getRoot());
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
    }

    private void onShowPass() {
        binding.imgHidden.setOnClickListener(v -> {
            Helper.onShowPass(binding.imgHidden, binding.edtPass);
        });
    }
}