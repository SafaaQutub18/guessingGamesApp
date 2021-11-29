package com.example.guessinggames

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class PhraseGameActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var scoreText: TextView
    lateinit var phraseText: TextView
    lateinit var recyclerV: RecyclerView
    lateinit var newGusseNumET: EditText
    lateinit var sendBt: Button

    var guessesTextList: ArrayList<PhraseGameText> = ArrayList()
    val phraseAdapterRV: PhraseGameAdapter by lazy { PhraseGameAdapter() }

    //Phrase var:
    var myPhrase = "this is the secret phrase"
    //var starsString = "**** ** *** ****** ******"
    var starsString = ""
    var unguessedletter = 9
    var lossCount = 0
    var guessedLetter = ""
    var resultText = ""
    var currentScore = 0
    var highest_Score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Guess the Phrase"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase_game)
        for(i in 0 until myPhrase.length){
            if(myPhrase[i] != ' ')
                starsString += "*"
            else
                starsString += " "
        }

        recyclerV = findViewById(R.id.rvGameText)
        newGusseNumET = findViewById(R.id.editTextNewNumber)
        sendBt = findViewById(R.id.sendBt)
        phraseText = findViewById(R.id.phraseTV)
        scoreText = findViewById(R.id.scoreTV)
        recyclerV.adapter = phraseAdapterRV

        // git the highest Score from Shared Preferences
        highest_Score =  getHighestScore()
        scoreText.text = "Highest Score: $highest_Score"

        phraseText.text = "Phrase: $starsString\nGuessed letters:"


        sendBt.setOnClickListener {
            val userGuess = newGusseNumET.text.toString()
            if(lossCount < 10)
                checkGuess(userGuess)
            else{
                resultText = "You loss T.T"
                gameOverAlert()
            }
            //save score

            saveScore(currentScore)
            scoreText.text = "Highest Score: $highest_Score"

            phraseText.text = "Phrase: $starsString\nGuessed letters: $guessedLetter "
            phraseAdapterRV.setPhraseList(guessesTextList)
            newGusseNumET.setText("")


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


    fun checkGuess(userGusse: String) {
        if (userGusse.length > 1) {
            when (userGusse) {
                myPhrase -> {
                    currentScore += 10
                    resultText = "You win!"
                    gameOverAlert()
                }
                else -> {
                    if(currentScore>0) currentScore--
                    lossCount++
                    guessesTextList.add(PhraseGameText("wrong guess: $userGusse ","#FA2500"))
                }
            }
        } else {
            if(myPhrase.contains(userGusse))
                checkLetter(userGusse)
            else {
                if(currentScore>0) currentScore--
                lossCount++
                guessesTextList.add(PhraseGameText("wrong guess: $userGusse","#FA2500"))
            }
        }


    }
    fun checkLetter(letter: String) {
        var foundCount = 0
        // check if the guess completed
        if (starsString.contains('*')) {
            //check if the letter not entered previously
            if(!guessedLetter.contains(letter)) {
                guessedLetter += (letter + ",")
                unguessedletter--
                currentScore++
            }
            var index = myPhrase.indexOf(letter)
            while (index >= 0) {
                foundCount++
                starsString[index] = letter
                val chars = starsString.toCharArray()
                chars[index] = letter.single()
                starsString = String(chars)
                index = myPhrase.indexOf(letter, index + 1);
            }
            guessesTextList.add(PhraseGameText("found $foundCount $letter(s)","#33D402"))
            guessesTextList.add(PhraseGameText("$unguessedletter guess remaining","#010300"))
        } else {
            resultText = "You win!"
            gameOverAlert()
        }
        if(unguessedletter == 0){
            resultText = "You win!"
            gameOverAlert()
        }
    }
    fun getHighestScore(): Int{
        val highestScore : Int
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        highestScore = Integer.parseInt(sharedPreferences.getString("score", "0").toString())  // --> retrieves data from Shared Preferences
        return highestScore
    }
    fun saveScore(score: Int){
        if(score > getHighestScore()){
            // We can save data with the following code
            with(sharedPreferences.edit()) {
                putString("score", score.toString())
                apply()
            }
            this.highest_Score = score
        }
    }
    fun gameOverAlert() {
        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("$resultText\nYour scores:$currentScore")
            // positive button text and action
            .setPositiveButton("Play again", DialogInterface.OnClickListener { dialog, id -> this.recreate() })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id -> dialog.cancel()})
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

private operator fun String.set(i: Int, value: String) {

}
