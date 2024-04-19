package fpoly.md16.depotlife.Login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import fpoly.md16.depotlife.ForgotPassword.ForgotPasswordActivity;
import fpoly.md16.depotlife.Helper.Helper;
import fpoly.md16.depotlife.Helper.Interfaces.Api.ApiUser;
import fpoly.md16.depotlife.MainActivity;
import fpoly.md16.depotlife.Staff.Model.UserResponse;
import fpoly.md16.depotlife.databinding.ActivityLoginBinding;
import fpoly.md16.depotlife.databinding.DialogLoadingBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static ActivityLoginBinding binding;
    private UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isCheck = (boolean) Helper.getSharedPre(LoginActivity.this, "isRemember", Boolean.class);
        if (isCheck) {
            binding.edtUsername.setText((String) Helper.getSharedPre(LoginActivity.this, "email", String.class));
            binding.edtPass.setText((String) Helper.getSharedPre(LoginActivity.this, "password", String.class));
            binding.cbRemember.setChecked(true);
        }

        onShowPass();

        binding.tvForgot.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        binding.btnLogin.setOnClickListener(view -> {
            Map<String, Object> data = new HashMap<>();

            String username = binding.edtPass.getText().toString().trim();
            String pass = binding.edtPass.getText().toString().trim();

            data.put("isRemember", binding.cbRemember.isChecked());

            if (username.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Hãy nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            } else {
                userResponse = new UserResponse("doxuanhieu13012003@gmail.com", "Hieudeptrai102", "12121212");

                ApiUser.apiUser.login(userResponse).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.d("onResponse_login", "response_code: " + response.code());
                        if (response.isSuccessful()) {
                            UserResponse userResponse = response.body();
//                            UserResponse.User user = userResponse.getUser();
                            Log.d("onResponse_login", "userResponse: " + userResponse.toString());

                            Log.d("token", "token: "+userResponse.getToken());
                            data.put("token",userResponse.getToken());
                            data.put("id", userResponse.getUser().getId());
                            data.put("name", userResponse.getUser().getName());
                            data.put("phone_number", userResponse.getUser().getPhoneNumber());
                            data.put("role", userResponse.getUser().getRole());
                            data.put("avatar", userResponse.getUser().getAvatar());
                            data.put("email", userResponse.getUser().getEmail());
//                            data.put("password", pass);
                            data.put("password", "Duylam@2003");
                            data.put("birthday", userResponse.getUser().getBirthday());
                            data.put("status", userResponse.getUser().getStatus());

                            Helper.saveSharedPre(getApplicationContext(), data);
                            onLoadingNavigate();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Log.d("onResponse", "connect_failed: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable throwable) {
                        Log.d("onFailure_login", "connect_failed: " + throwable.getMessage());
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                });


            }
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

        });
    }

    private void onShowPass() {
        binding.imgHidden.setOnClickListener(v -> {
            Helper.onShowPass(binding.imgHidden, binding.edtPass);
        });
    }
}