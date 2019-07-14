package pe.edu.cibertec.retrofitgitflow.presentation.post_detail.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.cibertec.retrofitgitflow.R;
import pe.edu.cibertec.retrofitgitflow.data.entities.Post;
import pe.edu.cibertec.retrofitgitflow.domain.post_detail_interactor.PostDetailInteractorImpl;
import pe.edu.cibertec.retrofitgitflow.presentation.post_detail.IPostDetailContrac;
import pe.edu.cibertec.retrofitgitflow.presentation.post_detail.presenter.PostPresenter;

public class PostDetailActivity extends AppCompatActivity implements IPostDetailContrac.IView {
    private PostPresenter presenter;
    private ProgressBar progressBar;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        textView = findViewById(R.id.textView);
        presenter= new PostPresenter( new PostDetailInteractorImpl());
        presenter.attachView(this);
        progressBar = findViewById(R.id.progressBarMain);

        int valor = getIntent().getExtras().getInt("post_id");
        presenter.getpostId(valor);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
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
    public void getAllPostSuccess(Post post) {
        textView.setText(post.toString());
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
