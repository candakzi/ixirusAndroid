package com.oecbv.ixirus.ui.developmentplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.oecbv.ixirus.R;

public class DevelopmentPlanFragment extends Fragment {

    private DevelopmentPlanViewModel developmentPlanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        developmentPlanViewModel =
                ViewModelProviders.of(this).get(DevelopmentPlanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_development_plan, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        developmentPlanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}