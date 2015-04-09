package uz.support.v14.app.index;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import uz.support.v14.R;
import uz.support.v14.collection.MyArray;
import uz.support.v14.widget.Section;
import uz.support.v14.widget.SessionAdapter;
import uz.support.v14.widget.ViewSetup;

public abstract class IndexSectionFragment extends IndexFragment {

    @Override
    protected ViewSetup onCreateViewSetup(LayoutInflater inflater, ViewGroup container) {
        return new ViewSetup(inflater, container, R.layout.i_list_layout);
    }

    public void setSections(MyArray<Section> sections) {
        SessionAdapter adapter = new SessionAdapter(getActivity(), sections);
        vsRoot.listView(R.id.list).setAdapter(adapter);
    }
}
