package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;

/**
 * created by monkeycf on 2019/12/15
 * 学习模块Fragment
 */
public class LearnFragment extends Fragment {

    private Button mButtonShow;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_learn_fragment, container, false);
//        mButtonShow = v.findViewById(R.id.btn_learn_item_show);
//        mButtonShow.setOnClickListener(view -> {
//            Intent intent = JSBridgeActivity.newIntent(getActivity(), "123");
//            startActivity(intent);
//        });

//        ImageView imageView = v.findViewById(R.id.iv_show);
//        Glide.with(getActivity())
//                .load("http://img.chensenran.top/1576477362006.gif")
//                .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
//                .error(R.drawable.ic_launcher_background)//图片加载失败后，显示的图片
//                .into(imageView);

//        adapter = new ImageStageredAdapter(this);
//        recyclerView.setAdapter(adapter);
//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(20);
//        recyclerView.addItemDecoration(itemDecoration);
        mRecyclerView = v.findViewById(R.id.rv_learn_wall);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        List<Dome> t = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            t.add(new Dome("1", "标题", "http://img.chensenran.top/1576487280115.png"));
            t.add(new Dome("1", "标题", "https://upload-images.jianshu" +
                    ".io/upload_images/2035066-c44dc4632679e9df" +
                    ".png?imageMogr2/auto-orient/strip|imageView2/2/w/417/format/webp"));
            t.add(new Dome("1", "标题", "https://cn.bing.com/th?id=OIP" +
                    ".UPqSZ-pEvm8lpBXfF2S9VAHaFl&pid=Api&rs=1"));
            t.add(new Dome("1", "标题", "https://upload-images.jianshu" +
                    ".io/upload_images/2035066-c44dc4632679e9df" +
                    ".png?imageMogr2/auto-orient/strip|imageView2/2/w/417/format/webp"));
            t.add(new Dome("1", "标题", "https://cn.bing.com/th?id=OIP" +
                    ".UPqSZ-pEvm8lpBXfF2S9VAHaFl&pid=Api&rs=1"));
            t.add(new Dome("1", "标题", "https://cn.bing.com/th?id=OIP" +
                    ".UPqSZ-pEvm8lpBXfF2S9VAHaFl&pid=Api&rs=1"));
            t.add(new Dome("1", "标题", "https://cn.bing.com/th?id=OIP" +
                    ".UPqSZ-pEvm8lpBXfF2S9VAHaFl&pid=Api&rs=1"));
            t.add(new Dome("1", "标题", "https://upload-images.jianshu" +
                    ".io/upload_images/2035066-c44dc4632679e9df" +
                    ".png?imageMogr2/auto-orient/strip|imageView2/2/w/417/format/webp"));
        }
        mRecyclerView.setAdapter(new LearnAdapter(t));

        StaggeredDividerItemDecoration decoration =
                new StaggeredDividerItemDecoration(getActivity(), 5);
        mRecyclerView.addItemDecoration(decoration);


        return v;
    }

    private class LearnAdapter extends RecyclerView.Adapter<LearnHolder> {
        private List<Dome> mDomes = new ArrayList<>();
        private final double STANDARD_SCALE = 1.1; //当图片宽高比例大于STANDARD_SCALE时，采用3:4比例，小于时，则采用1:1比例
        private final float SCALE = 4 * 1.0f / 3;       //图片缩放比例


        public LearnAdapter(List<Dome> domes) {
            mDomes = domes;
        }

        @Override
        public int getItemCount() {
            return mDomes.size();
        }

        @Override
        public void onBindViewHolder(@NonNull LearnHolder holder, int position) {
            holder.bind(mDomes.get(position));
        }

        @NonNull
        @Override
        public LearnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LearnHolder(layoutInflater, parent);
        }
    }

    private class LearnHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextView;

        public LearnHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.index_learn_wall_item, parent, false));
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.iv_wall_item);
            mTextView = itemView.findViewById(R.id.tv_wall_item);
        }

        public void bind(Dome dome) {
            mTextView.setText(dome.title);
            Glide.with(getActivity())
                    .load(dome.url)
                    .override(600, 800)
                    .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
                    .error(R.drawable.ic_launcher_background)//图片加载失败后，显示的图片
                    .into(mImageView);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "click this item", Toast.LENGTH_SHORT).show();
        }
    }

    private class Dome {
        public String url;
        public String title;
        public String id;

        public Dome(String id, String title, String url) {
            this.url = url;
            this.title = title;
            this.id = id;
        }
    }

    public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Context context;
        private int interval;

        public StaggeredDividerItemDecoration(Context context, int interval) {
            this.context = context;
            this.interval = interval;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    this.interval, context.getResources().getDisplayMetrics());
            // 中间间隔
            if (position % 2 == 0) {
                outRect.left = 0;
            } else {
                // item为奇数位，设置其左间隔为5dp
                outRect.left = interval;
            }
            // 下方间隔
            outRect.bottom = interval;
        }
    }
}
