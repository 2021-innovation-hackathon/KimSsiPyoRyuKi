package com.kims.hackathon.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kims.hackathon.R;
import com.kims.hackathon.client.bucket.Hocket;
import com.kims.hackathon.client.bucket.SimpleHocket;
import com.kims.hackathon.view.SimpleHeartView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimpleHocketRecyclerView extends RecyclerView {
    private Context context;

    public SimpleHocketRecyclerView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SimpleHocketRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private List<SimpleHocket> getSimpleHockets() {
        SimpleHocket dummy1 = new SimpleHocket();
        dummy1.setTitle("해커톤");
        dummy1.setLikes(10);
        dummy1.setTargetDate(LocalDateTime.now());

        SimpleHocket dummy2 = new SimpleHocket();
        dummy2.setTitle("해커톤2");
        dummy2.setLikes(15);
        dummy2.setTargetDate(LocalDateTime.now().minusDays(1));

        SimpleHocket dummy3 = new SimpleHocket();
        dummy3.setTitle("오마카세");
        dummy3.setLikes(20);
        dummy3.setTargetDate(LocalDateTime.now().minusDays(2));

        SimpleHocket dummy4 = new SimpleHocket();
        dummy4.setTitle("오마카세2");
        dummy4.setLikes(25);
        dummy4.setTargetDate(LocalDateTime.now().minusDays(3));

        List<SimpleHocket> hockets = new ArrayList<>();
        hockets.add(dummy1);
        hockets.add(dummy2);
        hockets.add(dummy3);
        hockets.add(dummy4);


        return hockets;
    }


    private void initView() {
        setLayoutManager(getHorizontalLayoutManager());
        setAdapter(new SimpleHocketAdapter(context, getSimpleHockets()));
    }

    private LayoutManager getHorizontalLayoutManager() {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    private static class SimpleHocketAdapter extends Adapter<SimpleHocketViewHolder> {

        private Context context;
        private List<SimpleHocket> hockets;

        private SimpleHocketAdapter(Context context, List<SimpleHocket> hockets) {
            this.context = context;
            this.hockets = hockets;
        }

        @NonNull
        @Override
        public SimpleHocketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.hocket_simple_view, parent, false);
            return new SimpleHocketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SimpleHocketViewHolder holder, int position) {
            String title = hockets.get(position).getTitle();
            String date = hockets.get(position).getDateAsString();
            int likes = hockets.get(position).getLikes();

            holder.titleView.setText(title);
            holder.dateView.setText(date);
            holder.simpleHeartView.setLikeNumber(likes);
        }

        @Override
        public int getItemCount() {
            return hockets.size();
        }
    }

    static class SimpleHocketViewHolder extends ViewHolder {

        private SimpleHeartView simpleHeartView;
        private TextView titleView;
        private TextView dateView;

        public SimpleHocketViewHolder(@NonNull View itemView) {
            super(itemView);
            this.simpleHeartView = itemView.findViewById(R.id.hocket_simple_heart_view);
            this.titleView = itemView.findViewById(R.id.hocket_simple_title);
            this.dateView = itemView.findViewById(R.id.hocket_simple_target_date);
        }
    }
}
