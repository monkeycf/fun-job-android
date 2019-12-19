package csr.dmt.zust.edu.cn.funjobapplication.view.note;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class NoteMarkdownFragment extends Fragment {
    private static NoteMarkdownFragment sNoteMarkdownFragment;
    private IFragmentInteraction listener; // 定义用来与外部activity交互，获取到宿主context

    /**
     * 定义了所有activity必须实现的接口方法
     */
    public interface IFragmentInteraction {
        void getMarkdownText(String str);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 判断是否实现该接口
        if (context instanceof IFragmentInteraction) {
            listener = (IFragmentInteraction) context; // 获取到宿主context并赋值
        } else {
            throw new IllegalArgumentException("Context not found implements IFragmentInteraction.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_fragment_markdown, container, false);
        EditText mEtMarkdown = (EditText) v.findViewById(R.id.et_input_field);
        mEtMarkdown.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.getMarkdownText(s.toString()); // 执行回调
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null; // 释放
    }

    /**
     * 获得实例
     *
     * @return NoteMarkdownFragment 实例
     */
    public static NoteMarkdownFragment getInstance() {
        if (sNoteMarkdownFragment == null) {
            sNoteMarkdownFragment = new NoteMarkdownFragment();
        }
        return sNoteMarkdownFragment;
    }

    /**
     * 私有构造函数以实现单例
     */
    private NoteMarkdownFragment() {
    }
}
