package me.genel.handydisplay.core

import handy_display.loadImage
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Text
import me.genel.handydisplay.core.widget.AbstractWidget
import org.apache.logging.log4j.kotlin.Logging
import java.awt.Image
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val OVERLAY_GROUP = "overlay"

val formatter = DateTimeFormatter.ofPattern("hh:mm - dd/MM/yy");
val SEMI_BLACK: Color = Color.rgb(0, 0, 0, 0.4)

private val barBox = loadImage(OVERLAY_GROUP, "bar_box.bmp").getScaledInstance(480, 28, Image.SCALE_DEFAULT)
private val leftImg = loadImage(OVERLAY_GROUP, "left_toggle.bmp").getScaledInstance(40, 74, Image.SCALE_DEFAULT)
private val rightImg = loadImage(OVERLAY_GROUP, "right_toggle.bmp").getScaledInstance(40, 74, Image.SCALE_DEFAULT)

private var cycleWidgetsLeft: (() -> Unit)? = null
private var cycleWidgetsRight: (() -> Unit)? = null

fun createOverlayPane(
    left: () -> Unit,
    right: () -> Unit
): Pane {
    cycleWidgetsLeft = left
    cycleWidgetsRight = right

    val url = AbstractWidget::class.java.classLoader.getResource("fxml/overlay.fxml")
    val loader = FXMLLoader(url)
    return loader.load<StackPane>()
}

class OverlayController : Logging {

    @FXML
    lateinit var widgetNameText: Text
    @FXML
    lateinit var datetimeText: Text
    @FXML
    lateinit var lagDataText: Text

    @FXML
    fun initialize() {
        val datetimeTimer = Timer()
        datetimeTimer.scheduleAtFixedRate( object: TimerTask() {
            override fun run() {
                widgetNameText.text = LocalDate.now().format(formatter)
            }
        }, 0L, 1000L)
    }

    @FXML
    fun fxmlLeftToggleButtonOnAction() =
        (cycleWidgetsLeft ?: throw NullPointerException("cycleWidgetsLeft lambda has not been set!")).invoke()

    @FXML
    fun fxmlRightToggleButtonOnAction() =
        (cycleWidgetsRight ?: throw NullPointerException("cycleWidgetsRight lambda has not been set!")).invoke()

}