package pl.trzcinskiraf.justdonotforget.dao

import pl.trzcinskiraf.justdonotforget.domain.Note

fun NoteRealmObject.createNote() =
        Note(
                uuid = uuid,
                title = title,
                content = content
        )