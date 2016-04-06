package com.gitgood.buzzmovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.view.KeyEvent;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // get list view from activity content
        View recyclerView = findViewById(R.id.list);
        //instantiate and set adapter
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    final public boolean onCreateOptionsMenu(Menu menu) {
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
    final protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //use the query to search
        }
    }

    // configure back button to take us back to previus overall search ativity
    final public boolean onKeyDown(int keyCode, KeyEvent event)
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
        final public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        final public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.movieItem = movieValues.get(position);
            holder.movieIdView.setText(movieValues.get(position).getMovie());
            holder.movieContentView.setText(movieValues.get(position).toString());

            holder.movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    Log.d("Hi", String.valueOf(System.identityHashCode(holder.movieItem)));
                    intent.putExtra("Movie", holder.movieItem.getRottenTomatoID());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        final public int getItemCount() {
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
            final public String toString() {
                return super.toString() + " '" + movieContentView.getText() + "'";
            }
        }
    }
}
