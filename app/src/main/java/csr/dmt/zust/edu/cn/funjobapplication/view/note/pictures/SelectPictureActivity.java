package csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.Layout.SquareFrameLayout;

public class SelectPictureActivity extends AppCompatActivity {
    public static final String SELECT_PICTURE_TO_SHOW_FRAGMENT_EXTRA_KEY = "SELECT_PICTURE_TO_SHOW_FRAGMENT_EXTRA_KEY";
    private static final String SHOW_FRAGMENT_TO_SELECT_PICTURE_KEY = "SHOW_FRAGMENT_TO_SELECT_PICTURE_KEY";
    public static final int MAX_SIZE = 9;
    private TextView mTvBack;
    private TextView mTvSelectCount;
    private TextView mTvPreview;

    private ArrayList<Picture> mSelectedPictures = new ArrayList<>();    //被选中图片的集合
    private ArrayList<Picture> mPictures = new ArrayList<>(); // 所有图片
    private SelectPictureAdapter mSelectPictureAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        mTvBack = findViewById(R.id.tv_back);
        mTvSelectCount = findViewById(R.id.tv_ok);
        mTvPreview = findViewById(R.id.tv_preview);
        RecyclerView pictureRecyclerView = findViewById(R.id.rv_select_picture);

        initButtonClickHandler();
        setupSelectedImages();
        pictureRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mSelectPictureAdapter = new SelectPictureAdapter(mPictures, mSelectedPictures);
        pictureRecyclerView.setAdapter(mSelectPictureAdapter);
        /*
         * 每个Activity或者Fragment都有唯一的一个LoaderManager实例，用来启动、停止、保持、重启、关闭它的Loaders。
         * 异步加载图片
         */
        LoaderManager.getInstance(this).initLoader(0, null, mLoaderCallbacks);
    }

    /**
     * 获取Intent
     *
     * @param context  Context
     * @param pictures 已经选择的图片
     * @return intent
     */
    public static Intent newIntent(Context context, ArrayList<Picture> pictures) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SHOW_FRAGMENT_TO_SELECT_PICTURE_KEY, pictures);

        Intent intent = new Intent(context, SelectPictureActivity.class);
        intent.putExtras(bundle);

        return intent;
    }

    /**
     * 定义按钮点击事件
     */
    private void initButtonClickHandler() {
        // 完成
        mTvSelectCount.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(SELECT_PICTURE_TO_SHOW_FRAGMENT_EXTRA_KEY, mSelectedPictures);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        });
        mTvBack.setOnClickListener(v -> finish());
        // 预览
        mTvPreview.setOnClickListener(v -> {
            if (mSelectedPictures.size() > 0) {
                Intent intent = PreviewImageActivity.newIntent(this, mSelectedPictures);
                startActivity(intent);
            } else {
                Toast.makeText(SelectPictureActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始换已选择的图片
     */
    private void setupSelectedImages() {
        // 如果有图片，初始化 已选择数组
        Intent intent = getIntent();
        ArrayList<Picture> selectPictures =
                (ArrayList<Picture>) intent.getExtras().get(SHOW_FRAGMENT_TO_SELECT_PICTURE_KEY);
        if (selectPictures != null) {
            mSelectedPictures.addAll(selectPictures);
        }

        if (mSelectedPictures.size() > 0 && mSelectedPictures.size() <= MAX_SIZE) {
            mTvPreview.setClickable(true);
            mTvPreview.setText(new Formatter().format("预览%d/%d", mSelectedPictures.size(), MAX_SIZE).toString());
            mTvPreview.setTextColor(ContextCompat.getColor(SelectPictureActivity.this, R.color.colorAccent));
        }
    }

    /**
     * 添加图片
     *
     * @param pictures 所有图片
     */
    private void addImagesToAdapter(ArrayList<Picture> pictures) {
        mPictures.clear();
        mPictures.addAll(pictures);

        mSelectPictureAdapter.notifyDataSetChanged();
    }

    private class SelectPictureAdapter extends RecyclerView.Adapter<SelectPictureHolder> {
        private List<Picture> mMyAllPictures;
        private List<Picture> mMySelectPictures;

        private SelectPictureAdapter(List<Picture> myAllPictures, List<Picture> mySelectPictures) {
            this.mMyAllPictures = myAllPictures;
            this.mMySelectPictures = mySelectPictures;
        }

        @NonNull
        @Override
        public SelectPictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SelectPictureActivity.this);
            return new SelectPictureHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPictureHolder holder, int position) {
            Picture thePicture = mMyAllPictures.get(position);
            if (!TextUtils.isEmpty(thePicture.getPath())) {
                Glide.with(SelectPictureActivity.this)
                        .load(thePicture.getPath())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))//圆角半径
                        .into(holder.getImageViewImage());
                // 每张图片设置监听器
                holder.getSquareFrameLayout().setOnClickListener(v -> {
                    if (thePicture.isSelect()) {
                        thePicture.setSelect(false);
                        mMySelectPictures.remove(thePicture);
                        holder.getImageViewClicked().setSelected(false);
                    } else if (mMySelectPictures.size() < SelectPictureActivity.MAX_SIZE) {
                        thePicture.setSelect(true);
                        mMySelectPictures.add(thePicture);
                        holder.getImageViewClicked().setSelected(true);
                    }
                    mTvPreview.setText(new Formatter().format("预览%d/%d", mSelectedPictures.size(), MAX_SIZE).toString());
                });
                // 需要设置点击状态
                // 再次进入时 可以显示是否被选状态
                holder.getImageViewClicked().setSelected(thePicture.isSelect());
            }
        }

        @Override
        public int getItemCount() {
            return mMyAllPictures.size();
        }
    }

    private class SelectPictureHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewImage;
        private ImageView mImageViewClicked;

        private SquareFrameLayout getSquareFrameLayout() {
            return mSquareFrameLayout;
        }

        private SquareFrameLayout mSquareFrameLayout;

        private SelectPictureHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_note_pictures_selecte_list, parent, false));
            mImageViewImage = itemView.findViewById(R.id.iv_image);
            mImageViewClicked = itemView.findViewById(R.id.iv_selected);
            mSquareFrameLayout = itemView.findViewById(R.id.sf_image);
        }

        private ImageView getImageViewClicked() {
            return mImageViewClicked;
        }

        private ImageView getImageViewImage() {
            return mImageViewImage;
        }
    }

    /*
     *LoaderManager.LoaderCallbacks<D>接口LoaderManager用来向客户返回数据的方式。
     *
     * Cursor 是每行的集合
     * Cursor 是一个随机的数据源。所有的数据都是通过下标取得。
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        /**
         * 所有图片内容的容器
         */
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        //创建一个CursorLoader，去异步加载相册的图片
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            /*
             * CursorLoader是Google封装的很好的专门用于数据库读取获取Cursor的Loader类
             * context ： 上下文
             * uri : 要访问数据库的 uri地址
             * projection ： 对应于数据库语句里的 某列， 如果只需要访问某几列， 则传入这几列的名字即可， 如果不传， 则默认访问全部数据。
             * selection ：一些特殊的筛选条件
             * selectionArgs: 传入具体的参数， 会替换上述 selection中的？
             * sortOrder： 排序规则， 可以为空
             */
            return new CursorLoader(SelectPictureActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC");
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                ArrayList<Picture> pictures = new ArrayList<>();

                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        String path =
                                data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name =
                                data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime =
                                data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        int id =
                                data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                        String thumbPath =
                                data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                        String bucket =
                                data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));

                        Picture picture = new Picture();
                        picture.setPath(path);
                        picture.setName(name);
                        picture.setDate(dateTime);
                        picture.setId(id);
                        picture.setThumbPath(thumbPath);
                        picture.setFolderName(bucket);
                        pictures.add(picture);
                        //如果是被选中的图片
                        if (mSelectedPictures.size() > 0) {
                            for (Picture i : mSelectedPictures) {
                                if (i.getPath().equals(picture.getPath())) {
                                    picture.setSelect(true);
                                }
                            }
                        }

                    } while (data.moveToNext());
                }
                addImagesToAdapter(pictures);
                // 删除掉不存在的，在于用户选择了相片，又去相册删除
                if (mSelectedPictures.size() > 0) {
                    List<Picture> rs = new ArrayList<>();
                    for (Picture i : mSelectedPictures) {
                        File f = new File(i.getPath());
                        if (!f.exists()) {
                            rs.add(i);
                        }
                    }
                    mSelectedPictures.removeAll(rs);
                }
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    };
}
