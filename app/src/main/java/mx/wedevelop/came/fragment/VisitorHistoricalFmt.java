package mx.wedevelop.came.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.wedevelop.came.R;
import mx.wedevelop.came.model.Visitor;

/**
 * Created by Cristian Colorado <cristian@wedevelop.mx> on 7/12/16.
 */
public class VisitorHistoricalFmt extends Fragment {

    public static VisitorHistoricalFmt newInstance() {
        VisitorHistoricalFmt visitorHistoricalFmt = new VisitorHistoricalFmt();

//        Bundle bundle = new Bundle();
//        bundle.putParcelable(VisitorProfileFmt.VISITOR, visitor);
//        visitorProfileFmt.setArguments(bundle);

        return visitorHistoricalFmt;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.visitor_historical_fmt, container, false);
        return rootView;
    }
}
