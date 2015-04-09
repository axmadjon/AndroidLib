package uz.support.v14.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.app.content.ContentFragment;
import uz.support.v14.widget.ViewSetup;
import uz.support.v14.demo.api.DemoRequest;
import uz.support.v14.letter.LetterReceiver;

public class DemoLetterFragment extends ContentFragment {

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.demo_letter_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceHelper.send(new DemoRequest(), new LetterReceiver<String, Void>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Void error) {

            }
        });
    }
}
