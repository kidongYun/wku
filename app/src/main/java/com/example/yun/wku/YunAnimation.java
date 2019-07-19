package com.example.yun.wku;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class YunAnimation {
    protected View targetView;
    protected int SENSITIVITY = 5;

    public YunAnimation(View targetView) {
        this.targetView = targetView;
    }

    public void start() {
        Log.i("WKU", "YunAnimation / start()!!!");
    }

    // OCCUR THIS FUNCTION WHEN YOU DITACH YOUR HAND ON DISPLAY
    public void actionUp() {
        Log.i("WKU", "YunAnimation / actionUp()!!!");
    }

    public void setAnimationSensitivity(int sensitivity) {
        this.SENSITIVITY = sensitivity;
    }
}

class YunScaleAnimation extends YunAnimation {
    private int fromWidth;
    private int fromHeight;
    private int toWidth;
    private int toHeight;

    private int HEIGHT_SENSITIVITY;
    private int WIDTH_SENSITIVITY;

    public YunScaleAnimation(View targetView, int fromWidth, int fromHeight, int toWidth, int toHeight) {
        super(targetView);

        this.fromWidth = fromWidth;
        this.fromHeight = fromHeight;
        this.toWidth = toWidth;
        this.toHeight = toHeight;

        this.WIDTH_SENSITIVITY = super.SENSITIVITY;
        this.HEIGHT_SENSITIVITY = super.SENSITIVITY;
    }

    public void start() {

        android.view.ViewGroup.LayoutParams targetViewLayoutParams = targetView.getLayoutParams();

        if(fromHeight > toHeight) {
            if(targetViewLayoutParams.height >= toHeight + HEIGHT_SENSITIVITY) {
                targetViewLayoutParams.height = targetViewLayoutParams.height - HEIGHT_SENSITIVITY;
            } else if(targetViewLayoutParams.height < (toHeight + HEIGHT_SENSITIVITY) && targetViewLayoutParams.height > toHeight) {
                targetViewLayoutParams.height = toHeight;
            }
        } else {
            if(targetViewLayoutParams.height <= toHeight - HEIGHT_SENSITIVITY) {
                targetViewLayoutParams.height = targetViewLayoutParams.height + HEIGHT_SENSITIVITY;
            } else if(targetViewLayoutParams.height > (toHeight + HEIGHT_SENSITIVITY) && targetViewLayoutParams.height < toHeight) {
                targetViewLayoutParams.height = toHeight;
            }
        }

        if(fromWidth > toWidth) {
            if(targetViewLayoutParams.width >= toWidth + WIDTH_SENSITIVITY) {
                targetViewLayoutParams.width = targetViewLayoutParams.width - WIDTH_SENSITIVITY;
            } else if(targetViewLayoutParams.width < (toWidth + WIDTH_SENSITIVITY) && targetViewLayoutParams.width > toWidth) {
                targetViewLayoutParams.width = toWidth;
            }
        } else {
            if(targetViewLayoutParams.width <= toWidth - WIDTH_SENSITIVITY) {
                targetViewLayoutParams.width = targetViewLayoutParams.width + WIDTH_SENSITIVITY;
            } else if(targetViewLayoutParams.width > (toWidth + WIDTH_SENSITIVITY) && targetViewLayoutParams.width < toWidth) {
                targetViewLayoutParams.width = toWidth;
            }
        }

        targetView.setLayoutParams(targetViewLayoutParams);
    }

    // OCCUR THIS FUNCTION WHEN YOU DETACH YOUR HAND ON DISPLAY
    public void actionUp() {
        android.view.ViewGroup.LayoutParams targetViewLayoutParams = targetView.getLayoutParams();

        if(fromWidth != toWidth) {
            targetViewLayoutParams.width = toWidth;
        }

        if(fromHeight != toHeight) {
            targetViewLayoutParams.height = toHeight;
        }

        targetView.setLayoutParams(targetViewLayoutParams);
    }

    public void setWidthSensitivity(int sensitivity) {
        this.WIDTH_SENSITIVITY = sensitivity;
    }
    public void setHeightSensitivity(int sensitivity) {
        this.HEIGHT_SENSITIVITY = sensitivity;
    }
}

class YunAnimationController{

    private final int SCROLL_UP_STATE = 1;
    private final int SCROLL_DOWN_STATE = 2;
    private final int SCROLL_LEFT_STATE = 4;
    private final int SCROLL_RIGHT_STATE = 8;
    private final int NULL_STATE = 16;

    private View contorllerView;

    private int SENSITIVITY = 5;
    private int ACTION_UP_SENSITIVITY = 30;

    private int x = 0;
    private int y = 0;

    private int prevX;
    private int prevY;

    private int state = NULL_STATE;

    private int stateValueX = 0;
    private int stateValueY = 0;

    private boolean IS_FIRST_FLAG = false;

    private ArrayList<YunAnimation> scrollUpAnimations =  null;
    private ArrayList<YunAnimation> scrollDownAnimations = null;
    private ArrayList<YunAnimation> scrollLeftAnimations = null;
    private ArrayList<YunAnimation> scrollRightAnimations = null;

