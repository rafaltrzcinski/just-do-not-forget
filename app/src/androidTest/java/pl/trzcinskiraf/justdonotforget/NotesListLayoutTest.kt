package pl.trzcinskiraf.justdonotforget

import org.junit.Rule
import org.junit.Test
import pl.trzcinskiraf.justdonotforget.TestUtils.isComponentDisplayed
import pl.trzcinskiraf.justdonotforget.TestUtils.isInRecyclerView
import pl.trzcinskiraf.justdonotforget.TestUtils.pressButton
import pl.trzcinskiraf.justdonotforget.dao.RealmNoteDao
import pl.trzcinskiraf.justdonotforget.domain.Note

class NotesListLayoutTest {

    @Rule @JvmField
    val rule = rule<NotesListActivity> {
        val dao = RealmNoteDao.getInstance()
        dao.deleteAll()
        dao.save(Note(title = "Note1", content = "Some note text"))
    }

    @Test
    fun shouldAddNewNoteButtonBeDisplayed() {
        isComponentDisplayed(R.id.add_new_note_button)
    }

    @Test
    fun shouldOneNoteBeDisplayedOnStart() {
        isInRecyclerView(R.id.notes_list_view, R.id.notes_list_item)
    }

    @Test
    fun shouldAfterClickOnAddNewNoteButtonNoteComponentsBeDisplayed() {
        pressButton(R.id.add_new_note_button)
        isComponentDisplayed(R.id.note_title_edit_text)
        isComponentDisplayed(R.id.note_content_edit_text)
        isComponentDisplayed(R.id.add_next_note_button)
    }
}