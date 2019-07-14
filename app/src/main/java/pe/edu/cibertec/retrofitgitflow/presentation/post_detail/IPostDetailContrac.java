package pe.edu.cibertec.retrofitgitflow.presentation.post_detail;

import pe.edu.cibertec.retrofitgitflow.data.entities.Post;

public interface IPostDetailContrac {
    interface IView {
        void showError(String errorMsg);
        void showProgressBar();
        void hideProgressBar();
        void getAllPostSuccess(Post postList);
    }
    interface  IPresenter {
        void attachView(IPostDetailContrac.IView view);
        void detachView();
        boolean isViewAttached();
        void getpostId(int id);
    }
}
