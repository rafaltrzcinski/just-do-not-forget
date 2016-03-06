package pl.trzcinskiraf.justdonotforget.adapter

import pl.trzcinskiraf.justdonotforget.domain.Note

class NotesListAdapter(val notes: List<Note>) : BaseAdapter() {

    init {
        notes.forEach {
            adapters.add(NoteItemAdapter(it))
        }
    }
}