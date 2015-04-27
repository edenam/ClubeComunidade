package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import eden.com.br.clubecomunidade.DAO.DAO;
import eden.com.br.clubecomunidade.DAO.DAOAccessCallback;
import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.adapter.SimpleGuideListGridViewAdapter;
import eden.com.br.clubecomunidade.bean.Business;
import eden.com.br.clubecomunidade.customviews.MyGridView;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SimpleGuideListFragment extends Fragment {

    public static final String FRAGMENT_TAG = "simple_guide_list_fragment";
    private OnFragmentInteractionListener mListener;
    public final ArrayList<Business> businesses = new ArrayList<>();

    public SimpleGuideListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_simple_guide_list, container, false);

        final MyGridView gridView = (MyGridView) rootView.findViewById(R.id.guide_list_gridview);

        DAO.getInstance().getSimpleGuideList(4, new DAOAccessCallback() {
            @Override
            public void done(List<?> resultSet, Exception e) {

                for(Business businessParsed : (List<Business>) resultSet) {

                    businesses.add(businessParsed);

                }

                final boolean[] resized = {false};
                gridView.setAdapter(new SimpleGuideListGridViewAdapter(getActivity(), businesses));
//                gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        if(!resized[0]) {
//                            resized[0] = true;
//                            resizeGridView(gridView, businesses.size(), 2);
//                        }
//                    }
//                });

            }
        });

        return rootView;
    }

    private void populateGridView(){

    }

    private void resizeGridView(GridView gridView, int items, int columns) {
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        int oneRowHeight = gridView.getHeight();
        int rows = (int) (items / columns);
        params.height = oneRowHeight * rows;
        gridView.setLayoutParams(params);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
