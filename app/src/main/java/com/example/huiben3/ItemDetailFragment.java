package com.example.huiben3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    /*
    屏幕宽度
     */
    private int windowWidth;
    /*
    屏幕高度
     */
    private int windowHeight;

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

            pageNum = mItem.paths.size();
            currentPosition = 0;
//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
        }
        windowWidth = getArguments().getInt("width");
        windowHeight = getArguments().getInt("height");
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
        final View rootView = inflater.inflate(R.layout.item_detail_image_switcher, container, false);
        if (mItem != null) {
            if (mImageSwitcher == null) {
                mImageSwitcher = rootView.findViewById(R.id.item_detail_image_switcher);
            }
            if (mImageSwitcher != null) {
                mImageSwitcher.setLeft(0);
                mImageSwitcher.setTop(0);
                //设置动画效果
                mImageSwitcher.setMinimumWidth(windowHeight);
                mImageSwitcher.setMinimumHeight(windowWidth);
                mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));//设置淡入动画
                mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));//设置淡出动画
                mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {//设置View工厂

                    @Override
                    public View makeView() {
                        ImageView imageView = new ImageView(mContext);//实例化一个ImageView类的对象
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);//设置保持纵横比居中缩放图像
                        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                        imageView.animate().rotation(90);
                        // imageView.setAdjustViewBounds(true);
                        imageView.setMinimumWidth(windowWidth);
                        imageView.setMinimumHeight(windowHeight);
                        // imageView.setFitsSystemWindows(true);
                        imageView.setLeft(0);
                        imageView.setTop(0);

                        Log.i("画布大小", "getMaxWidth=" + imageView.getWidth() + ";getMaxHeight=" + imageView.getHeight());
                        Log.i("画布大小", "getMinimumWidth=" + imageView.getMinimumWidth() + ";getMinimumHeight=" + imageView.getMinimumHeight());
                        return imageView;
                    }
                });

                setImage(mItem.paths.get(0).getPath());
            } else {
                Log.e("严重异常", "imageSwitcher初始化失败");
            }
        }
        setLayout(rootView, 0, 0);
        return rootView;
    }

    /*
     * 设置控件所在的位置XY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
        Log.d("设置控件所在的位置XY", x + " " + y + " " + margin.width + " " + margin.height);
    }

    @Override
    public View makeView() {
        final ImageView i = new ImageView(mContext);
        i.setBackgroundColor(Color.TRANSPARENT);

//        i.setBackgroundColor(0xf5000000);
//        i.setScaleType(ImageView.ScaleType.FIT_XY);//设置保持纵横比居中缩放图像
//        i.setLayoutParams(new ImageSwitcher.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        i.animate().rotation(90);
//        i.setAdjustViewBounds(true);
//        Log.i("makeView", "" + i.getWidth() + "-" + i.getHeight());
        return i;
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
            Log.i("触摸事件", "当前绘本：" + mItem.toString());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    //手指按下的X坐标
                    downX = event.getX();
                    downY = event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    Log.i("上一页", currentPosition + " -> " + (pageNum - 1));

                    float lastX = event.getX();
                    float lastY = event.getY();
                    Log.i("滑动方向", "X: " + downX + "->" + lastX + " Y: " + downY + "->" + lastY);
                    //抬起的时候的X坐标大于按下的时候就显示上一张图片
                    if (downX < lastX) {
                        if (currentPosition > 0) {
                            //设置动画，这里的动画比较简单，不明白的去网上看看相关内容
                            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
                            currentPosition--;
                            setImage(mItem.paths.get(currentPosition).getPath());
//                        mImageSwitcher.setImageResource(imgIds[currentPosition % imgIds.length]);
                            // setImageBackground(currentPosition);
                        } else {
                            Toast.makeText(mContext, "已经是第一张", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (currentPosition < pageNum - 1) {
                            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
                            currentPosition++;
                            setImage(mItem.paths.get(currentPosition).getPath());
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
        Log.i("图片大小", "getMaxWidth=" + bit.getWidth() + ";getMaxHeight=" + bit.getHeight());
        Drawable drawable = new BitmapDrawable(bit);
        mImageSwitcher.setImageDrawable(drawable);
    }

}
