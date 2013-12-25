/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.base.mylibonline;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import ws.base.modeles.Book;

/**
 * Fragment that displays the news headlines for a particular news category.
 *
 * This Fragment displays a list with the news headlines for a particular news category.
 * When an item is selected, it notifies the configured listener that a headlines was selected.
 */
public class BookLinesFragment extends ListFragment implements OnItemClickListener, OnItemLongClickListener {
    // The list of headlines that we are displaying
	private ArrayList<String> headlinesList = new ArrayList<String>();

    // The list adapter for the list we are displaying
    //ArrayAdapter<String> listAdapter;
	public ListAdapter listAdapter;

    // The listener we are to notify when a headline is selected
    private OnHeadlineSelectedListener mHeadlineSelectedListener = null;
    private OnHeadlineSelectedListenerLong mHeadlineSelectedListenerLong = null;
    

    /**
     * Represents a listener that will be notified of headline selections.
     */
    public interface OnHeadlineSelectedListener {
        /**
         * Called when a given headline is selected.
         * @param index the index of the selected headline.
         */
        public void onHeadlineSelected(int index);
    }
    
    public interface OnHeadlineSelectedListenerLong {
        /**
         * Called when a given headline is selected.
         * @param index the index of the selected headline.
         */
        public void onHeadlineSelectedLong(int index);
    }
    


    /**
     * Default constructor required by framework.
     */
    public BookLinesFragment() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(listAdapter);
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        loadCategory(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new BookLineAdapter(getActivity(), R.layout.headline_item, headlinesList);
		setListAdapter(listAdapter);
    }

    /**
     * Sets the listener that should be notified of headline selection events.
     * @param listener the listener to notify.
     */
    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener listener) {
        mHeadlineSelectedListener = listener;
    }
    
    public void setOnHeadlineSelectedListenerLong(OnHeadlineSelectedListenerLong listener) {
        mHeadlineSelectedListenerLong = listener;
    }


    /**
     * Load and display the headlines for the given news category.
     * @param categoryIndex the index of the news category to display.
     */
    public void loadCategory(int categoryIndex) {
        headlinesList.clear();
        for (int Cpt = 0; Cpt < BaseActivity.listBook.size(); Cpt++){
        	Book book = BaseActivity.listBook.get(Cpt);
        	if (categoryIndex != 1){
        		headlinesList.add(book.getTitre());
        	}else{
        		headlinesList.add(book.getAuteur());
        	}
        }
        //Set<String> set = new HashSet<String>(headlinesList);
        //headlinesList = new ArrayList<String>(set);
        
        ((BaseAdapter) listAdapter).notifyDataSetChanged();
    }
    
    

    /**
     * Handles a click on a headline.
     *
     * This causes the configured listener to be notified that a headline was selected.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mHeadlineSelectedListener) {
            mHeadlineSelectedListener.onHeadlineSelected(position);
        }
    }
    
    @Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    	if (null != mHeadlineSelectedListenerLong) {
            mHeadlineSelectedListenerLong.onHeadlineSelectedLong(position);
            return true;
        }
		return true;
	}
   
    

    /** Sets choice mode for the list
     *
     * @param selectable whether list is to be selectable.
     */
    public void setSelectable(boolean selectable) {
        if (selectable) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
        else {
            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        }
    }

	

	
    

    
}
