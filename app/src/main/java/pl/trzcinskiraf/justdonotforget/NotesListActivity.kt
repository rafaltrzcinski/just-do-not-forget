package pl.trzcinskiraf.justdonotforget

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import de.greenrobot.event.EventBus
import io.realm.Realm
import io.realm.RealmConfiguration
import pl.trzcinskiraf.justdonotforget.adapter.NotesListAdapter
import pl.trzcinskiraf.justdonotforget.dao.RealmNoteDao
import pl.trzcinskiraf.justdonotforget.domain.Note
import pl.trzcinskiraf.justdonotforget.event.NoteClickEvent
import java.util.*

class NotesListActivity : JustDoNotForgetActivity() {

    val notesListRecyclerView by lazy { findViewById(R.id.notes_list_view) as RecyclerView }
    val addNewNoteButton by lazy { findViewById(R.id.add_new_note_button) as FloatingActionButton }
    var notes: MutableList<Note> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_list_layout)
        setRealmConfiguration()
        notesListRecyclerView.layoutManager = GridLayoutManager(this, 2)
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
        setUpItemTouchHelper()
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

    private fun setUpItemTouchHelper() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = notesListRecyclerView.adapter as NotesListAdapter
                adapter.remove(swipedPosition)
                loadNotesFromDB()
                NoteActivity().deleteNote(notes[swipedPosition].uuid)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(notesListRecyclerView)
    }

}
