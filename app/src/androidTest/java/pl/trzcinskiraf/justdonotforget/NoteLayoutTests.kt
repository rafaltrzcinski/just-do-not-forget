package pl.trzcinskiraf.justdonotforget

import android.content.Intent
import android.support.test.espresso.Espresso.closeSoftKeyboard
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.trzcinskiraf.justdonotforget.TestUtils.checkTextMatching
import pl.trzcinskiraf.justdonotforget.TestUtils.isComponentDisplayed
import pl.trzcinskiraf.justdonotforget.TestUtils.isSnackBarWithTextDisplayed
import pl.trzcinskiraf.justdonotforget.TestUtils.pressButton
import pl.trzcinskiraf.justdonotforget.TestUtils.typeText
import pl.trzcinskiraf.justdonotforget.domain.Note

class NoteLayoutTests {

    @Rule @JvmField
    val rule = ruleManuallyStarted<NoteActivity> {  }

    @Before
    fun startActivity() {
        val intent = Intent()
        intent.putExtra("noteKey", Note(title = "", content = ""))
        rule.launchActivity(intent)
    }

    @Test
    fun shouldNoteComponentsBeDisplayedOnStart() {
        isComponentDisplayed(R.id.note_title_edit_text)
        isComponentDisplayed(R.id.note_content_edit_text)
        isComponentDisplayed(R.id.save_note_action)
        isComponentDisplayed(R.id.delete_note_action)
        isComponentDisplayed(R.id.add_next_note_button)
    }

    @Test
    fun shouldNoteComponentsBeEmptyAtStart() {
        checkTextMatching(R.id.note_title_edit_text, "")
        checkTextMatching(R.id.note_content_edit_text, "")
    }

    @Test
    fun shouldNotifyDisplayedWhenAddNextNoteWithEmptyTitle() {
        pressButton(R.id.add_next_note_button)
        isSnackBarWithTextDisplayed("Note title is empty !")
    }

    @Test
    fun shouldNotifyBeDisplayedWhenDeleteNoteBeforeCreate() {
        pressButton(R.id.delete_note_action)
        isSnackBarWithTextDisplayed("Note does not exist yet, please create first")
    }

    @Test
    fun shouldOpenNoteScreenWithEmptyNoteOnNextNoteButtonAction() {
        typeText(R.id.note_title_edit_text, "Note1")
        closeSoftKeyboard()
        pressButton(R.id.add_next_note_button)
        checkTextMatching(R.id.note_title_edit_text, "")
    }

}