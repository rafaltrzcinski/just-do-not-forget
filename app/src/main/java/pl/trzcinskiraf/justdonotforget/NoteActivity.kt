package pl.trzcinskiraf.justdonotforget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.Toolbar
import android.widget.EditText
import pl.trzcinskiraf.justdonotforget.dao.RealmNoteDao
import pl.trzcinskiraf.justdonotforget.domain.Note
import java.util.*

class NoteActivity : JustDoNotForgetActivity() {

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
        val noteUuid = note.uuid

        saveNoteAction.setOnClickListener {
            if (!isNoteEmpty()) {
                saveOrUpdateNote(noteUuid)
                finish()
            } else
                notifyBySnackBar("Note title is empty !")
        }

        deleteNoteAction.setOnClickListener {
            if (isNoteRemovable(noteUuid)) {
                deleteNote(noteUuid)
                finish()
            } else
                notifyBySnackBar("Note does not exist yet, please create first")
        }

        addNextNoteButton.setOnClickListener {
            if (!isNoteEmpty()) {
                saveOrUpdateNote(noteUuid)
                NoteActivity.start(this, Note(title = "", content = ""))
                finish()
            } else
                notifyBySnackBar("Note title is empty !")
        }
    }

    override fun onBackPressed() {
        if (!isNoteEmpty())
            saveOrUpdateNote(note.uuid)
        super.onBackPressed()
    }

    private fun saveOrUpdateNote(noteUUID: String) {
        val note = Note(
                title = noteTitle.text.toString(),
                content = noteContent.text.toString())
        if (getAllNotesUuids().contains(noteUUID)) {
            deleteNote(noteUUID)
            saveNote(note)
        } else {
            saveNote(note)
        }
    }

    private fun isNoteRemovable(noteUUID: String) = getAllNotesUuids().contains(noteUUID)

    private fun isNoteEmpty() = noteTitle.text.isNullOrBlank()

    private fun getAllNotesUuids(): List<String> {
        val allUuids = ArrayList<String>()
        RealmNoteDao.getInstance().findAll().forEach { allUuids.add(it.uuid) }
        return allUuids
    }

    private fun notifyBySnackBar(notifierText: String) {
        val snackBar = Snackbar.make(noteContent, notifierText, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    fun deleteNote(noteUUID: String) = RealmNoteDao.getInstance().deleteOne(noteUUID)

    fun saveNote(noteToSave: Note) = RealmNoteDao.getInstance().save(noteToSave)

}