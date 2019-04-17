package com.example.bookcase_audio;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;




public class BookListFragment extends Fragment {


    private BookInterface mListener;
    private Context c;
    ArrayList <Book> bookList;
    Book books;
    BookAdapter adapter;
    ListView listView;

    public BookListFragment() {
        // Required empty public constructor
    }


    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        listView = view.findViewById(R.id.bookList);
        bookList = new ArrayList<>();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookInterface) {
            mListener = (BookInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.c = context;
    }

   

    public void getBooks(final ArrayList<Book> bookArray) {
       adapter = new BookAdapter(c , bookArray);
       adapter.notifyDataSetChanged();
       listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               books = bookArray.get(position);
               ((BookInterface) c).bookSelected(books);
           }
       });
    }


    public interface BookInterface {
        void bookSelected(Book bookObj);
    }
}
