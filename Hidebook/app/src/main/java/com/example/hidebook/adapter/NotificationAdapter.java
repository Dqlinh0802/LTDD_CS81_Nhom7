package com.example.hidebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hidebook.R;
import com.example.hidebook.model.NotificationModel;

import java.util.ArrayList;


public class NotificationAdapter extends  RecyclerView.Adapter<NotificationAdapter.HolderNotification>{

    private Context context;
    private ArrayList<NotificationModel> notificationList;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationsList){
        this.context = context;
        this.notificationList = notificationsList;
    }


    @NonNull
    @Override
    public HolderNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view notification_item
        View view = LayoutInflater.from(context).inflate(R.layout.notification_items, parent, false);
        return new HolderNotification(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotification holder, int position) {
        //get and set data to views

        //get data
        NotificationModel model = notificationList.get(position);
        String name = model.getsName();
        String notification = model.getNotification();
        String image = model.getsImage();
        String timestamp = model.getTimestamp();

        //set to views
        holder.nameTV.setText(name);
        holder.notificationTV.setText(notification);



    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    //holder class for views of notification_item
    class HolderNotification extends RecyclerView.ViewHolder{
        //Khai báo views
        ImageView avatarIV;
        TextView nameTV, notificationTV, timeTV;



        public HolderNotification(@NonNull View itemView) {
            super(itemView);
            //init views
            avatarIV = itemView.findViewById(R.id.iv_avatar);
            nameTV = itemView.findViewById(R.id.tv_name);
            notificationTV = itemView.findViewById(R.id.tv_notification);
            timeTV = itemView.findViewById(R.id.tv_time);

        }
    }


}
