package pl.trzcinskiraf.justdonotforget

import android.os.Bundle
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

    val notesListRecyclerView by lazy { findViewById(R.id.notes_list_view) as RecyclerView }
    var notes: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list_layout)
        setRealmConfiguration()
        notesListRecyclerView.layoutManager = LinearLayoutManager(this)
        loadNotesFromDB()
        setRecyclerViewAdapter()
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


    private fun setRecyclerViewAdapter() {
        notesListRecyclerView.adapter = NotesListAdapter(notes)
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
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
