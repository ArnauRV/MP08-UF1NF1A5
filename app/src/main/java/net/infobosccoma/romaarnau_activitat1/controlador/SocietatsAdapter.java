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
import net.infobosccoma.romaarnau_activitat1.model.Societats;

import java.util.ArrayList;
import java.util.List;

public class SocietatsAdapter extends ArrayAdapter<Societats> implements Filterable{
    private List<Societats> dades;
    private Filter mFilter;
    private List<Societats> mItems;
    private Context context;

    public SocietatsAdapter(Context context, List<Societats> dades) {
        super(context, R.layout.activity_content_societats,dades);
        this.dades = dades;
        this.mItems = dades;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Societats getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //LayoutInflater generador de layouts
        View element = inflater.inflate(R.layout.activity_content_societats, null); // l'inflador construeix a partir del xml en forma d'objecte

        ImageView imatgeCapcalera = (ImageView) element.findViewById(R.id.imgSocietat);

        imatgeCapcalera.setImageBitmap(Utilitats.getPhoto(mItems.get(position).getImgID()));

        TextView lblTitol = (TextView) element.findViewById(R.id.tvText1); // tinc el layout, doncs busco vistes
        lblTitol.setText(mItems.get(position).getNom()); // li assigno que el lbltitol ha de mostrar x títol

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
                List<Societats> tempList = new ArrayList<Societats>();

                // search content in friend list
                for (Societats user : dades) {
                    if (user.getNom().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        System.out.println(user.getNom());
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
                mItems = (List<Societats>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}