package com.oecbv.ixirus.ui.quizes;

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

public class QuizesFragment extends Fragment {

    private QuizesViewModel quizesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quizesViewModel =
                ViewModelProviders.of(this).get(QuizesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quizes, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        quizesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}