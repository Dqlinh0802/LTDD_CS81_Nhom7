package com.example.hidebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hidebook.FragmentReplacerActivity;
import com.example.hidebook.R;


public class SignUpFragment extends Fragment {

    private EditText nameET, passET, cfpassET, emailET;

    private Button signupBT;

    private TextView signinTV;

    private String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])" +
            "(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+" +
            "=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        //





        clickListener();
    }
    private void init(View view){
        nameET = view.findViewById(R.id.et_name);
        passET = view.findViewById(R.id.et_pass);
        cfpassET = view.findViewById(R.id.et_cfpass);
        emailET = view.findViewById(R.id.et_email);
        //
        signupBT = view.findViewById(R.id.btn_signup);
        //
        signinTV = view.findViewById(R.id.tv_signin);
    }



    private  void  clickListener(){
        signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentReplacerActivity) getActivity()).setFragment(new SignInFragment());
            }
        });

        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameET.getText().toString();
                String pass = passET.getText().toString();
                String cfpass = cfpassET.getText().toString();
                String email = emailET.getText().toString();

                if(name.isEmpty() || name.equals(" ")){
                    nameET.setError("Please input valid name");
                    return;
                }
                if(pass.isEmpty() || pass.length() < 6 || pass.matches(regexPassword)){
                    passET.setError("Please input valid password");
                    return;
                }
                if(!pass.equals(cfpass)){
                    cfpassET.setError("Password not match");
                    return;
                }
                if(email.isEmpty() || email.matches(Patterns.EMAIL_ADDRESS.toString())){
                    nameET.setError("Please input valid email");
                    return;
                }
            }
        });

    }
}