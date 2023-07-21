package me.genel.handydisplay.widgets.weather

import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import me.genel.handydisplay.core.AbstractWidget

class WeatherWidget : AbstractWidget("weather", "Weather") {
    override fun createContentPane(): Pane {
        val url = WeatherWidget::class.java.classLoader.getResource("fxml/weather.fxml")
        val loader = FXMLLoader(url)
        return loader.load<StackPane>()
    }
}