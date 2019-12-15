package csr.dmt.zust.edu.cn.funjobapplication.view.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zzhoujay.richtext.RichText;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class NoteTextShowFragment extends Fragment {
    private static final String MARKDOWN_TEXT = "NOTE_MARKDOWN_TEXT_SHOW_KEY";
    private String markdownText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_fragment_text, container, false);
        getArgumentsMarkdown();

        TextView mTvInputShow = v.findViewById(R.id.tv_input_show);
        RichText.fromMarkdown(markdownText).into(mTvInputShow);
        return v;
    }

    /**
     * 获得实例
     *
     * @param value 需要传递的字符串
     * @return NoteTextShowFragment 实例
     */
    public static NoteTextShowFragment getInstance(String value) {
        Bundle args = new Bundle();
        args.putSerializable(MARKDOWN_TEXT, value);

        NoteTextShowFragment noteTextShowFragment = new NoteTextShowFragment();
        noteTextShowFragment.setArguments(args);
        return noteTextShowFragment;
    }

    private NoteTextShowFragment() {
    }

    /**
     * 获取传入的markdown字符串
     */
    private void getArgumentsMarkdown() {
        markdownText = getArguments().get(MARKDOWN_TEXT).toString();
    }
}
