package cl.mankeke.bopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    val tagLog = "SplashActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Getting preference
        //val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Load the fade-in animation
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)

        // Load the growth animation
        val scaleUp = AnimationUtils.loadAnimation(applicationContext, R.anim.scaleup)

        // Create an AnimationSet to combine both animations
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(scaleUp)

        val imageView = findViewById<ImageView>(R.id.imagePotato)

        imageView.startAnimation(fadeIn)
        val seconds = 2
        val delayMillis = seconds!!.toLong() // 2 seconds (adjust as needed)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the splash screen activity
        }, delayMillis)
    }
}