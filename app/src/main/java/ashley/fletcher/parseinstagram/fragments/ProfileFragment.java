package ashley.fletcher.parseinstagram.fragments;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import ashley.fletcher.parseinstagram.Post;

import static ashley.fletcher.parseinstagram.Post.KEY_CREATED_AT;

public class ProfileFragment extends PostsFragment {

   @Override
    protected void queryPosts() {
       ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);

       postQuery.include(Post.KEY_USER);
       postQuery.setLimit(20);
       postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
       postQuery.addDescendingOrder(KEY_CREATED_AT);
       postQuery.findInBackground(new FindCallback<Post>() {
           @Override
           public void done(List<Post> posts, ParseException e) {
               if(e != null){
                   Log.e(TAG, "Error with query");
                   e.printStackTrace();
                   return;
               }
               nPosts.addAll(posts);
               adapter.notifyDataSetChanged();

               for (int i = 0;i < posts.size(); i++) {
                   Post post = posts.get(i);
                   Log.d(TAG, "Post: "+ posts.get(i).getDescription() + " username: " + post.getUser().getUsername());
               }
           }
       });
    }
}
