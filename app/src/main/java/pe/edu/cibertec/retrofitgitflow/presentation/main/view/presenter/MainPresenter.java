package pe.edu.cibertec.retrofitgitflow.presentation.main.view.presenter;

import java.util.List;

import pe.edu.cibertec.retrofitgitflow.data.entities.Post;
import pe.edu.cibertec.retrofitgitflow.domain.main_interactor.IMainInteractor;
import pe.edu.cibertec.retrofitgitflow.presentation.main.view.IMainContract;

public class MainPresenter implements IMainContract.IPresenter {
    IMainContract.IView view ;
    IMainInteractor interactor;

    public MainPresenter (IMainInteractor iMainInteractor){
        interactor = iMainInteractor;
    }

    @Override
    public void attachView(IMainContract.IView view) {
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
    public void getAllpost() {
        this.view.showProgressBar();
        interactor.getAllPost(new IMainInteractor.MainCallBack() {
            @Override
            public void onSuccess(List<Post> postList) {
                if(isViewAttached()) {
                    view.getAllPostSuccess(postList);
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
        //
    }


}
