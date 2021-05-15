package com.example.hidebook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidebook.MainActivity;
import com.example.hidebook.R;
import com.example.hidebook.ReplacerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpFragment extends Fragment {

    private EditText nameET, passET, cfpassET, emailET;

    private Button signupBT;

    private TextView signinTV;

    private String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])" +
            "(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+" +
            "=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";



    //Linh
    private FirebaseAuth auth;
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";



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

        //Linh
        auth = FirebaseAuth.getInstance();
    }



    private  void  clickListener(){
        signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new SignInFragment());
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

                //Bao
//                if(email.isEmpty() || email.matches(Patterns.EMAIL_ADDRESS.toString())){
//                    nameET.setError("Please input valid email");
//                    return;
//                }

                //Linh
                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){
                    nameET.setError("Please input valid email");
                    return;
                }

                //Linh
                createAccount(name, email, pass);


            }
        });

    }

    //Linh
    private void createAccount(String name, String email, String pass){
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();

                            UserProfileChangeRequest.Builder request = new UserProfileChangeRequest.Builder();
                            request.setDisplayName(name);

                            user.updateProfile(request.build());


                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Email verification link send",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            uploadUser(user, name, email);

                        }else {

                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error"+ exception, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    //Linh
    private void uploadUser(FirebaseUser user, String name, String email){

        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();

        Map<String , Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("profileImage", " ");
        map.put("uid", user.getUid());
        map.put("followers",list);
        map.put("following",list1);
        map.put("status"," ");
        //Bảo -
        map.put("search", name.toLowerCase());



        FirebaseFirestore.getInstance().collection("User").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            assert getActivity() != null;
                            startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
                            getActivity().finish();


                        }else {

                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error"+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}