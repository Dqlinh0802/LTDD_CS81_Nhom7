package com.example.hidebook.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hidebook.R;
import com.example.hidebook.adapter.HomeAdapter;
import com.example.hidebook.model.HomeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Home extends Fragment {


    private RecyclerView recyclerView;
    HomeAdapter adapter;
    private List<HomeModel> list;
    //Linh
    private FirebaseUser user;


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

        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

        //Linh
        loadDataFromFirestore();

        adapter.OnPressed(new HomeAdapter.OnPressed() {
            @Override
            public void onLiked(int position, String id, String uid, List<String> likeList, boolean isChecked) {
                DocumentReference reference = FirebaseFirestore.getInstance().collection("User")
                        .document(uid)
                        .collection("Post Images")
                        .document(id);

                if (likeList.contains(user.getUid())){
                    likeList.remove(user.getUid()); //unlike
                }else {
                    likeList.add(user.getUid());    //like
                }

                Map<String, Object> map = new HashMap<>();
                map.put("likes", likeList);
                reference.update(map);
            }

            @Override
            public void onComment(int position, String id, String uid, String comment, LinearLayout commentLayout, EditText edt_comment) {

                if (comment.isEmpty() || comment.equals(" ")){
                    Toast.makeText(getContext(), "Can not send empty comment", Toast.LENGTH_SHORT).show();
                    return;
                }
                CollectionReference reference = FirebaseFirestore.getInstance().collection("User")
                        .document(uid)
                        .collection("Post Images")
                        .document(id)
                        .collection("Comments");
                String commentID = reference.document().getId();

                Map<String, Object> map = new HashMap<>();
                map.put("uid", user.getUid());
                map.put("comment", comment);
                map.put("commentID", commentID);
                map.put("postID", id);

                reference.document(commentID)
                        .set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    edt_comment.setText("");
                                    commentLayout.setVisibility(View.GONE);
                                }else {
                                    Toast.makeText(getContext(), "Failed to commnet: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }

            @Override
            public void setCommentCount(TextView textView) {

                Activity activity = getActivity();

                commentCount.observe((LifecycleOwner) activity, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (commentCount.getValue() == 0)
                        {
                            textView.setVisibility(View.GONE);
                        }else
                            textView.setVisibility(View.VISIBLE);

                        textView.setText("See all" + commentCount.getValue() + "comments");
                    }
                });



            }
        });
    }
    private MutableLiveData<Integer> commentCount = new MutableLiveData<>();
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

       final DocumentReference reference = FirebaseFirestore.getInstance().collection("User")
                .document(user.getUid());
//                .collection("Post Images");
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("User");

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("Error: ", error.getMessage());
                    return;
                }
                if (value == null)
                    return;

                List<String> uidList =(List<String>) value.get("following");

                if (uidList == null || uidList.isEmpty())
                    return;

                collectionReference.whereIn("uid", uidList)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null){
                                    Log.d("Error: ", error.getMessage());
                                }
                                if (value == null)
                                    return;
                                for (QueryDocumentSnapshot snapshot : value){
                                    snapshot.getReference().collection("Post Images")
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null){
                                                        Log.d("Error: ", error.getMessage());
                                                    }

                                                    if (value == null)
                                                        return;
                                                    list.clear();
                                                    for (QueryDocumentSnapshot snapshot : value){

                                                        if (!snapshot.exists())
                                                            return;

                                                        HomeModel model = snapshot.toObject(HomeModel.class);


                                                        list.add(new HomeModel(
                                                                model.getName(),
                                                                model.getProfileImage(),
                                                                model.getImageUrl(),
                                                                model.getUid(),
                                                                model.getDescription(),
                                                                model.getId(),
                                                                model.getTimestamp(),
                                                                model.getLikes()
                                                        ));
                                                        snapshot.getReference().collection("Comments").get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()){
                                                                            QuerySnapshot snapshots = task.getResult();

                                                                            int count = 0;

                                                                            for (QueryDocumentSnapshot snapshot : task.getResult()){
                                                                                count++;
                                                                            }
                                                                            commentCount.setValue(count);
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                }
                            }
                        });
            }
        });


    }
}