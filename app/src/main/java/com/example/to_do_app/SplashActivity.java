package com.example.to_do_app.onboarding;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_do_app.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView txtLoading;
    private TextView txtPercent;

    private int currentProgress = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Durée totale de la splash (en ms)
    private static final int TOTAL_DURATION = 3000;
    // Intervalle de mise à jour (en ms)
    private static final int INTERVAL = 30;
    // Incrément par tick
    private static final int INCREMENT = 100 / (TOTAL_DURATION / INTERVAL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        progressBar = findViewById(R.id.progressBar);
        txtLoading  = findViewById(R.id.txtLoading);
        txtPercent  = findViewById(R.id.txtPercent); // ajoute cet id dans le XML

        progressBar.setProgress(0);

        startLoading();
    }

    private void startLoading() {
        handler.post(progressRunnable);
    }

    private final Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (currentProgress < 100) {
                currentProgress = Math.min(currentProgress + INCREMENT, 100);
                progressBar.setProgress(currentProgress);
                txtPercent.setText(currentProgress + "%");
                updateLoadingText(currentProgress);
                handler.postDelayed(this, INTERVAL);
            } else {
                // Chargement terminé → naviguer vers MainActivity
                goToMain();
            }
        }
    };

    private void updateLoadingText(int progress) {
        if (progress < 30) {
            txtLoading.setText("Initialisation...");
        } else if (progress < 60) {
            txtLoading.setText("Chargement des données...");
        } else if (progress < 90) {
            txtLoading.setText("Presque prêt...");
        } else {
            txtLoading.setText("Bienvenue !");
        }
    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // empêche le retour arrière vers la splash
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Eviter les fuites mémoire
        handler.removeCallbacks(progressRunnable);
    }
}
