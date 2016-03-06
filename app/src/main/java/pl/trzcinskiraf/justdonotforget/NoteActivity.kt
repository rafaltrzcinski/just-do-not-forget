package pl.trzcinskiraf.justdonotforget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pl.trzcinskiraf.justdonotforget.domain.Note

class NoteActivity : AppCompatActivity() {

    companion object {
        private val noteKey = "noteKey"
        fun start(context: Context, note: Note) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(noteKey, note)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_layout)
    }
}