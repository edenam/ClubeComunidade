package eden.com.br.clubecomunidade.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import eden.com.br.clubecomunidade.R;
import eden.com.br.clubecomunidade.bean.Business;

/**
 * Created by root on 27/04/15.
 */
public class SimpleGuideListGridViewAdapter extends BaseAdapter{

    private FragmentActivity context;
    ArrayList<Business> businessList;

    public SimpleGuideListGridViewAdapter(FragmentActivity context, ArrayList<Business> businessList){
        this.context = context;
        this.businessList = businessList;
    }

    @Override
    public int getCount() {
        return businessList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.businessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_guide_item, null);
        }

        Business tempBusiness = businessList.get(position);

        TextView nameTV = (TextView) convertView.findViewById(R.id.guide_item_name);
        if (nameTV != null) nameTV.setText(tempBusiness.getName());

        TextView addressTV = (TextView) convertView.findViewById(R.id.guide_item_address);
        if (addressTV != null) addressTV.setText(tempBusiness.getAddress());

        TextView telephoneTV = (TextView) convertView.findViewById(R.id.guide_item_telephones);
        if (telephoneTV != null) telephoneTV.setText(tempBusiness.getTelephones());

        TextView categoryTV = (TextView) convertView.findViewById(R.id.guide_item_category);
        if (categoryTV != null) categoryTV.setText(tempBusiness.getCategory().getName());

        return convertView;
    }
}
