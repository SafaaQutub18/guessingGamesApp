package com.example.guessinggames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var myLayout : ConstraintLayout //used for snackbar

    lateinit var numGameBt: Button
    lateinit var phaseGameBt: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Main Activity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numGameBt = findViewById(R.id.numGameBtn)
        phaseGameBt = findViewById(R.id.phraseGameBtn)

        myLayout = findViewById(R.id.mainActivityLayout)


        numGameBt.setOnClickListener {
            val intent = Intent(this, NumberGameActivity ::class.java)
            startActivity(intent)
        }
        phaseGameBt.setOnClickListener {
            val intent = Intent(this, PhraseGameActivity ::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numGameItem -> {
                val intent = Intent(this, NumberGameActivity::class.java)
                startActivity(intent)

                Snackbar.make(myLayout, "Hello there!", Snackbar.LENGTH_SHORT).show()
                return true
            }
            R.id.phraseGameItem -> {
                val intent = Intent(this, PhraseGameActivity ::class.java)
                startActivity(intent)
                Snackbar.make(myLayout, "Goodbye", Snackbar.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}