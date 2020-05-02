package com.example.intellectus.tn.esprit.intellectus.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.intellectus.R;
import com.example.intellectus.tn.esprit.intellectus.Entity.Message;
import com.example.intellectus.tn.esprit.intellectus.Utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ChatBoxAdapter  extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {
    private List<Message> MessageList;
    private Context mContext;

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public TextView message;
        public ImageView avatar;


        public MyViewHolder(View view) {
            super(view);

            nickname = (TextView) view.findViewById(R.id.dad);
            message = (TextView) view.findViewById(R.id.message);
            avatar = (ImageView) view.findViewById(R.id.avatar);





        }
    }
    public ChatBoxAdapter(Context mContext, List<Message> MessagesList) {
        this.mContext = mContext ;
        this.MessageList = MessagesList;


    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }
    @Override
    public ChatBoxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);



        return new ChatBoxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatBoxAdapter.MyViewHolder holder, final int position) {
        final Message m = MessageList.get(position);
        holder.nickname.setText(m.getNickname());

        if (m.getAvatar().length() > 3){
            Picasso.with(mContext).load(AppUtils.SERVER_URL+"uploads/"+m.getAvatar()).into(holder.avatar);
        }else{
            Picasso.with(mContext).load(AppUtils.SERVER_URL+"uploads/default.jpg").into(holder.avatar);
        }
        holder.message.setText(m.getMessage() );




    }



}
