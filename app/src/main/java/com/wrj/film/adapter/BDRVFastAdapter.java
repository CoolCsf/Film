package com.wrj.film.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tool.R;
import com.wrj.film.BR;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BDRVFastAdapter<VM, BD extends ViewDataBinding> extends BaseQuickAdapter<VM, BDRVFastAdapter.BDBaseViewHolder> {

    public BDRVFastAdapter(int layoutResId, @Nullable List<VM> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BDRVFastAdapter.BDBaseViewHolder helper, VM item) {
        helper.getBinding().setVariable(BR.data, item);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    class BDBaseViewHolder extends BaseViewHolder {
        public BDBaseViewHolder(View view) {
            super(view);
        }

        public BD getBinding() {
            return (BD) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
