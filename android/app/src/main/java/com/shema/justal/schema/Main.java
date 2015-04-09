package com.shema.justal.schema;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * The main program
 * Taking all the frame and putting on !
 * @author Justal Kevin
 */
public class Main extends Activity implements Constants {
    private FrameLayout frame;
    private List<Rectangle> listRectangle= new ArrayList<Rectangle>();
    private static int idFrame=0;

    private float mStartX;
    private float mStartY;
    private float mx;
    private float my;

    private boolean drawingMode=false;
    private boolean movingMode=false;

    private FrameLayout.LayoutParams params;
    private int left,top,index;
    private float tmpX=0,tmpY=0,newX=0,newY=0;

    private int contentViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = (FrameLayout) findViewById(R.id.framelayout);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Calculate the fucking size of the bar - There is certainly a way for passing this
        Window win = getWindow();
        contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        mx = event.getX();
        my = event.getY()-contentViewTop;

        if(!drawingMode && !movingMode) {
            if(!inside(mx,my)) {
                drawingMode=true;
            } else {
                movingMode=true;
            }
        }

        if(drawingMode) {
            onTouchEventRectangle(event);
        } else if(movingMode) {
            onTouchEventMoveRectangle(event);
        } else {
            throw new IllegalArgumentException("Impossible");
        }
        return true;
    }

    private void onTouchEventRectangle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = mx;
                mStartY = my;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                drawRectangle();
                drawingMode=false;
                break;
        }
    }

    private void onTouchEventMoveRectangle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Position
                tmpX=event.getX();
                tmpY=event.getY()-contentViewTop;

                // LayoutParam
                index=-1;
                for(int i=0;i<listRectangle.size();i++) {
                    if(listRectangle.get(i).inside(mx,my)) {
                        index=i;
                    }
                }
                params = (FrameLayout.LayoutParams) frame.getChildAt(index).getLayoutParams();
                left=params.leftMargin;
                top=params.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY()-contentViewTop;
                params.leftMargin=left+(int)(newX-tmpX);
                params.topMargin=top+(int)(newY-tmpY);
                listRectangle.get(index).setMove((int)(newX-tmpX),(int)(newY-tmpY));
                frame.getChildAt(index).setLayoutParams(params);
                tmpX=event.getX();
                tmpY=event.getY()-contentViewTop;
                break;
            case MotionEvent.ACTION_UP:
                movingMode=false;
                break;
        }
    }

    /**
     * Checking if we try to draw a rectangle with a too small size.
     * @param left The left side of the rectangle that we try to draw
     * @param top The top side of the rectangle that we try to draw
     * @param right The right side of the rectangle that we try to draw
     * @param bottom The bottom side of the rectangle that we try to draw
     * @return true if the size of the rectangle that we try to draw is acceptable, false if else
     */
    private boolean isSizeDrawable(float left,float top,float right,float bottom) {
        if(Math.abs(left-right)>SIZE_MAX_X_RECTANGLE && Math.abs(top-bottom)>SIZE_MAX_Y_RECTANGLE) {
            return true;
        }
        return false;
    }

    /**
     * Checking if we try to draw a rectangle upon an other rectangle
     * @param left The left side of the rectangle that we try to draw
     * @param top The top side of the rectangle that we try to draw
     * @param right The right side of the rectangle that we try to draw
     * @param bottom The bottom side of the rectangle that we try to draw
     * @return True if the rectangle is not upon an other one, false if else
     */
    private boolean isOutOfRectangle(float left,float top,float right,float bottom) {
        for(int i=0;i<listRectangle.size();i++) {
            if(listRectangle.get(i).on(left,top,right,bottom)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Drawing a simple rectangle if this one follow some principle
     */
    private void drawRectangle(){
        float right = mStartX > mx ? mStartX : mx;
        float left = mStartX > mx ? mx : mStartX;
        float bottom = mStartY > my ? mStartY : my;
        float top = mStartY > my ? my : mStartY;
        if(isSizeDrawable(left,top,right,bottom) && isOutOfRectangle(left,top,right,bottom)) {
            Rectangle tmp = new Rectangle(this, left, top, right, bottom);
            listRectangle.add(tmp);
            frame.addView(tmp, idFrame);
            idFrame++;
        }
    }

    /**
     * Checking if the corner of the rectanle that we want draw is not inside an other one
     * @param x The X position of the finger
     * @param y The Y position of the finger
     * @return True if this position is inside a rectangle
     */
    private boolean inside(float x,float y) {
        for(int i=0;i<listRectangle.size();i++) {
            if(listRectangle.get(i).inside(x,y)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}