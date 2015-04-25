package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;

public class MainFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public static final String FRAGMENT_TAG = "main_fragment";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
//    public static MainFragment newInstance(int sectionNumber) {
//        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Incluindo o fragment do banner de noticias
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.banner_news_container, new BannerNewsFragment(), BannerNewsFragment.FRAGMENT_TAG);
//        ft.replace(R.id.events_with_pics_container, new EventsWithPicsFragment(), EventsWithPicsFragment.FRAGMENT_TAG);
//        ft.replace(R.id.guide_with_pics, new GuideWithPicsFragment(), EventsWithPicsFragment.FRAGMENT_TAG);
        ft.addToBackStack(null);
        ft.commit();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Fragment fragment, Bundle arguments) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fragment);
        }
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
