package cl.mankeke.bopem

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.hardware.SensorEventListener
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.concurrent.DelayQueue
import kotlin.random.Random


class GameActivity : AppCompatActivity(), SensorEventListener{
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private lateinit var gestureDetector: GestureDetector
    private lateinit var mediaPlayerBgm: MediaPlayer
    private lateinit var mediaPlayerVictory: MediaPlayer
    private lateinit var mediaPlayerLose: MediaPlayer
    private lateinit var instructiontext: TextView
    private lateinit var feedbacktext: TextView
    private lateinit var scoretext: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var score = 0
    private val instructionlist = listOf(
        "Deslice por la pantalla",
        "Agita el dispositivo",
        "Presione dos veces"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gestureDetector = GestureDetector(this, GestureListener())
        mediaPlayerBgm = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayerVictory = MediaPlayer.create(this, R.raw.winsoundeffect)
        mediaPlayerLose = MediaPlayer.create(this, R.raw.wrongsoundeffect)

        instructiontext = findViewById<TextView>(R.id.instructionText)
        feedbacktext = findViewById<TextView>(R.id.feedbackText)
        scoretext = findViewById<TextView>(R.id.scoreText)

        mediaPlayerBgm.start()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        RandomizeInstructions()
        feedbacktext.text = " "
        scoretext.text = "0"

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (instructiontext.text == "Presione dos veces") {
                mediaPlayerVictory.start()
                feedbacktext.text = "Bien Hecho"
                score++;
                scoretext.text = score.toString()
                InstructionDelay()
            } else{
                feedbacktext.text ="Perdiste"
                mediaPlayerLose.start()
            }
            return super.onDoubleTap(e)

        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            if (instructiontext.text == "Deslice por la pantalla") {
                mediaPlayerVictory.start()
                score++;
                scoretext.text = score.toString()
                InstructionDelay()
            } else{
                feedbacktext.text ="Perdiste"
                mediaPlayerLose.start()
            }
            return true
        }
    }
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
        mediaPlayerBgm.pause()
        sensorManager?.unregisterListener(this)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerBgm.stop()
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val acceleration = Math.sqrt(x * x + y * y + z * z.toDouble()).toFloat()

            // You can adjust the acceleration threshold based on your needs
            val threshold = 50.0f

            if (acceleration > threshold && instructiontext.text == "Agita el dispositivo") {
                mediaPlayerVictory.start()
                score++;
                scoretext.text = score.toString()
                InstructionDelay()
            } else if(acceleration > threshold && instructiontext.text != "Agita el dispositivo"){
                mediaPlayerLose.start()
            }
        }
    }

    private fun RandomizeInstructions()
    {
        val randomnum = Random.nextInt(0, instructionlist.size)
        val randominstruction = instructionlist[randomnum]
        instructiontext.text = randominstruction
    }

    private fun InstructionDelay() {
        handler.postDelayed({
            RandomizeInstructions() // Realizar la función después de 3 segundos
            feedbacktext.text = " "
        }, 3000) // Retraso de 3 segundos (3000 milisegundos)
    }


}