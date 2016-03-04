package pl.trzcinskiraf.justdonotforget.dao

import pl.trzcinskiraf.justdonotforget.domain.Note

interface NoteDao {

    fun save(note: Note): String

    fun findAll(): List<Note>

    fun findOne(uuid: String): Note

    fun deleteAll()

    fun deleteOne(uuid: String)
}