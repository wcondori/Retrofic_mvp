package pe.edu.cibertec.retrofitgitflow.presentation.post_detail.presenter;

import pe.edu.cibertec.retrofitgitflow.data.entities.Post;
import pe.edu.cibertec.retrofitgitflow.domain.post_detail_interactor.IPostDetailInteractor;
import pe.edu.cibertec.retrofitgitflow.presentation.post_detail.IPostDetailContrac;

public class PostPresenter implements IPostDetailContrac.IPresenter {
    IPostDetailContrac.IView view ;
    IPostDetailInteractor interactor;
    public PostPresenter (IPostDetailInteractor iMainInteractor){
        interactor = iMainInteractor;
    }

    @Override
    public void attachView(IPostDetailContrac.IView view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }

    @Override
    public boolean isViewAttached() {
        return this.view != null;
    }

    @Override
    public void getpostId(int id) {
        this.view.showProgressBar();
        interactor.getPostId(id, new IPostDetailInteractor.PostDetailCallBack() {
            @Override
            public void onSuccess(Post post) {
                if(isViewAttached()) {
                    view.getAllPostSuccess(post);
                    view.hideProgressBar();
                }
            }

            @Override
            public void onError(String errorMsg) {
                if(isViewAttached()) {
                    view.showError(errorMsg);
                    view.hideProgressBar();
                }
            }
        });
    }
}
