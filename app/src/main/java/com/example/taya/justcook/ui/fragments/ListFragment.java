package com.example.taya.justcook.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.taya.justcook.domain.entity.Category;
import com.example.taya.justcook.dal.DBHelper;
import com.example.taya.justcook.ui.DetailedRecipeActivity;
import com.example.taya.justcook.R;

public class ListFragment extends android.support.v4.app.Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int category;
    private ListView listView;
    private DBHelper dbHelper;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    public void setCategory(int category) {
        this.category = category;
    }

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        category = Category.ALL.ordinal();
    }


    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_recipe, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        setList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int listPosition, long rowId) {
                Intent intent = new Intent(getContext(), DetailedRecipeActivity.class);
                intent.putExtra("recordId", rowId);
                startActivityForResult(intent, 0);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setList();
    }

    public void setList() {
        dbHelper = DBHelper.getInstance(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        cursor = dbHelper.getDataByCategory(category, database);
        scAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1, cursor,
                new String[]{DBHelper.RECIPE_NAME},
                new int[]{android.R.id.text1}, 0);
        listView.setAdapter(scAdapter);
        database.close();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
