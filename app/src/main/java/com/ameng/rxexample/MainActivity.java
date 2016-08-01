package com.ameng.rxexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

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
    }

    /**
     * 使用 create() 方法來創造一個 Observable ，並為它定義事件觸發規則：
     */
    private void sample_01() {
        multipleButton.addButton("sample_01").setOnClickListener(new View.OnClickListener() {
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
        multipleButton.addButton("sample_02").setOnClickListener(new View.OnClickListener() {
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
        multipleButton.addButton("sample_03").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] words = {"a", "b", "c"};
                Observable observable = Observable.from(words);
                observable.subscribe(observer).unsubscribe();
            }
        });
    }

    private void sample_04() {
        multipleButton.addButton("sample_04").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable observable = Observable.just("a", "b", "c");
                observable.subscribe(onNextAction,onErrorAction,onCompletedAction).unsubscribe();
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
