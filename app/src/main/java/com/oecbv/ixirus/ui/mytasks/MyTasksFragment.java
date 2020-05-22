package com.oecbv.ixirus.ui.mytasks;

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

public class MyTasksFragment extends Fragment {

    private MyTasksViewModel myTasksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myTasksViewModel =
                ViewModelProviders.of(this).get(MyTasksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_tasks, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        myTasksViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}