package com.example.hidebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidebook.R;
import com.example.hidebook.ReplacerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.hidebook.fragment.SignUpFragment.EMAIL_REGEX;


public class ForgotPasswordFragment extends Fragment {

    private  TextView signinTV;

    private EditText emailET;

    private Button recoverBT;

    private FirebaseAuth auth;


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();
    }

    private void init(View view){
        signinTV = view.findViewById(R.id.tv_signin);
        emailET = view.findViewById(R.id.et_email);
        recoverBT = view.findViewById(R.id.btn_recover);

        auth = FirebaseAuth.getInstance();
    }

    private void clickListener(){
        signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new SignInFragment());
            }
        });

        recoverBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString();

                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    emailET.setError("Input valid email");
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(),"Pass word reset email sent successfully",
                                            Toast.LENGTH_SHORT).show();
                                    emailET.setText("");
                                }else {
                                    String err = task.getException().getMessage();
                                    Toast.makeText(getContext(), "Error: " + err,
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });
    }
}