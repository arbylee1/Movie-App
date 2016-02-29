package com.gitgood.buzzmovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        handleIntent(getIntent());

        // get all movies from intent.extra and get all the titles to be display in list
        movies = (ArrayList<Movie>) getIntent().getSerializableExtra("SEARCH");
        for(Movie movie : movies) {
            Movies.addItem(movie);
        }
        // get list view from acitivity content
        View recyclerView = findViewById(R.id.list);
        //instantie and set adapter
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
        }
    }

    // configure back button to take us back to previus overall search ativity
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(new MovieViewAdapter(Movies.ITEMS));
    }

    public class MovieViewAdapter
            extends RecyclerView.Adapter<MovieViewAdapter.ViewHolder> {

        private final List<Movie> movieValues;

        public MovieViewAdapter(List<Movie> items) {movieValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.movieItem = movieValues.get(position);
            holder.movieIdView.setText(movieValues.get(position).getMovie());
            holder.movieContentView.setText(movieValues.get(position).toString());

            holder.movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("Movie", holder.movieItem.getRottenTomatoID());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View movieView;
            public final TextView movieIdView;
            public final TextView movieContentView;
            public Movie movieItem;

            public ViewHolder(View view) {
                super(view);
                movieView = view;
                movieIdView = (TextView) view.findViewById(R.id.detailTextViewTitle);
                movieContentView = (TextView) view.findViewById(R.id.detailTextViewContent);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + movieContentView.getText() + "'";
            }
        }
    }
}
