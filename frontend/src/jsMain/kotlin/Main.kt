import androidx.compose.runtime.*
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.Json


fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}


@Composable
fun App() {
    val mainScope = rememberCoroutineScope()

    var input by mutableStateOf("")
    var output by mutableStateOf("")

    var outputColor by mutableStateOf(Color.white)

    Div({
        style {
            backgroundColor(Color.dimgray)
            height(100.vh)
            width(100.vw)
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.FlexStart)
            alignItems(AlignItems.Center)
            flexDirection(FlexDirection.Column)
        }
    }) {

        H2(attrs = {
            style {
                color(Color.antiquewhite)
            }
        }) {
            Text("INPUT")
        }


        Input(
            attrs = {
                type(InputType.Text)
                value(input)
                onInput { event ->
                    val target = event.target as? HTMLInputElement
                    input = target?.value ?: ""
                }
                style {
                    width(25.vw)
                    height(5.vw)
                    fontSize(25.px)
                    textAlign("center")
                }
            },
            type = InputType.Text
        )


        Button(attrs = {
            onClick {
                mainScope.launch {
                    val response = sendPostRequest(input)
                    output = response.text
                    outputColor = if (response.isOk) Color.white else Color.indianred
                }
            }
            style {
                width(25.vw)
                height(5.vw)
            }
        }) {

            Div(attrs = {
                style {
                    color(Color.black)
                    fontSize(20.px)
                }
            }) {
                Text("Calculate")
            }
        }


        H2(attrs = {
            style {
                color(Color.antiquewhite)
            }
        }) {
            Text("OUTPUT")
        }


        Div(attrs = {
            style {
                fontSize(42.px)
                color(outputColor)
            }
        }) {
            Text(output)
        }
    }
}


suspend fun sendPostRequest(input: String): CalculationResponse {
    val url = "http://localhost:8080/api/v1/calculator"

    val requestInit = RequestInit(
        method = "POST",
        mode = RequestMode.CORS,
        headers = "Content-Type" to "text/plain",
        body = input
    )

    return try {
        val response = window.fetch(url, requestInit).await()
        val responseText = response.text().await()

        if (response.ok) CalculationResponse(responseText, isOk = true)
        else if (response.status == 404.toShort()) CalculationResponse("Server is unavailable", isOk = false)
        else {
            val text = JSON.parse<Json>(responseText)["message"]?.toString() ?: "Calculation error"
            CalculationResponse(text, isOk = false)
        }
    }
    catch (e: Exception) {
        CalculationResponse("something went terribly wrong", false)
    }
}


data class CalculationResponse(val text: String, val isOk: Boolean)

