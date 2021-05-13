package com.example.hidebook.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hidebook.R;
import com.example.hidebook.adapter.HomeAdapter;
import com.example.hidebook.model.HomeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {


    private RecyclerView recyclerView;
    HomeAdapter adapter;
    private List<HomeModel> list;

    //Linh
    private FirebaseUser user;
    DocumentReference reference;

    public Home() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        //Linh
        //reference = FirebaseFirestore.getInstance().collection("Posts").document(user.getUid());

        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

        //Linh
        loadDataFromFirestore();
    }
    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Linh
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    //Linh
    private void loadDataFromFirestore(){

        list.add(new HomeModel("Tung","11/05/2021","","","123456",10));
        list.add(new HomeModel("Bao","12/05/2021","","","341256",171));
        list.add(new HomeModel("Ngan","13/05/2021","","","134256",102));
        list.add(new HomeModel("Quang","14/05/2021","","","156234",13));
        list.add(new HomeModel("Phong","14/05/2021","","","156234",33));

//        CollectionReference reference = FirebaseFirestore.getInstance().collection("User")
//                .document(user.getUid())
//                .collection("Post Images");
//
//        reference.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//
//                if(error != null){
//                    Log.e("Error", error.getMessage());
//                    return;
//                }
//                for (QueryDocumentSnapshot snapshot : value){
//
//                    list.add(new HomeModel(snapshot.get("userName").toString(),
//                            snapshot.get("timestamp").toString(),
//                            snapshot.get("profileImage").toString(),
//                            snapshot.get("postImage").toString(),
//                            snapshot.get("uid").toString(),
//                            Integer.parseInt(snapshot.get("likeCount").toString())
//                            ));
//                }
//
//            }
//        });

        adapter.notifyDataSetChanged();
    }
}