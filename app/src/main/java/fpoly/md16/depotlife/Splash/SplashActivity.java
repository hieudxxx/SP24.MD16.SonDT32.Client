package fpoly.md16.depotlife.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


import fpoly.md16.depotlife.Login.LoginActivity;
import fpoly.md16.depotlife.R;

public class SplashActivity extends AppCompatActivity {

    private TextView textViewSplash;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textViewSplash = findViewById(R.id.textViewSplash);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        // Ẩn TextView ban đầu
         textViewSplash.setAlpha(0f);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            textViewSplash.animate()
                    .alpha(1f)
                    .translationY(150)
                    .setDuration(1500)
                    .setStartDelay(0);
        }, 0);

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                navigateLoginScreen();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }


    private void navigateLoginScreen() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}