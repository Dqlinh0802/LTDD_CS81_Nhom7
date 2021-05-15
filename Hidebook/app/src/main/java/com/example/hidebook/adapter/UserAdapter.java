package com.example.hidebook.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hidebook.MainActivity;
import com.example.hidebook.R;
import com.example.hidebook.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private List<Users> list;

    OnUserClicked onUserClicked;

    public UserAdapter(List<Users> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

<<<<<<< HEAD
        if (list.get(position).getUid().equals(user.getUid())) {
            holder.layout.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        } else {
=======
        if(list.get(position).getUid().equals(user.getUid())){

            holder.layout.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        }else {

>>>>>>> 818aec88eba5e667c013b7d937b54ce779865723
            holder.layout.setVisibility(View.VISIBLE);
        }

        holder.nameTV.setText(list.get(position).getName());
        holder.statusTV.setText(list.get(position).getStatus());

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_person)
                .timeout(6500)
                .into(holder.profileImage);

<<<<<<< HEAD

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClicked.onClicked(list.get(position).getUid());
            }
        });
=======
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClicked.onClicked( list.get(position).getUid());
            }
        });


>>>>>>> 818aec88eba5e667c013b7d937b54ce779865723

    }


<<<<<<< HEAD

=======
>>>>>>> 818aec88eba5e667c013b7d937b54ce779865723
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileImage;
        private TextView nameTV, statusTV;
        private RelativeLayout layout;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            nameTV = itemView.findViewById(R.id.nameTV);
            statusTV = itemView.findViewById(R.id.statusTV);
            layout = itemView.findViewById(R.id.relativeLayout);
        }

<<<<<<< HEAD


    }

    public void OnUserClicked (OnUserClicked onUserClicked){
        this.onUserClicked = onUserClicked;
    }

    public interface OnUserClicked{
        void onClicked(String uid);
=======
>>>>>>> 818aec88eba5e667c013b7d937b54ce779865723
    }


    public void OnUserClicked(OnUserClicked onUserClicked){
        this.onUserClicked = onUserClicked;
    }

    public interface OnUserClicked {
        void onClicked( String uid);
    }

}
