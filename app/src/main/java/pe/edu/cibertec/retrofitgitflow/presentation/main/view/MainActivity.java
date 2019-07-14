package pe.edu.cibertec.retrofitgitflow.presentation.main.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.cibertec.retrofitgitflow.R;
import pe.edu.cibertec.retrofitgitflow.data.entities.Post;
import pe.edu.cibertec.retrofitgitflow.domain.main_interactor.MainInteractorImpl;
import pe.edu.cibertec.retrofitgitflow.network.JsonPlaceHolderApi;
import pe.edu.cibertec.retrofitgitflow.presentation.main.view.presenter.MainPresenter;
import pe.edu.cibertec.retrofitgitflow.presentation.post_detail.view.PostDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements IMainContract.IView{

    private TextView textViewResult;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private ProgressBar progressBar;
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter= new MainPresenter( new MainInteractorImpl());
        presenter.attachView(this);

        textViewResult = findViewById(R.id.textViewResult);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        progressBar = findViewById(R.id.progressBarMain);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList);
        postAdapter.setOnItemClickListener(new PostClickListener() {
            @Override
            public void onClick(int position) {
                getToDetailPost(postList.get(position).getId());
            }
        });
        recyclerViewPosts.setAdapter(postAdapter);
        //callService();
        presenter.getAllpost();
    }

    private void callService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setVisibility(View.VISIBLE);
                    textViewResult.setText("Code: " + response.code());
                } else {
                    List<Post> posts = response.body();
                    postList.addAll(posts);
                    postAdapter.notifyDataSetChanged();
                    /*for (Post post: postList) {
                        String content = "";
                        content += "Id: " + post.getId() + "\n";
                        content += "userId: " + post.getUserId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Body: " + post.getText() + "\n\n";
                        textViewResult.append(content);
                    }*/

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setVisibility(View.VISIBLE);
                textViewResult.setText(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this,"",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getAllPostSuccess(List<Post> postList) {
        this.postList.addAll(postList);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void getToDetailPost(int postId) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("post_id",postId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }
}
