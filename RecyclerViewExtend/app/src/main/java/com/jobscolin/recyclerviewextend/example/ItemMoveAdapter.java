package com.jobscolin.recyclerviewextend.example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jobscolin.recyclerviewextend.R;
import com.jobscolin.recyclerviewextend.itemmove.ActiveViewHolder;
import com.jobscolin.recyclerviewextend.itemmove.FixedViewHolder;

import java.util.List;

/**
 * @author : xdl
 * @time : 2019/11/21
 * @describe :
 */
public class ItemMoveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mImages;
    public static final int ADD = 100;
    public static final int NORMAL = 101;
    private Context mContext;
    private AddImageListener mAddImageListener;
    private DeleteImageListener mDeleteImageListener;

    public ItemMoveAdapter(List<String> images, Context mContext) {
        this.mContext = mContext;
        mImages = images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_touch_layout_item, viewGroup, false);
        switch (viewType) {
            case ADD:
                return new AddViewHolder(view);
            case NORMAL:
                return new MoveViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof AddViewHolder) {
            Glide.with(mContext).load(R.drawable.add).into(((AddViewHolder) viewHolder).mImageView);
            ((AddViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAddImageListener != null) {
                        mAddImageListener.click();
                    }
                }
            });
        } else if (viewHolder instanceof MoveViewHolder) {
            ((MoveViewHolder) viewHolder).delete.setVisibility(View.VISIBLE);
            ((MoveViewHolder) viewHolder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteImageListener != null) {
                        mDeleteImageListener.click();
                    }
                }
            });
            RequestOptions requestOptions = new RequestOptions().centerCrop();
            Glide.with(mContext).load(mImages.get(position)).apply(requestOptions)
                    .into(((MoveViewHolder) viewHolder).mImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mImages == null || mImages.size() == 0) {
            return 1;
        }
        return mImages.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == mImages.size() - 1) {
            return ADD;
        } else {
            return NORMAL;
        }
    }



    class AddViewHolder extends FixedViewHolder {
        ImageView mImageView;

        public AddViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.touch_recycler_image);
        }
    }

    class MoveViewHolder extends ActiveViewHolder {
        ImageView mImageView;
        ImageButton delete;

        public MoveViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.touch_recycler_image);
            delete = itemView.findViewById(R.id.touch_recycler_delete);
        }
    }

    public interface AddImageListener {
        void click();
    }

    public interface DeleteImageListener {
        void click();
    }

    public void setAddImageListener(AddImageListener addImageListener) {
        mAddImageListener = addImageListener;
    }

    public void setDeleteImageListener(DeleteImageListener deleteImageListener) {
        mDeleteImageListener = deleteImageListener;
    }


}
