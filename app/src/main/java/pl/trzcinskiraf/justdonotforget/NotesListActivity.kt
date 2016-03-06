package pl.trzcinskiraf.justdonotforget

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import de.greenrobot.event.EventBus
import io.realm.Realm
import io.realm.RealmConfiguration
import pl.trzcinskiraf.justdonotforget.adapter.NotesListAdapter
import pl.trzcinskiraf.justdonotforget.dao.RealmNoteDao
import pl.trzcinskiraf.justdonotforget.domain.Note
import pl.trzcinskiraf.justdonotforget.event.NoteClickEvent
import java.util.*

class NotesListActivity : AppCompatActivity() {

    companion object {
        private const val resultCode = 1
    }

    val notesListRecyclerView by lazy { findViewById(R.id.notes_list_view) as RecyclerView }
    val addNewNoteButton by lazy { findViewById(R.id.add_new_note_button) as FloatingActionButton }
    var notes: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list_layout)
        setRealmConfiguration()
        notesListRecyclerView.layoutManager = LinearLayoutManager(this)
        loadNotesFromDB()
        setUpRecyclerView()
        addNewNoteButton.setOnClickListener {
            NoteActivity.start(this, Note(title = "", content = ""))
        }
    }

    private fun setRealmConfiguration() {
        val config = RealmConfiguration.Builder(this).build()
        Realm.setDefaultConfiguration(config)
    }

    private fun loadNotesFromDB() {
        val dao = RealmNoteDao.getInstance()
        notes.clear()
        notes.addAll(dao.findAll())
    }

    private fun setUpRecyclerView() {
        val newAdapter = NotesListAdapter()
        newAdapter.updateNotes(notes)
        notesListRecyclerView.adapter = newAdapter
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
        loadNotesFromDB()
        setUpRecyclerView()
        super.onResume()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    fun onEvent(event: NoteClickEvent) {
        NoteActivity.start(this, event.note)
    }

}
