package ws.base.mylibonline;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookLineAdapter extends ArrayAdapter<String> {
	  
	Context context;
	ArrayList<String> listInfoLine = new ArrayList<String>();

	public BookLineAdapter(Context context, int textViewResourceId,ArrayList listInfoLine) {
		super(context, textViewResourceId, listInfoLine);
		this.context = context;
		this.listInfoLine = listInfoLine;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLigne = inflater.inflate(R.layout.headline_item, parent, false);
		TextView label=(TextView)viewLigne.findViewById(R.id.tvListTitre);
		label.setText(listInfoLine.get(position));

		return viewLigne;
	}

}