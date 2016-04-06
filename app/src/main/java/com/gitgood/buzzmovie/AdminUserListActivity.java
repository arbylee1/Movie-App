package com.gitgood.buzzmovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

import java.util.List;
public class AdminUserListActivity extends AppCompatActivity {
    // configure back button to take us back to previus overall search ativity
    public final boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public final boolean onCreateOptionsMenu(Menu menu) {
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
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        // get list view from activity content
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listuser);
        //instantiate and set adapter
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        SharedPreferences userinfo = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        List <User> allUsers = CurrentUser.getInstance().getAllUsers(userinfo);
        recyclerView.setAdapter(new UserViewAdapter(allUsers));
    }

    @Override
    protected final void onNewIntent(Intent intent) {
    }

    public class UserViewAdapter
            extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {

        private final List<User> users;

        public UserViewAdapter(List<User> items) {
            users = items;
        }

        @Override
        public final ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_detail, null);
            return new ViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(final ViewHolder holder, int position) {
            Log.d("Hiooo", String.valueOf(position));
            User user = users.get(position);
            holder.movieItem = user;
            holder.movieIdView.setText(user.getUserName());
            holder.movieIdView.setTextSize(16);


            if (users.get(position).getBanStatus()) {
                holder.itemView.setBackgroundColor(Color.RED);
                holder.movieContentView.setText("blocked");
            } else {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.movieContentView.setText("active");
            }

            holder.itemView.setMinimumWidth(1000);
            holder.itemView.setMinimumHeight(160);
            holder.movieIdView.setPaddingRelative(25, 25, 25, 25);
            holder.movieContentView.setPaddingRelative(25, 75, 25, 0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 20);
            holder.itemView.setLayoutParams(lp);

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
                        holder.movieContentView.setText("blocked");
                    } else {
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        user.unBan();
                        holder.movieContentView.setText("active");
                    }
                }
            });
        }

        @Override
        public final int getItemCount() {
            return users.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final View movieView;
            private final TextView movieIdView;
            private final TextView movieContentView;
            private User movieItem;

            public ViewHolder(View view) {
                super(view);
                movieView = view;
                movieIdView = (TextView) view.findViewById(R.id.TextViewTitle);
                movieContentView = (TextView) view.findViewById(R.id.TextViewContent);
            }

            @Override
            public final String toString() {
                return super.toString() + " '" + movieContentView.getText() + "'";
            }
        }
    }
}
