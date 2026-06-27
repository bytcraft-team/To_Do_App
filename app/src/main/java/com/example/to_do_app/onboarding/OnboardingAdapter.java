package com.example.to_do_app.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.to_do_app.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItems ;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems){
        this.onboardingItems = onboardingItems ;
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder{

        ImageView img ;
        TextView title ;
        TextView  description ;
        public OnboardingViewHolder(View itemView){
            super(itemView);

             img = itemView.findViewById(R.id.onboarding_image) ;
             title = itemView.findViewById(R.id.onboarding_title) ;
             description = itemView.findViewById(R.id.onboarding_description) ;

        }
    }

    @Override
    public OnboardingViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.onboarding_item , parent , false) ;
       return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnboardingViewHolder holder , int position){
        OnboardingItem item = onboardingItems.get(position) ;
        holder.img.setImageResource(item.getImage());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size() ;
    }
}
