package com.rsandoval.notitec.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.rsandoval.notitec.Application;
import com.rsandoval.notitec.R;
import com.rsandoval.notitec.models.Noticias;
import com.rsandoval.notitec.presenters.NoticiasContract;
import com.rsandoval.notitec.presenters.NoticiasPresenter;
import com.rsandoval.notitec.ui.adapters.NoticiasAdapter;
import com.rsandoval.notitec.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends BaseFragment implements NoticiasContract.View {

    @BindView(R.id.noticias)
    RecyclerView noticias;

    @BindView(R.id.loading)
    ProgressBar loading;

    private List<Noticias.Noticia> noticiaList = new ArrayList<>();
    private NoticiasAdapter adapter;
    private NoticiasContract.NoticiasListener noticiasListener;

    public NoticiasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        noticiasListener = new NoticiasPresenter(this,
                ((Application) getActivity().getApplicationContext()).getDatabaseReference(), getContext());
        noticiasListener.getNoticias();
        adapter = new NoticiasAdapter(noticiaList, getContext(), getFragmentManager());
        noticias.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        noticias.setAdapter(adapter);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_noticias;
    }

    @Override
    public void showSendNoticias(List<Noticias.Noticia> noticias) {
        noticiaList.clear();
        noticiaList.addAll(noticias);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        noticias.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        noticias.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }
}
