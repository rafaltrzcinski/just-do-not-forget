package pl.trzcinskiraf.justdonotforget.adapter

import pl.trzcinskiraf.justdonotforget.domain.Note
import java.util.*

class NotesListAdapter : BaseAdapter() {

    fun updateNotes(notes: List<Note>) {
        adapters.clear()
        adapters.addAll(createAdapters(notes))
        notifyDataSetChanged()
    }

    private fun createAdapters(notes: List<Note>): ArrayList<ItemAdapter> {
        val itemsAdapters = ArrayList<ItemAdapter>()
        notes.forEach { itemsAdapters.add(NoteItemAdapter(it)) }
        return itemsAdapters
    }
}