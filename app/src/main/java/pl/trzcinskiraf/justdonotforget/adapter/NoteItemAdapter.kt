package pl.trzcinskiraf.justdonotforget.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.greenrobot.eventbus.EventBus
import pl.trzcinskiraf.justdonotforget.R
import pl.trzcinskiraf.justdonotforget.domain.Note
import pl.trzcinskiraf.justdonotforget.event.NoteClickEvent

class NoteItemAdapter(private val note: Note) : ItemAdapter {

    override val itemViewType: Int = R.id.notes_list_item

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.note_item, parent, false)
        return NoteItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val noteHolder = holder as NoteItemHolder
        noteHolder.title.text = note.title
        noteHolder.content.text = note.content
        val view = noteHolder.itemView
        view.setOnClickListener(onNoteClicked)
    }

    private val onNoteClicked = { view: View ->
        val noteClickEvent = NoteClickEvent(note)
        EventBus.getDefault().post(noteClickEvent)
    }

    private inner class NoteItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.note_title) as TextView
        val content = itemView.findViewById(R.id.note_content) as TextView
    }
}