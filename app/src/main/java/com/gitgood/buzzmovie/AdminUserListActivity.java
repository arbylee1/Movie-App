package com.gitgood.buzzmovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        // get list view from activity content
        recyclerView = (RecyclerView) findViewById(R.id.listuser);
        //instantiate and set adapter
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        SharedPreferences userinfo = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        List <User> allUsers = CurrentUser.getInstance().getAllUsers(userinfo);
        recyclerView.setAdapter(new UserViewAdapter(allUsers));
    }

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

    public class UserViewAdapter
            extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {

        private final List<User> Users;

        public UserViewAdapter(List<User> items) {
            Users = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_detail, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Log.d("Hiooo", String.valueOf(position));
            User user = Users.get(position);
            holder.movieItem = user;
            holder.movieIdView.setText(user.getUserName());
            holder.movieContentView.setText(user.getUserName() + " | LOCKED: " + user.getBanStatus());

            if (Users.get(position).getBanStatus() == true) {
                holder.itemView.setBackgroundColor(Color.RED);
            }

            holder.movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = holder.movieItem;
                    SharedPreferences userinfo = getSharedPreferences(
                            getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
                    Boolean banned = CurrentUser.getInstance().toggleIsBan(userinfo, holder.movieItem.getUserName());
                    Log.d("Hi", String.valueOf(System.identityHashCode(holder.movieItem)));
                    if (banned) {
                        holder.itemView.setBackgroundColor(Color.RED);
                        user.ban();
                        holder.movieContentView.setText(user.getUserName() + " | LOCKED: " + user.getBanStatus());
                    } else {
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        user.unBan();
                        holder.movieContentView.setText(user.getUserName() + " | LOCKED: " + user.getBanStatus());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return Users.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View movieView;
            public final TextView movieIdView;
            public final TextView movieContentView;
            public User movieItem;

            public ViewHolder(View view) {
                super(view);
                movieView = view;
                movieIdView = (TextView) view.findViewById(R.id.TextViewTitle);
                movieContentView = (TextView) view.findViewById(R.id.TextViewContent);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + movieContentView.getText() + "'";
            }
        }
    }
}
