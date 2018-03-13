package com.example.captain.compass.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by captain on 2018/1/28.
 */

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormsViewHolder> {
    private List<Form> forms = null;
    private List<Form> checkedForms = new ArrayList<>();
    private OnStateChangeListener onStateChangeListener = null;
    private OnOperateClickableChangeListener onOperateClickableChangeListener = null;
    private OnEditModeChangeListener onEditModeChangeListener = null;
    private Context context;
    private boolean isEditMode = false;

    public FormListAdapter(Context context, List<Form> forms) {
        this.forms = forms;
        this.context = context;
    }

    @Override
    public FormsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_form, parent, false);
        return new FormsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FormsViewHolder holder, int position) {
        Form form = forms.get(position);
        holder.tvFormId.setText(form.getFormId());
        holder.tvReceiverName.setText(form.getReceiverName());
        holder.tvReceiverTel.setText(form.getReceiverTel());
        holder.tvReceiverAddress.setText(form.getReceiverAddress());
        String strState = "";
        if (form.getState() == Constant.FORM_STATE_DELIVERYING) {
            strState = "配送中";
        } else if (form.getState() == Constant.FORM_STATE_RECEIVED) {
            strState = "已签收";
        } else if (form.getState() == Constant.FORM_STATE_REJECTED) {
            strState = "已拒收";
        } else if (form.getState() == Constant.FORM_STATE_RECEIVER_UNCONTACTABLE) {
            strState = "联系不到收件人";
        } else if (form.getState() == Constant.FORM_STATE_PACKAGE_BROKEN) {
            strState = "包裹破损";
        } else if (form.getState() == Constant.FORM_STATE_PACKAGE_MISSED) {
            strState = "包裹丢失";
        } else if (form.getState() == Constant.FORM_STATE_SECOND_DELIVERY) {
            strState = "二次配送";
        }
        holder.tvFormState.setText(strState);
        holder.btnUnreceived.setEnabled(form.getState() == Constant.FORM_STATE_DELIVERYING);
        holder.btnReceived.setEnabled(form.getState() == Constant.FORM_STATE_DELIVERYING);
        holder.btnReceived.setOnClickListener(
                v -> onStateChangeListener.onStateChanged(form, Constant.FORM_STATE_RECEIVED, position));
        holder.checkBox.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        holder.checkBox.setChecked(form.isChecked());
        holder.btnUnreceived.setOnClickListener(v -> {
            String[] items = new String[]{"无理由拒收", "联系不到收件人", "二次派送", "包裹破损", "包裹丢失"};
            new AlertDialog.Builder(context)
                    .setItems(items, (dialog, which) -> {
                        int state = form.getState();
                        if (which == 0)
                            state = Constant.FORM_STATE_REJECTED;
                        else if (which == 1)
                            state = Constant.FORM_STATE_RECEIVER_UNCONTACTABLE;
                        else if (which == 2)
                            state = Constant.FORM_STATE_SECOND_DELIVERY;
                        else if (which == 3)
                            state = Constant.FORM_STATE_PACKAGE_BROKEN;
                        else if (which == 4)
                            state = Constant.FORM_STATE_PACKAGE_MISSED;
                        onStateChangeListener.onStateChanged(form, state, position);
                    }).create().show();

        });
        holder.itemView.setOnLongClickListener(view -> {
            if (!isEditMode) {
                checkedForms.add(form);
                form.setChecked(true);
                setEditMode(true);
                onOperateClickableChangeListener.onOperateClickableChange(true);
                notifyDataSetChanged();
                return true;
            }
            return false;
        });
        holder.itemView.setOnClickListener(view -> {
            form.setChecked(!form.isChecked());
            if (checkedForms.contains(form)) {
                checkedForms.remove(form);
            } else
                checkedForms.add(form);
            onOperateClickableChangeListener.onOperateClickableChange(checkedForms.size() > 0);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public class FormsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_form_id)
        TextView tvFormId;

        @BindView(R.id.tv_form_state)
        TextView tvFormState;

        @BindView(R.id.tv_receiver_name)
        TextView tvReceiverName;

        @BindView(R.id.tv_receiver_tel)
        TextView tvReceiverTel;

        @BindView(R.id.tv_receiver_address)
        TextView tvReceiverAddress;

        @BindView(R.id.btn_received)
        Button btnReceived;

        @BindView(R.id.btn_unreceived)
        Button btnUnreceived;

        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public FormsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        if (editMode == this.isEditMode)
            return;
        isEditMode = editMode;
        onEditModeChangeListener.onEditModeChange(isEditMode);
        //一单退出编辑模式，所有已选中的全部清空
        if (!isEditMode) {
            for (Form f : checkedForms)
                f.setChecked(false);
            checkedForms.clear();
        }
        notifyDataSetChanged();
    }

    public void setOperateClickable(boolean clickable) {
        onOperateClickableChangeListener.onOperateClickableChange(clickable);
    }

    //订单状态变化监听
    public interface OnStateChangeListener {
        void onStateChanged(Form form, int state, int position);
    }

    public OnStateChangeListener getOnStateChangeListener() {
        return onStateChangeListener;
    }

    public void setOnStateChangeListener(OnStateChangeListener stateChangeListener) {
        this.onStateChangeListener = stateChangeListener;
    }


    public interface OnOperateClickableChangeListener {
        void onOperateClickableChange(boolean isClickable);
    }

    public void setOnOperateClickableChangeListener(OnOperateClickableChangeListener onOperateClickableChangeListener) {
        this.onOperateClickableChangeListener = onOperateClickableChangeListener;
    }

    public OnOperateClickableChangeListener getOnOperateClickableChangeListener() {
        return onOperateClickableChangeListener;
    }

    public interface OnEditModeChangeListener {
        void onEditModeChange(boolean isEditMode);
    }

    public void setOnEditModeChangeListener(OnEditModeChangeListener onEditModeChangeListener) {
        this.onEditModeChangeListener = onEditModeChangeListener;
    }

    public OnEditModeChangeListener getOnEditModeChangeListener() {
        return onEditModeChangeListener;
    }

    public List<Form> getCheckedForms() {
        return checkedForms;
    }

}
