package com.example.guessinggames

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView


class NumberGameActivity : AppCompatActivity() {

    lateinit var recyclerV: RecyclerView
    lateinit var newGusseNumET: EditText
    lateinit var sendBt: Button

    var gameTextList: ArrayList<NumberGameText> = ArrayList()
    val gameAdapterRV: NumberGameAdapter by lazy { NumberGameAdapter() }

    //game var:
    var chancesNum = 3
    var random = kotlin.random.Random.nextInt(0, 11)



    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Number Game"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game)


        recyclerV = findViewById(R.id.rvGameText)
        newGusseNumET = findViewById(R.id.editTextNewNumber)
        sendBt = findViewById(R.id.sendBt)

        recyclerV.adapter = gameAdapterRV



        sendBt.setOnClickListener {
            val userNum = Integer.parseInt(newGusseNumET.text.toString())
            checkNumber(userNum)
            gameAdapterRV.setGameList(gameTextList)
            newGusseNumET.setText("") // نفضي الedit text

            //gameTextList.add(GameText())

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        var mainItem = menu?.findItem(R.id.mainMenuItem)
        mainItem?.setVisible(true)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numGameItem -> {
                val intent = Intent(this, NumberGameActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.phraseGameItem -> {
                val intent = Intent(this, PhraseGameActivity ::class.java)
                startActivity(intent)
                return true
            }
            R.id.mainMenuItem -> {
                val intent = Intent(this, MainActivity ::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun checkNumber(userGusse: Int) {
        var finalText: String
        if (chancesNum > 0) {
            gameTextList.add(NumberGameText("you guessed $userGusse"))
            when (userGusse) {
                random -> {
                    println("you got it *_*")
                    gameTextList.add(NumberGameText("you got it *_* , correct answer"))
                    chancesNum = 0
                }
                else -> {
                    println("wrong guess")
                    chancesNum--
                    gameTextList.add(NumberGameText("wrong guess, you have $chancesNum guesses left"))
                }
            }
        } else {
            //gameTextList.add(GameText("Game over"))
            gameOverAlert()
        }

    }

    fun gameOverAlert() {
        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        // then we set up the input
        // val input = EditText(this)
        // here we set the message of our alert dialog
        dialogBuilder.setMessage("Play again?")
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> this.recreate() })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game over")
        // add the Edit Text
        //alert.setView(input)
        // show alert dialog
        alert.show()
    }
}