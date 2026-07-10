package com.example.to_do_app.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.to_do_app.activities.SignUpActivity;
import com.example.to_do_app.R;
import com.example.to_do_app.adapters.OnboardingAdapter;

import java.util.Arrays;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager2 ;
    private Button btnNext ;
    private TextView btnSkip ;
    private OnboardingAdapter onboardingAdapter ;
    private View dot1;
    private View dot2;
    private View dot3;

    private static final List<OnboardingItem> ITEMS = Arrays.asList(
            new OnboardingItem(
                    R.drawable.task_manage,
                    "Manage your tasks easily",
                    "Create, organize and track your daily tasks in one place."
            ),

            new OnboardingItem(
                    R.drawable.calendrer,
                    "Set priorities and deadlines",
                    "Stay organized by setting priorities and due dates."
            ),

            new OnboardingItem(
                    R.drawable.reminder,
                    "Never miss a task",
                    "Receive smart reminders and stay productive every day."
            )
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();
        setupVoewPager2();
        setupClickListeners();
        setupPageChangeCallback();

    }

    private void initViews() {
        viewPager2 = findViewById(R.id.viewPager) ;
        btnNext = findViewById(R.id.btn_next) ;
        btnSkip = findViewById(R.id.btn_skip) ;

        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
    }

    private void setupVoewPager2() {
        OnboardingAdapter adapter = new OnboardingAdapter(ITEMS) ;
        viewPager2.setAdapter(adapter);
    }

    private void setupClickListeners() {
        btnNext.setOnClickListener(v ->{
            int currentPage = viewPager2.getCurrentItem();
            if(currentPage < ITEMS.size() - 1){
                viewPager2.setCurrentItem(currentPage + 1 );
            }else{
                openLogin();
            }
        }) ;

        btnSkip.setOnClickListener(v ->{
            viewPager2.setCurrentItem(ITEMS.size() -1);
        });
    }

    private void setupPageChangeCallback(){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                updateDots(position);
                updateButtons(position);
            }

        });

    }


    private void updateDots(int position) {

        dot1.setBackgroundResource(R.drawable.dot_inactive);
        dot2.setBackgroundResource(R.drawable.dot_inactive);
        dot3.setBackgroundResource(R.drawable.dot_inactive);

        switch (position) {

            case 0:
                dot1.setBackgroundResource(R.drawable.dot_active);
                break;

            case 1:
                dot2.setBackgroundResource(R.drawable.dot_active);
                break;

            case 2:
                dot3.setBackgroundResource(R.drawable.dot_active);
                break;
        }

    }

    private void updateButtons(int position) {

        if (position == ITEMS.size() - 1) {

            btnNext.setText("Get Started");
            btnSkip.setVisibility(View.GONE);

        } else {

            btnNext.setText("Next");
            btnSkip.setVisibility(View.VISIBLE);

        }

    }

    private void openLogin() {

        Intent intent = new Intent(OnboardingActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();

    }
}
