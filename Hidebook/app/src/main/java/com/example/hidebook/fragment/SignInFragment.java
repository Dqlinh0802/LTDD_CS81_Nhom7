package com.example.hidebook.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidebook.MainActivity;
import com.example.hidebook.R;
import com.example.hidebook.ReplacerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.hidebook.fragment.SignUpFragment.EMAIL_REGEX;


public class SignInFragment extends Fragment {

    //Bao
    private ImageButton changeLBT;
    private TextView tvSignUp, tvFgPass;
    //Linh
    private EditText emailET, passET;
    private Button loginBtn;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 1;
    //GoogleSignInClient mGoogleSignInClient;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadLocale();
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

        //Bao
        changeLBT = view.findViewById(R.id.translateIB);

        tvSignUp = view.findViewById(R.id.tv_signup);
        tvFgPass = view.findViewById(R.id.tv_forgotpass);
        //Linh
        emailET = view.findViewById(R.id.et_email);
        passET = view.findViewById(R.id.et_pass);
        loginBtn = view.findViewById(R.id.btn_signin);

        //Linh
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

    }


    private void showChangeLanguageDialog(){
        final String[] listItems = {"English", "Việt Nam"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.Choose_Language);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    setLocale("en");
                    getActivity().recreate();

                } else if (which == 1){
                    setLocale("vn");
                    getActivity().recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDial = mBuilder.create();
        mDial.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(getString(R.string.setting), Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.setting), Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }


    public void clickListener(){
        //Bao
        changeLBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new SignUpFragment());
            }
        });

        //Linh
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Linh
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();

                //Linh
                if(email.isEmpty() || !email.matches(EMAIL_REGEX)){

                    emailET.setError("Input valid email");
                    return;
                }
                //Linh
                if(pass.isEmpty() || pass.length() < 6){
                    passET.setError("Input 6 digit valid password ");
                    return;
                }

                //Linh
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    FirebaseUser user = auth.getCurrentUser();


                                    if (!user.isEmailVerified()){
                                        Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }

                                    sendUserToMainActivity();
                                }else {
                                    String exception = task.getException().getMessage();
                                    Toast.makeText(getContext(), "Error"+ task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        tvFgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ReplacerActivity) getActivity()).setFragment(new ForgotPasswordFragment());
            }
        });
    }



    //Linh
    public void sendUserToMainActivity(){

        if(getActivity() == null)
            return;

        startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }



    //Linh
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            updateUi(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    //Linh
    private void updateUi(FirebaseUser user){

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        Map<String , Object> map = new HashMap<>();
        map.put("name", account.getDisplayName());
        map.put("email", account.getEmail());
        map.put("profileImage", String.valueOf(account.getPhotoUrl()));
        map.put("uid", user.getUid());
        map.put("following", 0);
        map.put("followers", 0);
        map.put("status"," ");


        FirebaseFirestore.getInstance().collection("User").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            assert getActivity() != null;
                            sendUserToMainActivity();


                        }else {

                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error"+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}