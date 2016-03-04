package pl.trzcinskiraf.justdonotforget.dao

import io.realm.Realm
import pl.trzcinskiraf.justdonotforget.domain.Note

object RealmNoteDao : NoteDao {

    fun getInstance() = RealmNoteDao

    private fun <T> Realm.inTransaction(inTransaction: Realm.() -> T): T {
        beginTransaction()
        val result = inTransaction()
        commitTransaction()
        return result
    }

    private fun <T> inRealm(inRealm: Realm.() -> T): T {
        val realm = Realm.getDefaultInstance()
        try {
            return inRealm(realm)
        } finally {
            realm.close()
        }
    }

    private fun <T> inRealmAndTransaction(func: Realm.() -> T): T {
        return inRealm {
            inTransaction {
                func()
            }
        }
    }

    override fun save(note: Note): String {
        return inRealmAndTransaction {
            copyToRealmOrUpdate(NoteRealmObject(note)).uuid
        }
    }

    override fun findAll(): List<Note> {
        return inRealm {
            where(NoteRealmObject::class.java)
                    .findAll()
                    .map { it.createNote() }
        }
    }

    override fun findOne(uuid: String): Note {
        return inRealm { where(NoteRealmObject::class.java)
                .equalTo("uuid", uuid)
                .findFirst()
                .createNote() }
    }

    override fun deleteAll() {
        inRealmAndTransaction { where(NoteRealmObject::class.java)
                .findAll()
                .removeAll { true } }
    }

    override fun deleteOne(uuid: String) {
        inRealm { where(NoteRealmObject::class.java)
                .equalTo("uuid", uuid)
                .findFirst()
                .removeFromRealm() }
    }
}