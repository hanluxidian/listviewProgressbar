package com.example.recyclerviewmultiitemtype;

import android.content.Context;
import android.media.MediaDataSource;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    List<Image> mDatas;
    Context context;
    OnItemClickLitener mOnItemClickLitener;

    public MyAdapter(List<Image> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case Image.TYPE_One:
                View view = LayoutInflater.from(context).inflate(R.layout.item_one, parent, false);

                return new oneImageHolder(view);
            case Image.TYPE_Two:
                View view2 = LayoutInflater.from(context).inflate( R.layout.item_two, parent, false);
                return new TwoImageHolder(view2);
            case Image.TYPE_Three:
                View view3 = LayoutInflater.from(context).inflate( R.layout.item_three, parent, false);
                return new ThreeImageHolder(view3);
            default:break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("hanluAdapter", "onBindViewHolder");
        final int position1 = position;
        switch (mDatas.get(position).getType()){
            case Image.TYPE_One:
                ((oneImageHolder)holder).bindView(position1);
//                if (mOnItemClickLitener != null) {
//                    ((oneImageHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mOnItemClickLitener.onItemClick(v, position1);
//                        }
//                    });
//                    ((oneImageHolder)holder).layout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            mOnItemClickLitener.onItemClick(view, position1);
//                        }
//                    });
//                }
            break;
            case Image.TYPE_Two:
                ((TwoImageHolder)holder).bindView(position);
            break;
            case Image.TYPE_Three:
                ((ThreeImageHolder)holder).bindView(position);
            break;
            default:break;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }

     class oneImageHolder extends RecyclerView.ViewHolder{
        ImageView imag_1;
        ConstraintLayout layout;

        public oneImageHolder(@NonNull View itemView) {
            super(itemView);
            imag_1 = (ImageView) itemView.findViewById(R.id.imageview_one);
            layout = (ConstraintLayout) itemView.findViewById(R.id.one_constraintlayout);
            layout.setOnClickListener(mOnclickListener);
        }

        public void bindView(int position){
            oneImageBean ll = (oneImageBean) mDatas.get(position);
        }
    }

     class TwoImageHolder extends RecyclerView.ViewHolder{
         ImageView imag_1;
         ImageView imag_2;
         ConstraintLayout layout;

         public TwoImageHolder(@NonNull View itemView) {
             super(itemView);
             imag_1 = (ImageView) itemView.findViewById(R.id.imageview_two_1);
             imag_2 = (ImageView) itemView.findViewById(R.id.imageview_two_2);
             layout = (ConstraintLayout) itemView.findViewById(R.id.one_constraintlayout);
         }

         public void bindView(int position){
             twoImageBean ll = (twoImageBean) mDatas.get(position);
         }

    }

     class ThreeImageHolder extends RecyclerView.ViewHolder{
         ImageView imag_1;
         ImageView imag_2;
         ImageView imag_3;
         ConstraintLayout layout;

         public ThreeImageHolder(@NonNull View itemView) {
             super(itemView);
             imag_1 = (ImageView) itemView.findViewById(R.id.imageview_three_1);
             imag_2 = (ImageView) itemView.findViewById(R.id.imageview_three_2);
             imag_3 = (ImageView) itemView.findViewById(R.id.imageview_three_3);
             layout = (ConstraintLayout) itemView.findViewById(R.id.one_constraintlayout);
         }

         public void bindView(int position){
             threeImageBean ll = (threeImageBean) mDatas.get(position);
         }
    }

    //设置回调接口
    public interface OnItemClickLitener {
        void onItemClick(View view);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mOnItemClickLitener != null) {
                mOnItemClickLitener.onItemClick(v);
            }

        }
    };

}
