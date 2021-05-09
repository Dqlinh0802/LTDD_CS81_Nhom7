package com.example.hidebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hidebook.FragmentReplacerActivity;
import com.example.hidebook.R;


public class SignInFragment extends Fragment {

    private TextView tvSignUp;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener();

    }

    public void init(View view){
        tvSignUp = view.findViewById(R.id.tv_signup);
    }

    public void clickListener(){
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new SignUpFragment());
            }
        });
    }


}