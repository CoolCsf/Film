package com.wrj.film.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tool.R;
import com.tool.util.ScreenUtils;
import com.wrj.film.BR;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/12/7.
 */

public class BDRVFastAdapter<VM, BD extends ViewDataBinding> extends BaseQuickAdapter<VM, BDRVFastAdapter.BDBaseViewHolder> {
    private int[] childId;
    private int staggeredViewId = 0;
    private int variableId = 0;
    private Object variable = null;

    public BDRVFastAdapter(int layoutResId, @Nullable List<VM> data) {
        super(layoutResId, data);
    }

    public BDRVFastAdapter(int layoutResId, @Nullable List<VM> data, int... chidClickId) {
        super(layoutResId, data);
        childId = chidClickId;
    }

    public void setVariable(int id, Object object) {
        this.variableId = id;
        variable = object;
    }

    public void setStaggeredViewId(int staggeredViewId) {
        this.staggeredViewId = staggeredViewId;
    }


    @Override
    protected void convert(BDRVFastAdapter.BDBaseViewHolder helper, VM item) {
        helper.getBinding().setVariable(BR.data, item);
        if (variableId != 0 && variable != null) {
            helper.getBinding().setVariable(variableId, variable);
        }
        if (childId != null && childId.length > 0) {
            for (Integer id : childId) {
                if (id != 0)
                    helper.addOnClickListener(id);
            }
        }
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
        public BDBaseViewHolder(View superView) {
            super(superView);
//            if (staggeredViewId != 0) {
//                View view = superView.findViewById(staggeredViewId);
//                ViewGroup.LayoutParams params = view.getLayoutParams();
//                params.height = 300 + (int) (Math.random() * 200);//200-300的随机数
//                view.setLayoutParams(params);
//            }
        }

        public BD getBinding() {
            return (BD) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
