package pl.trzcinskiraf.justdonotforget.dao;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import pl.trzcinskiraf.justdonotforget.domain.Note;

@RealmClass
public class NoteRealmObject extends RealmObject {

    @PrimaryKey private String uuid;
    private String title;
    private String content;

    public NoteRealmObject() {
    }

    public NoteRealmObject(Note note) {
        this.uuid = note.getUuid();
        this.title = note.getTitle();
        this.content = note.getContent();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