    public YunAnimationController(View contorllerView) {
        this.contorllerView = contorllerView;

        scrollUpAnimations = new ArrayList<>();
        scrollDownAnimations = new ArrayList<>();
        scrollLeftAnimations = new ArrayList<>();
        scrollRightAnimations = new ArrayList<>();
    }

    public void addAnimation(YunAnimation scrollUpAnimation, YunAnimation scrollDownAnimation, YunAnimation scrollLeftAnimation, YunAnimation scrollRightAnimation) {
        this.scrollUpAnimations.add(scrollUpAnimation);
        this.scrollDownAnimations.add(scrollDownAnimation);
        this.scrollLeftAnimations.add(scrollLeftAnimation);
        this.scrollRightAnimations.add(scrollRightAnimation);
    }

    public void start() {
        onListening();
    }

    private void onListening() {
        contorllerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE :

                        if(!IS_FIRST_FLAG) {
                            setXY(motionEvent);
                            IS_FIRST_FLAG = true;
                        } else {
                            setPrevXY();
                            setXY(motionEvent);
                            setState();
                        }

                        if(prevY > y + SENSITIVITY) {
                            // occur Scroll Up
                            scrollUp();

                        } else if(prevY + SENSITIVITY < y) {
                            // occur Scroll Down
                            scrollDown();

                        } else if(prevX > x + SENSITIVITY) {
                            // occur Scroll Left
                            scrollLeft();

                        } else if(prevX + SENSITIVITY < x) {
                            // occur Scroll Right
                            scrollRight();

                        }
                        break;

                    case MotionEvent.ACTION_UP :

                        if(state == SCROLL_LEFT_STATE) {
                            for(int i=0; i<scrollLeftAnimations.size(); i++) {
                                if(scrollLeftAnimations.get(i) != null) {
                                    scrollLeftAnimations.get(i).actionUp();
                                }
                            }
                        } else if(state == SCROLL_RIGHT_STATE) {
                            for(int i=0; i<scrollRightAnimations.size(); i++) {
                                if(scrollRightAnimations.get(i) != null) {
                                    scrollRightAnimations.get(i).actionUp();
                                }
                            }
                        } else if(state == SCROLL_UP_STATE) {
                            for(int i=0; i<scrollUpAnimations.size(); i++) {
                                if(scrollUpAnimations.get(i) != null) {
                                    scrollUpAnimations.get(i).actionUp();
                                }
                            }
                        } else if(state == SCROLL_DOWN_STATE) {
                            for(int i=0; i<scrollDownAnimations.size(); i++) {
                                if(scrollDownAnimations.get(i) != null) {
                                    scrollDownAnimations.get(i).actionUp();
                                }
                            }
                        }

                        x = 0;
                        y = 0;
                        prevX = 0;
                        prevY = 0;
                        state = NULL_STATE;
                        stateValueX = 0;
                        stateValueY = 0;
                        IS_FIRST_FLAG = false;
                        break;
                }

                return false;
            }
        });
    }

    private void scrollUp() {

        for(int i=0; i<scrollUpAnimations.size(); i++) {
            if(scrollUpAnimations.get(i) == null) {
                return;
            }
            scrollUpAnimations.get(i).start();
        }
    }

    private void scrollDown() {

        for(int i=0; i<scrollDownAnimations.size(); i++) {
            if(scrollDownAnimations.get(i) == null) {
                return;
            }
            scrollDownAnimations.get(i).start();
        }
    }

    private void scrollLeft() {

        for(int i=0; i<scrollLeftAnimations.size(); i++) {
            if(scrollLeftAnimations.get(i) == null) {
                return;
            }
            scrollLeftAnimations.get(i).start();
        }
    }

    private void scrollRight() {

        for(int i=0; i<scrollRightAnimations.size(); i++) {
            if(scrollRightAnimations.get(i) == null) {
                return;
            }
            scrollRightAnimations.get(i).start();
        }
    }

    private void setXY(MotionEvent motionEvent) {
        this.x = (int)motionEvent.getRawX();
        this.y = (int)motionEvent.getRawY();
    }

    private void setPrevXY() {
        this.prevX = x;
        this.prevY = y;
    }

    private void setState() {
        stateValueX = stateValueX + (x - prevX);
        stateValueY = stateValueY + (y - prevY);

        if(Math.abs(stateValueX) > Math.abs(stateValueY)) {
            if(stateValueX > ACTION_UP_SENSITIVITY) {
                state = SCROLL_RIGHT_STATE;
            } else if(stateValueX < -ACTION_UP_SENSITIVITY) {
                state = SCROLL_LEFT_STATE;
            }
        } else {
            if(stateValueY > ACTION_UP_SENSITIVITY) {
                state = SCROLL_DOWN_STATE;
            } else if(stateValueY < -ACTION_UP_SENSITIVITY) {
                state = SCROLL_UP_STATE;
            }
        }
    }

    public void setScrollSensitivity(int sensitivity) {
        this.SENSITIVITY = sensitivity;
    }
}
