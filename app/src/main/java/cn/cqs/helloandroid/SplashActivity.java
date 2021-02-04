package cn.cqs.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import cn.cqs.aop.aspect.ActivityAspect;
import cn.cqs.common.view.particle.ParticleView;

/**
 * Created by bingo on 2020/12/17.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/17
 */
public class SplashActivity extends AppCompatActivity {
    ParticleView particleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ActivityAspect.setEnterAnimation(new int[]{R.anim.fade_in,R.anim.fade_out});
        particleView = findViewById(R.id.particleView);
        particleView.startAnim();
        particleView.setOnParticleAnimListener(new ParticleView.OnParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
