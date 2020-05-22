package com.oecbv.ixirus.ui.myPeers;

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

public class MyPeersFragment extends Fragment {

    private MyPeersViewModel myPeersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPeersViewModel =
                ViewModelProviders.of(this).get(MyPeersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_peers, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        myPeersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}