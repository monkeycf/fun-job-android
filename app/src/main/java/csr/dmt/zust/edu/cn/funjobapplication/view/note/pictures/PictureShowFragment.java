package csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteMarkdownFragment;

import static android.app.Activity.RESULT_OK;

/**
 * created by monkeycf on 2019/12/18
 */
public class PictureShowFragment extends Fragment {
    private static final int EXTERNAL_PERMISSION_REQUEST_CODE = 0;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int TAKE_PHOTO_REQUEST_CODE = 1;
    private static final int SELECT_IMAGE_REQUEST_CODE = 2;

    private ShowImagesRecyclerViewAdapter mShowImagesRecyclerViewAdapter;
    private TextView mDragTip;

    private ArrayList<Picture> mSelectPictures = new ArrayList<>();
    private File mTakePhotoImageFile;

    private ISelectedPictureChange listener;

    // 选择的
    public interface ISelectedPictureChange {
        void onSelectedPictureChangeHandler(ArrayList<Picture> selectPictures);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note_pictures_list_show, container, false);
        RecyclerView mSelectedImageRecyclerView = v.findViewById(R.id.rv_selected_image);
        mDragTip = v.findViewById(R.id.drag_tip);

        // 选择图片
        v.findViewById(R.id.btn_select).setOnClickListener(view -> verifyExternalPermission());
        // 拍照
        v.findViewById(R.id.btn_take_photo).setOnClickListener(view -> verifyCameraPermission());

        mSelectedImageRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mShowImagesRecyclerViewAdapter = new ShowImagesRecyclerViewAdapter(mSelectPictures);
        mSelectedImageRecyclerView.setAdapter(mShowImagesRecyclerViewAdapter);
        mItemTouchHelper.attachToRecyclerView(mSelectedImageRecyclerView);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 判断是否实现该接口
        if (context instanceof ISelectedPictureChange) {
            listener = (ISelectedPictureChange) context; // 获取到宿主context并赋值
            listener.onSelectedPictureChangeHandler(mSelectPictures);
        } else {
            throw new IllegalArgumentException("Context not found implements ISelectedPictureChange.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 选择图片回调
            if (requestCode == SELECT_IMAGE_REQUEST_CODE && data != null) {
                updateSelectedPictures((ArrayList<Picture>) data.getExtras().get(SelectPictureActivity.SELECT_PICTURE_TO_SHOW_FRAGMENT_EXTRA_KEY));
            }
            if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
                galleryAddPictures();
            }
        }
    }

    /**
     * 获取权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // SD存储权限
        if (requestCode == EXTERNAL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSelectPictureActivity();
            } else {
                getExternalPermissions();
                Toast.makeText(getContext(), "需要您的存储权限!", Toast.LENGTH_SHORT).show();
            }
        }
        // 相机
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                getCameraPermissions();
                Toast.makeText(getContext(), "需要您的相机权限!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 验证相机权限
     */
    private void verifyCameraPermission() {
        if (getContext() == null || getActivity() == null) {
            throw new IllegalArgumentException("verifyCameraPermission:::getContext or getActivity is null...");
        }
        int isCameraPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA);
        if (isCameraPermission == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            getCameraPermissions();
        }
    }

    /**
     * 验证SD卡读写权限
     */
    private void verifyExternalPermission() {
        if (getContext() == null) {
            throw new IllegalArgumentException("verifyExternalPermission:::getContext is null...");
        }
        int isPermission1 = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE); // 读取外部存储
        int isPermission2 = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE); // 写
        // 判断是否有权限
        if (isPermission1 == PackageManager.PERMISSION_GRANTED && isPermission2 == PackageManager.PERMISSION_GRANTED) {
            startSelectPictureActivity();
        } else {
            getExternalPermissions();
        }
    }

    /**
     * 申请相机权限
     */
    private void getCameraPermissions() {
        if (getActivity() == null) {
            throw new IllegalArgumentException("getCameraPermissions:::getActivity is null...");
        }
        //申请权限
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                CAMERA_PERMISSION_REQUEST_CODE);
    }

    /**
     * 申请SD存储卡权限
     */
    private void getExternalPermissions() {
        if (getActivity() == null) {
            throw new IllegalArgumentException("getExternalPermissions:::getActivity is null...");
        }
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EXTERNAL_PERMISSION_REQUEST_CODE);
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        // 用来打开相机的Intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            mTakePhotoImageFile = createImageFile();
            if (mTakePhotoImageFile != null) {
                Uri mImageUri;

                // SDK 版本兼容
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 7.0以上要通过FileProvider将File转化为Uri
                    mImageUri = FileProvider.getUriForFile(getContext(),
                            this.getActivity().getPackageName() + ".fileprovider", mTakePhotoImageFile);
                } else {
                    // 7.0以下则直接使用Uri的fromFile方法将File转化为Uri
                    mImageUri = Uri.fromFile(mTakePhotoImageFile);
                }
                // 将用于输出的文件Uri传递给相机
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                //启动相机
                startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
            }
        }
    }

    /**
     * 生成文件
     *
     * @return File
     */
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = null;
        try {
            file = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 跳转至SelectPictureActivity
     */
    private void startSelectPictureActivity() {
        Intent intent = SelectPictureActivity.newIntent(getContext(), mSelectPictures);
        startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
    }

    /**
     * 选择图片返回,更新显示的选择图片数组
     *
     * @param selectPictures 新的选择的图片数组
     */
    private void updateSelectedPictures(ArrayList<Picture> selectPictures) {
        // 清空当前图片数组,并重新赋值选择数组
        mSelectPictures.clear();
        if (selectPictures != null) {
            mSelectPictures.addAll(selectPictures);
        }
        if (mSelectPictures.size() > 1) {
            mDragTip.setVisibility(View.VISIBLE);
        }

        listener.onSelectedPictureChangeHandler(mSelectPictures);
        mShowImagesRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 将拍的照片添加到相册
     */
    private void galleryAddPictures() {
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    mTakePhotoImageFile.getAbsolutePath(), mTakePhotoImageFile.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(mTakePhotoImageFile));
        getActivity().sendBroadcast(mediaScanIntent);
        // 添加显示
        Picture picture = new Picture();
        // 路径需要为图库中的路径,不是临时路径
        picture.setPath(getLastPhotoByPath(getContext()));
        mSelectPictures.add(picture);

        listener.onSelectedPictureChangeHandler(mSelectPictures);
        mShowImagesRecyclerViewAdapter.notifyDataSetChanged();
    }

    /**
     * 获取图片第一张图的路径
     *
     * @param ctx Context
     * @return 路径
     */
    public static String getLastPhotoByPath(Context ctx) {
        Cursor cursor;
        String lastFilePath = "";
        String[] largeFileProjection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStore.Images.ImageColumns.DATE_TAKEN};
        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        cursor = ctx.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                largeFileProjection, null, null, largeFileSort);
        if (cursor == null) {
            throw new IllegalArgumentException("getLastPhotoByPath:::cursor is null...");
        }
        if (cursor.getCount() < 1) {
            cursor.close();
            return lastFilePath;
        }
        while (cursor.moveToNext()) {
            String data =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            File f = new File(data);
            if (f.exists()) {//第一个图片文件，就是最近一次拍照的文件；
                lastFilePath = f.getPath();
                break;
            }
        }
        cursor.close();
        return lastFilePath;
    }

    /**
     * 每个元素的触摸
     */
    private ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP |
                    ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        /**
         * 拖动的时候不断的回调方法
         */
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            //获取到原来的位置
            int fromPosition = viewHolder.getAdapterPosition();
            //获取到拖到的位置
            int targetPosition = target.getAdapterPosition();
            if (fromPosition < targetPosition) {
                for (int i = fromPosition; i < targetPosition; i++) {
                    Collections.swap(mSelectPictures, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > targetPosition; i--) {
                    Collections.swap(mSelectPictures, i, i - 1);
                }
            }
            mShowImagesRecyclerViewAdapter.notifyItemMoved(fromPosition, targetPosition);
            return true;
        }

        /**
         * 侧滑删除后会回调的方法
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mSelectPictures.remove(position);
            listener.onSelectedPictureChangeHandler(mSelectPictures);
            mShowImagesRecyclerViewAdapter.notifyItemRemoved(position);
        }
    });

    private class ShowImagesRecyclerViewAdapter extends RecyclerView.Adapter<ShowImagesRecyclerViewHolder> {
        private List<Picture> mPictures;

        private ShowImagesRecyclerViewAdapter(List<Picture> pictures) {
            mPictures = pictures;
        }

        @NonNull
        @Override
        public ShowImagesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ShowImagesRecyclerViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShowImagesRecyclerViewHolder holder, int position) {
            holder.bind(mPictures.get(position));
        }

        @Override
        public int getItemCount() {
            return mPictures.size();
        }
    }

    private class ShowImagesRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        private ShowImagesRecyclerViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_note_pictures_selected, parent, false));
            mImageView = itemView.findViewById(R.id.iv_selected_image);
        }

        private void bind(Picture picture) {
            if (getContext() == null) {
                throw new IllegalArgumentException("ShowImagesRecyclerViewHolder:bind:::getContext is null...");
            }
            Glide.with(getContext())
                    .load(picture.getPath())
                    .into(mImageView);
        }
    }
}
