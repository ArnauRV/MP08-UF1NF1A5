package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 06/02/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.model.Especies;

import java.util.ArrayList;
import java.util.List;

public class EspeciesAdapter extends ArrayAdapter<Especies> implements Filterable{
    private List<Especies> dades;
    private Filter mFilter;
    private List<Especies> mItems;
    private Context context;

    public EspeciesAdapter(Context context, List<Especies> dades) {
        super(context, R.layout.activity_content_especies,dades);
        this.dades = dades;
        this.mItems = dades;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Especies getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(R.layout.activity_content_especies, null);

        ImageView imatgeCapcalera = (ImageView) element.findViewById(R.id.imgEspecies);
        imatgeCapcalera.setImageBitmap(Utilitats.getPhoto(mItems.get(position).getImgID()));

        TextView lblTitol = (TextView) element.findViewById(R.id.tvNomEspecie);
        lblTitol.setText(mItems.get(position).getNomEspecie());

        return element;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemsFilter();
        }
        return mFilter;
    }

    private class ItemsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                List<Especies> tempList = new ArrayList<Especies>();

                // search content in friend list
                for (Especies user : dades) {
                    if (user.getNomEspecie().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        System.out.println(user.getNomEspecie());
                        tempList.add(user);
                    }
                }

                filterResults.values = tempList;
                filterResults.count = tempList.size();
            } else {
                filterResults.values = dades;
                filterResults.count = dades.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mItems = (List<Especies>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
