package com.example.huiben3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.huiben3.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements ViewSwitcher.ViewFactory, View.OnTouchListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private Context mContext;

    /**
     * 当前选中的图片id序号
     */
    private static int currentPosition = 0;

    private int pageNum;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @SuppressLint("ValidFragment")
    public ItemDetailFragment(Context context) {
        this.mContext = context;
    }

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ARG_ITEM_ID is ", ARG_ITEM_ID);
        Log.i("aruments ", getArguments().toString());
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            pageNum = mItem.pathes.size();
            currentPosition = 0;
//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
        }
    }

    private ImageSwitcher mImageSwitcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.item_detail, container, false);
//
//        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
//        }
        View rootView = inflater.inflate(R.layout.item_detail_image_switcher, container, false);
        if (mItem != null) {
            if (mImageSwitcher == null) {
                mImageSwitcher = rootView.findViewById(R.id.item_detail_image_switcher);
            }

            //设置动画效果
            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));//设置淡入动画
            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));//设置淡出动画
            mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {//设置View工厂

                @Override
                public View makeView() {
                    ImageView imageView = new ImageView(mContext);//实例化一个ImageView类的对象
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置保持纵横比居中缩放图像
                    imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    imageView.animate().rotation(90);
                    return imageView;
                }
            });

            setImage(mItem.pathes.get(0).getPath());
        }
        return rootView;
    }

    /**
     * 按下点的X坐标
     */
    private float downX;
    /**
     * 按下点的Y坐标
     */
    private float downY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        {
            Log.i("触摸事件", "当前绘本" + mItem.toString());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    //手指按下的X坐标
                    downX = event.getX();
                    downY = event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    float lastX = event.getX();
                    float lastY = event.getY();
                    Log.i("滑动方向", downX + " -> " + lastX);
                    //抬起的时候的X坐标大于按下的时候就显示上一张图片
                    if (lastX < downX) {
                        if (currentPosition > 0) {
                            //设置动画，这里的动画比较简单，不明白的去网上看看相关内容
                            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
                            currentPosition--;
                            setImage(mItem.pathes.get(currentPosition).getPath());
//                        mImageSwitcher.setImageResource(imgIds[currentPosition % imgIds.length]);
                            // setImageBackground(currentPosition);
                        } else {
                            Toast.makeText(mContext, "已经是第一张", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.i("上一页", currentPosition + " -> " + (pageNum - 1));
                        if (currentPosition < pageNum - 1) {
                            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
                            currentPosition++;
                            setImage(mItem.pathes.get(currentPosition).getPath());
//                        mImageSwitcher.setImageResource(imgIds[currentPosition]);
                            // setImageBackground(currentPosition);
                        } else {
                            Toast.makeText(mContext, "到了最后一张", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            return true;
        }
    }

    private void setImage(String pagePath) {
        Log.i("dev-tag:PlaceholderFragment", "pagePath=" + pagePath);
        Bitmap bit = BitmapFactory.decodeFile(pagePath);
        Drawable drawable = new BitmapDrawable(bit);
        mImageSwitcher.setImageDrawable(drawable);
    }

    @Override
    public View makeView() {
        final ImageView i = new ImageView(mContext);
        i.setBackgroundColor(0xff000000);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        i.animate().rotation(90);
        return i;
    }
}
