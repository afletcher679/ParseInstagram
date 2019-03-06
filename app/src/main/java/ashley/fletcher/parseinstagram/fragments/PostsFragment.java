package ashley.fletcher.parseinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ashley.fletcher.parseinstagram.Post;
import ashley.fletcher.parseinstagram.PostsAdapter;
import ashley.fletcher.parseinstagram.R;

import static ashley.fletcher.parseinstagram.Post.KEY_CREATED_AT;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> nPosts;

    private SwipeRefreshLayout swipeContainer;

    //onCreateView to inflate the view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPosts);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        //Create the data source
        nPosts = new ArrayList<>();
        //Create the adapter
        adapter = new PostsAdapter(getContext(), nPosts);
        //Set the adapter to the recycler view
        rvPosts.setAdapter((adapter));
        //Set the layout manager to the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Log.d("PostsFragment", "content is being refreshed");

                adapter.clear();

                queryPosts();

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);

            }
        });



    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);

        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                nPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " + posts.get(i).getDescription() + " username: " + post.getUser().getUsername());
                }
            }
        });
    }
}

