package com.example.hidebook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hidebook.R;
import com.example.hidebook.model.HomeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private List<HomeModel> list;
    OnPressed onPressed;
    Context context;



    public HomeAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items,parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        holder.tv_userName.setText(list.get(position).getName());
        holder.tv_time.setText(""+ list.get(position).getTimestamp());

//        List<String> likeList = list.get(position).getLikes();
//        int count = likeList.size();
//        if(count == 0)
//        {
//            holder.tv_likeCount.setVisibility(View.INVISIBLE);
//
//        }else if(count == 1) {
//            holder.tv_likeCount.setText(count + " like");
//        }else {
//            holder.tv_likeCount.setText(count + " likes");
//        }

        //holder.cb_like.setChecked(likeList.contains(user.getUid()));

        holder.tv_description.setText(list.get(position).getDescription());

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_person)
                .timeout(6500)
                .into(holder.profileImage);

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getImageUrl())
                .placeholder(new ColorDrawable(color))
                .timeout(7000)
                .into(holder.imageView);
        holder.clickListener(position,
                list.get(position).getId(),
                list.get(position).getName(),
                list.get(position).getUid(),
                list.get(position).getLikes()
        );

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnPressed{
        void onLiked(int position, String id, String uid, List<String>likeList, boolean isChecked);
        void onComment(int position, String id, String uid, String comment, LinearLayout commentLayout, EditText edt_comment);

        void setCommentCount(TextView textView);
    }
    public void OnPressed(OnPressed onPressed){
        this.onPressed = onPressed;
    }
    class HomeHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileImage;
        private TextView tv_userName, tv_time, tv_likeCount,tv_description, tv_comment;
        private ImageView imageView;
        private CheckBox cb_like;
        private ImageButton btn_comment, btn_share, btn_commentSend;
        private EditText edt_comment;
        LinearLayout commentLayout;
        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            imageView = itemView.findViewById(R.id.imageView);
            tv_userName = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_likeCount = itemView.findViewById(R.id.tv_likeCount);
            cb_like = itemView.findViewById(R.id.btn_like);
            btn_comment = itemView.findViewById(R.id.btn_comment);
            btn_share = itemView.findViewById(R.id.btn_share);
            tv_description = itemView.findViewById(R.id.tv_desc);
            edt_comment = itemView.findViewById(R.id.edt_comment);
            btn_commentSend = itemView.findViewById(R.id.btn_commentSend);
            commentLayout = itemView.findViewById(R.id.commentLayout);
            tv_comment = itemView.findViewById(R.id.tv_comment);

            onPressed.setCommentCount(tv_comment);
        }

        public void clickListener(final int position, final String id, String name, String uid, List<String> likes) {

            btn_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentLayout.getVisibility() == View.GONE){
                        commentLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {
                onPressed.onLiked(position, id, uid, likes, isChecked);
            });

            btn_commentSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String comment = edt_comment.getText().toString();
                    onPressed.onComment(position, id, uid, comment, commentLayout, edt_comment);
                }
            });
        }

    }


}
