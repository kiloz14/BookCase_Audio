package com.example.bookcase_audio;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class BookDetailsFragment extends Fragment {
    Context context;
    TextView textView;
    ImageView imageView;
    String bookSelected;
    String title, author, publisher;
    public static final String BOOK_KEY = "myBook";
    Book pagerBooks;
    ImageButton playButton, stopButton, pauseButton;
    SeekBar seekBar;
    TextView progressText;

    private BookDetailsInterface mListner;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BookDetailsFragment newInstance(Book bookList) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BOOK_KEY, bookList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            pagerBooks = getArguments().getParcelable(BOOK_KEY);
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BookListFragment.BookInterface){
            mListner = (BookDetailsInterface) context;
        }
        else{
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        playButton = view.findViewById(R.id.playButton);
        stopButton = view.findViewById(R.id.stopButton);
        pauseButton= view.findViewById(R.id.pauseButton);
        seekBar = view.findViewById(R.id.seekBar);
        textView = view.findViewById(R.id.bookTitle);
        imageView = view.findViewById(R.id.bookImage);
        progressText = view.findViewById(R.id.progressText);

        if(getArguments() != null) {
            displayBook(pagerBooks);
        }

        return view;
    }





    public void displayBook(final Book bookObj) {
        author = bookObj.getAuthor();
        title = bookObj.getTitle(); publisher = bookObj.getPublished();
        textView.setText(" \"" + title + "\" ");
        textView.append(", " + author);
        textView.append(", " + publisher);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        String imageURL = bookObj.getCoverURL();
        Picasso.get().load(imageURL).into(imageView);
        seekBar.setMax(bookObj.getDuration());

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookDetailsInterface) context).playBook(bookObj.getId());
                ((BookDetailsInterface) context).setProgress(progressHandler);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BookDetailsInterface) context).pauseBook();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(0);
                progressText.setText("0s");
                ((BookDetailsInterface) context).stopBook();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    progressText.setText("" + progress+"s");
                    ((BookDetailsInterface) context).seekBook(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    Handler progressHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            updateSeekbar(msg.what);
            return false;
        }
    });

    public void updateSeekbar(int time){
        seekBar.setProgress(time);
        Log.d("Progress", ":" + seekBar.getProgress());
        progressText.setText(""+ time+"s");
    }

    public interface  BookDetailsInterface{
        void playBook(int id);
        void pauseBook();
        void stopBook();
        void seekBook(int position);
        void setProgress(Handler progress);
    }

    }



