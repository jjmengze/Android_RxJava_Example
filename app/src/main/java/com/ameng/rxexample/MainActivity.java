package com.ameng.rxexample;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private MultipleButton multipleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        multipleButton = new MultipleButton(this);
        setContentView(multipleButton);
        sample_01();
        sample_02();
        sample_03();
        sample_04();
        sample_05();
        sample_06();
    }

    /**
     * 使用 create() 方法來創造一個 Observable ，並為它定義事件觸發規則：
     */
    private void sample_01() {
        multipleButton.addButton("使用 create() 方法來創造一個 Observable").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("a");
                        subscriber.onNext("b");
                        subscriber.onNext("c");
                        subscriber.onCompleted();
                    }
                });
                observable.subscribe(observer).unsubscribe();
            }
        });
    }

    /**
     * 快速建立事件佇列
     */
    private void sample_02() {
        multipleButton.addButton("快速建立事件佇列").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable observable = Observable.just("a", "b", "c");
                observable.subscribe(observer).unsubscribe();
            }
        });
    }

    /**
     * 將傳入的參數陣列或 Iterable 拆分成具體對像後，依次發送出來
     */
    private void sample_03() {
        multipleButton.addButton("將傳入的參數陣列或 Iterable 拆分成具體對像後，依次發送出來").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] words = {"a", "b", "c"};
                Observable observable = Observable.from(words);
                observable.subscribe(observer).unsubscribe();
            }
        });
    }

    /**
     * 用不完整定義的接口進行call back
     */
    private void sample_04() {
        multipleButton.addButton("用不完整定義的接口進行call back").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable observable = Observable.just("a", "b", "c");
                observable.subscribe(onNextAction, onErrorAction, onCompletedAction).unsubscribe();
            }
        });
    }

    /**
     * Res 取得圖片，並顯示在 ImageView，出現異常的時候Toast 回報錯誤
     */
    private void sample_05() {
        multipleButton.addButton("Res 取得圖片，並顯示在 ImageView，出現異常的時候Toast 回報錯誤").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int drableRes = R.drawable.doge;
                Observable.create(new Observable.OnSubscribe<Drawable>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        Drawable drawable = getTheme().getDrawable(drableRes);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                }).subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        multipleButton.addImageView(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Res 取得圖片運算後轉換成灰階，並顯示在 ImageView，出現異常的時候Toast 回報錯誤
     */
    private void sample_06() {
        multipleButton.addButton("Res 取得圖片運算後轉換成灰階，並顯示在 ImageView，出現異常的時候Toast 回報錯誤").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int drableRes = R.drawable.doge;
                Observable.create(new Observable.OnSubscribe<Bitmap>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drableRes);
                        ProcessingBase64 processingBase64 = new ProcessingBase64();
                        subscriber.onNext(processingBase64.getGrayScale(bitmap));
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.computation()) // 指定 subscribe() 在 computation thread
                        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的call back 在main thread
                        .subscribe(new Observer<Bitmap>() {
                            @Override
                            public void onNext(Bitmap bitmap) {
                                multipleButton.addImageView(bitmap);
                            }

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.e("ERROR", e.toString());
                            }
                        });
            }
        });
    }

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d("onNext", "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d("onCompleted", "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d("onError", "Error!");
        }
    };

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d("onNext", "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d("onCompleted", "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d("onError", "Error!");
        }
    };


    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.d("onNextAction", s);
        }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Log.d("onErrorAction", "onErrorAction");
        }
    };
    Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
            Log.d("onCompletedAction", "onCompletedAction");
        }
    };
}
