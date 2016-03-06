package pl.trzcinskiraf.justdonotforget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
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

    private val newNoteToolbar by lazy { findViewById(R.id.new_note_toolbar) as Toolbar }
    private val noteTitle by lazy { findViewById(R.id.note_title_edit_text) as EditText }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_layout)
        newNoteToolbar.inflateMenu(R.menu.menu_note)
        val note = intent.extras.getParcelable<Note>(noteKey)
        noteTitle.setText(note.title)
    }
}