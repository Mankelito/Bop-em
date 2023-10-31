package cl.mankeke.bopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val buttonSettings = findViewById<Button>(R.id.settings_button)
        val buttonAbout = findViewById<Button>(R.id.about_button)
        val buttonNext = findViewById<Button>(R.id.next_button)

        // Button click listeners
        buttonSettings.setOnClickListener {
            val intentSettings = Intent(this, SettingsActivity::class.java)
            startActivity(intentSettings)
        }

        buttonAbout.setOnClickListener {
            val intentAbout = Intent(this, AboutActivity::class.java)
            startActivity(intentAbout)
        }

        buttonNext.setOnClickListener {
            val intentAbout = Intent(this, GameActivity::class.java)
            startActivity(intentAbout)
        }






    }
}