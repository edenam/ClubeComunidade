package eden.com.br.clubecomunidade.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eden.com.br.clubecomunidade.DAO.DAO;
import eden.com.br.clubecomunidade.DAO.DAOAccessCallback;
import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.Business;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SimpleGuideListFragment extends Fragment {

    public static final String FRAGMENT_TAG = "simple_guide_list_fragment";
    private static final int BUSINESS_FETCH_COUNT = 4;
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

        // Fetch the information to the Business Guide and switch it on the table
        populateTableLayout(rootView);



        return rootView;
    }

    private void populateTableLayout(final View rootView){

        DAO.getInstance().getSimpleGuideList(SimpleGuideListFragment.BUSINESS_FETCH_COUNT, new DAOAccessCallback() {
            @Override
            public void done(List<?> resultSet, Exception e) {

                TableLayout guide = (TableLayout) rootView.findViewById(R.id.simple_guide_table);

                int i = 0;
                for(Business businessParsed : (List<Business>) resultSet) {

                    View cell;
                    switch (i) {
                        case 0:
                            cell = guide.findViewById(R.id.simple_guide_cell_0);
                            break;

                        case 1:
                            cell = guide.findViewById(R.id.simple_guide_cell_1);
                            break;

                        case 2:
                            cell = guide.findViewById(R.id.simple_guide_cell_2);
                            break;

                        case 3:
                            cell = guide.findViewById(R.id.simple_guide_cell_3);
                            break;

                        default:
                            return;
                    }

                    TextView nameTV = (TextView) cell.findViewById(R.id.guide_item_name);
                    if (nameTV != null) nameTV.setText(businessParsed.getName());

                    TextView addressTV = (TextView) cell.findViewById(R.id.guide_item_address);
                    if (addressTV != null) addressTV.setText(businessParsed.getAddress());

                    TextView telephoneTV = (TextView) cell.findViewById(R.id.guide_item_telephones);
                    if (telephoneTV != null) telephoneTV.setText(businessParsed.getTelephones());

                    TextView categoryTV = (TextView) cell.findViewById(R.id.guide_item_category);
                    if (categoryTV != null) categoryTV.setText(businessParsed.getCategory().getName());


                    businesses.add(businessParsed);
                    i++;

                }
            }
        });

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
