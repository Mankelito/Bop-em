package cl.mankeke.bopem

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button

class GameActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var mediaPlayerBgm: MediaPlayer
    private lateinit var mediaPlayerVictory: MediaPlayer
    private lateinit var mediaPlayerLose: MediaPlayer
    private lateinit var victoryButton: Button
    private lateinit var loseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gestureDetector = GestureDetector(this, GestureListener())
        mediaPlayerBgm = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayerVictory = MediaPlayer.create(this, R.raw.winsoundeffect)
        mediaPlayerLose = MediaPlayer.create(this, R.raw.wrongsoundeffect)

        victoryButton = findViewById<Button>(R.id.victoryButton)
        loseButton = findViewById<Button>(R.id.loseButton)

        victoryButton.setOnClickListener(){
            mediaPlayerVictory.start()
        }

        loseButton.setOnClickListener(){
            mediaPlayerLose.start()
        }

        mediaPlayerBgm.start()
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            mediaPlayerLose.start()
        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            mediaPlayerVictory.start()
            return true
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerBgm.pause()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerBgm.stop()
    }
}