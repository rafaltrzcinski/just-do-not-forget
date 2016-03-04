package pl.trzcinskiraf.justdonotforget.dao

object NoteDaoProvider {

    fun getInstance(): NoteDao = RealmNoteDao
}