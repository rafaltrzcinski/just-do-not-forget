package pl.trzcinskiraf.justdonotforget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.Toolbar
import android.widget.EditText
import pl.trzcinskiraf.justdonotforget.dao.RealmNoteDao
import pl.trzcinskiraf.justdonotforget.domain.Note
import java.util.*

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
    private val noteContent by lazy { findViewById(R.id.note_content_edit_text) as EditText }
    private val saveNoteAction by lazy { findViewById(R.id.save_note_action) as ActionMenuItemView }
    private val deleteNoteAction by lazy { findViewById(R.id.delete_note_action) as ActionMenuItemView }
    private val addNextNoteButton by lazy { findViewById(R.id.add_next_note_button) as FloatingActionButton }
    private val note by lazy { intent.extras.getParcelable<Note>(noteKey) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_layout)
        newNoteToolbar.inflateMenu(R.menu.menu_note)
        noteTitle.setText(note.title)
        noteContent.setText(note.content)
        saveNoteAction.setOnClickListener {
            saveOrUpdateNote(note.uuid)
            finish()
        }
        deleteNoteAction.setOnClickListener {
            deleteNote(note.uuid)
            finish()
        }
        addNextNoteButton.setOnClickListener {
            saveOrUpdateNote(note.uuid)
            NoteActivity.start(this, Note(title = "", content = ""))
            finish()
        }
    }

    override fun onBackPressed() {
        saveOrUpdateNote(note.uuid)
        super.onBackPressed()
    }

    private fun saveOrUpdateNote(noteUUID: String) {
        val note = Note(
                title = noteTitle.text.toString(),
                content = noteContent.text.toString())
        val allUuids = ArrayList<String>()
        RealmNoteDao.getInstance().findAll().forEach { allUuids.add(it.uuid) }
        if (allUuids.contains(noteUUID)) {
            deleteNote(noteUUID)
            saveNote(note)
        } else {
            saveNote(note)
        }
    }

    private fun deleteNote(noteUUID: String) {
        RealmNoteDao.getInstance().deleteOne(noteUUID)
    }

    private fun saveNote(noteToSave: Note) {
        RealmNoteDao.getInstance().save(noteToSave)
    }
}