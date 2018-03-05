package com.sandbox50572.mywebcam3;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder recorder;
    private Preview mPreview;




// пример записи видео (источник)
// Форма в активности делается програмно, так что просто создаете новый проект
// и вставляете эту активность.


            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);

                recorder = new MediaRecorder();
                recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

                mPreview = new Preview(this, recorder);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setContentView(mPreview);

            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                menu.add(0, 0, 0, "Начать запись");
                menu.add(0, 1, 0, "Завершить запись");
                return super.onCreateOptionsMenu(menu);
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        try {

                            recorder.start();

                        } catch (Exception e) {
                            e.printStackTrace();
                            recorder.release();
                        }
                        break;

                    case 1:
                        recorder.stop();
                        recorder.release();
                        recorder = null;
                        break;

                    default:
                        break;
                }
                return super.onOptionsItemSelected(item);
            }


        class Preview extends SurfaceView implements SurfaceHolder.Callback {
            SurfaceHolder mHolder;
            MediaRecorder tempRecorder;

            Preview(Context context, MediaRecorder recorder) {
                super(context);
                tempRecorder = recorder;
                mHolder = getHolder();
                mHolder.addCallback(this);
                mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }

            public Surface getSurface() {
                return mHolder.getSurface();
            }

            public void surfaceCreated(SurfaceHolder holder) {
                tempRecorder.setOutputFile("/sdcard/video.3gp");
                tempRecorder.setPreviewDisplay(mHolder.getSurface());
                try {
                    tempRecorder.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                    tempRecorder.release();
                    tempRecorder = null;
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                if (tempRecorder != null) {
                    tempRecorder.release();
                    tempRecorder = null;
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

            }
        }






    }


