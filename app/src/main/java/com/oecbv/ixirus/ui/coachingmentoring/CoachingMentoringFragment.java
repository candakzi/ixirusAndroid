package com.oecbv.ixirus.ui.coachingmentoring;

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

public class CoachingMentoringFragment extends Fragment {

    private CoachingMentoringViewModel coachingMentoringViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        coachingMentoringViewModel = ViewModelProviders.of(this).get(CoachingMentoringViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coaching, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        coachingMentoringViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}